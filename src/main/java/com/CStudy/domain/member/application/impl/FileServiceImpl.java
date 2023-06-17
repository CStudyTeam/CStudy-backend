package com.CStudy.domain.member.application.impl;

import com.CStudy.domain.member.application.FileService;
import com.CStudy.domain.member.entity.File;
import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.FileRepository;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.global.exception.member.NotFoundMemberId;
import com.CStudy.global.util.LoginUserDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3Client amazonS3Client;
    private final FileRepository fileRepository;
    private final MemberRepository memberRepository;

    public FileServiceImpl(AmazonS3Client amazonS3Client, FileRepository fileRepository, MemberRepository memberRepository) {
        this.amazonS3Client = amazonS3Client;
        this.fileRepository = fileRepository;
        this.memberRepository = memberRepository;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Override
    @Transactional
    public List<String> uploadFiles(MultipartFile[] multipartFileList, LoginUserDto loginUserDto) throws Exception {

        Member member = memberRepository.findById(loginUserDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));

        List<String> imagePathList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            String imagePath = uploadFile(multipartFile, member);
            imagePathList.add(imagePath);
        }

        return imagePathList;
    }

    @Override
    public byte[] getImageBytes(LoginUserDto loginUserDto) {

        Member member = memberRepository.findById(loginUserDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberId(loginUserDto.getMemberId()));

        String fileName = member.getFile().getFileName();

        String imageUrl = "https://" + S3Bucket + ".s3."+region+".amazonaws.com/" + fileName;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);
        return response.getBody();
    }

    private String uploadFile(MultipartFile multipartFile, Member member) throws Exception {
        String originalName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        File file = File.builder()
                .fileName(originalName)
                .member(member)
                .build();

        fileRepository.save(file);

        long size = multipartFile.getSize(); // 파일 크기

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근 가능한 URL 가져오기
    }
}
