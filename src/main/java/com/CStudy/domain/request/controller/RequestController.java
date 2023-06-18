package com.CStudy.domain.request.controller;

import com.CStudy.domain.request.application.RequestService;
import com.CStudy.domain.request.dto.request.CreateRequestRequestDto;
import com.CStudy.domain.request.dto.request.FlagRequestDto;
import com.CStudy.domain.request.dto.response.RequestResponseDto;
import com.CStudy.global.exception.ErrorResponse;
import com.CStudy.global.util.IfLogin;
import com.CStudy.global.util.LoginUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Request(게시판 API)", description = "게시판 관련 API(게시판 조회, 생성)")
@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
      this.requestService = requestService;
    }

    @Operation(summary = "게시판 문제 요청글 생성", description = "게시판 문제 요청글 생성. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 생성 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/request/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRequest(
            @Parameter(name = "CreateRequestRequestDto", description = "title: 게시글 제목, description: 게시글 내용")
            @RequestBody CreateRequestRequestDto requestDto,
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUser
    ) {
        requestService.createRequest(requestDto, loginUser.getMemberId());
    }

    @Operation(summary = "게시글 상태 수정", description = "게시판 상태를 대기에서 승인으로 변경.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 상태 변경 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/request/approve")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateFlag(
            @Parameter(description = "id: 게시글 id, flag: 승인-true, 대기-false")
            @RequestBody FlagRequestDto flagDto,
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUser
    ) {
        if(loginUser.getRoles().contains("ROLE_ADMIN")) {
            requestService.updateFlag(flagDto);
        }
    }

    @Operation(summary = "게시판 글 조회", description = "게시글 id를 이용해 게시글을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/request/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RequestResponseDto getRequest(
            @Parameter(description = "게시글 id")
            @PathVariable Long id
    ) {
        return requestService.getRequest(id);
    }

    @Operation(summary = "내가 요청한 문제 조회", description = "내가 요청한 문제 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "조회 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/request/mylist")
    @ResponseStatus(HttpStatus.OK)
    public Page<RequestResponseDto> getMemberRequestList(
            @Parameter(description = "page: 페이지 번호, size: 한 페이지 문제 수.")
            @PageableDefault(sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable,
            @Parameter(hidden = true)
            @IfLogin LoginUserDto loginUser
    ) {
        return requestService.getRequestList(loginUser.getMemberId(), pageable);
    }

    @Operation(summary = "게시판 전체 리스트 조회", description = "게시판 전체 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/request/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<RequestResponseDto> getRequestList(
            @Parameter(description = "page: 페이지 번호, size: 한 페이지 문제 수.")
            @PageableDefault(sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable
    ) {
        return requestService.getRequestList(pageable);
    }

}
