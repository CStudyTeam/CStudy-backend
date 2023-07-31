package com.CStudy.domain.competition.controller;

import com.CStudy.domain.competition.application.CompetitionService;
import com.CStudy.domain.competition.application.MemberCompetitionService;
import com.CStudy.domain.competition.dto.request.CreateCompetitionRequestDto;
import com.CStudy.domain.role.enums.RoleEnum;
import com.CStudy.enums.MemberTestEnum;
import com.CStudy.global.jwt.util.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class CompetitionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private CompetitionService competitionService;

    @MockBean
    private MemberCompetitionService memberCompetitionService;

    private static String VALID_TOKEN;
    private static String INVALID_TOKEN;


    @BeforeEach
    void setUp() {
        VALID_TOKEN = jwtTokenizer.createAccessToken(1L, MemberTestEnum.VALID_EMAIL.getMessage(), List.of(RoleEnum.ADMIN.getRoleName()));
        INVALID_TOKEN = jwtTokenizer.createAccessToken(2L, MemberTestEnum.VALID_EMAIL.getMessage(), List.of(RoleEnum.CUSTOM.getRoleName()));
    }

    @Test
    @DisplayName("대회 생성하기")
    void createCompetitionWithValidParameter() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();

        CreateCompetitionRequestDto createCompetitionRequestDto = CreateCompetitionRequestDto.builder()
                .competitionStart(now)
                .competitionEnd(now.plusHours(1))
                .competitionTitle("CS 경기")
                .participants(100)
                .build();
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/competition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + VALID_TOKEN)
                                .content(objectMapper.writeValueAsBytes(createCompetitionRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        //then
        //verify()
    }

    @Test
    @DisplayName("대회 생성하기 Token 오류")
    void createCompetitionWithInvalidToken() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();

        CreateCompetitionRequestDto createCompetitionRequestDto = CreateCompetitionRequestDto.builder()
                .competitionStart(now)
                .competitionEnd(now.plusHours(1))
                .competitionTitle("CS 경기")
                .participants(100)
                .build();
        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/competition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + INVALID_TOKEN)
                                .content(objectMapper.writeValueAsBytes(createCompetitionRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
        //then
        //verify()
    }
}