package com.CStudy.global.config;


import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.global.jwt.util.JwtTokenizer;
import com.CStudy.global.util.IfLoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//Cors 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;

    public WebConfig(
            JwtTokenizer jwtTokenizer,
            MemberRepository memberRepository
    ) {
        this.jwtTokenizer = jwtTokenizer;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "OPTIONS", "DELETE");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new IfLoginArgumentResolver());
    }
}
