package com.CStudy.domain.request.controller;

import com.CStudy.domain.request.application.RequestService;
import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.request.FlagRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
      this.requestService = requestService;
    }

    @PostMapping("/request/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRequest(
            @RequestBody CreateRequestRequestDto requestDto,
            @IfLogin LoginUserDto loginUser
    ) {
        requestService.createRequest(requestDto, loginUser.getMemberId());
    }

    @PostMapping("/request/approve")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateFlag(
        @RequestBody FlagRequestDto flagDto,
        @IfLogin LoginUserDto loginUser
    ) {
        if(loginUser.getRoles().contains("ROLE_ADMIN")) {
            requestService.updateFlag(flagDto.getId());
        }
    }

    @GetMapping("/request/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public RequestResponseDto getRequest(
            @PathVariable Long id
    ) {
        return requestService.getRequest(id);
    }

    @GetMapping("/request/list/{memberId}")
    @ResponseStatus(HttpStatus.CREATED)
    public  Page<RequestResponseDto> getMemberRequestList(
            @PageableDefault(sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable,
            @PathVariable Long memberId
    ) {
        return requestService.getRequestList(memberId, pageable);
    }

    @GetMapping("/request/list")
    @ResponseStatus(HttpStatus.CREATED)
    public Page<RequestResponseDto> getRequestList(
            @PageableDefault(sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable
    ) {
        return requestService.getRequestList(pageable);
    }

}
