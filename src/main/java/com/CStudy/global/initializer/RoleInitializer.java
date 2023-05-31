package com.CStudy.global.initializer;

import com.CStudy.domain.member.entity.Member;
import com.CStudy.domain.member.repository.MemberRepository;
import com.CStudy.domain.role.entity.Role;
import com.CStudy.domain.role.enums.RoleEnum;
import com.CStudy.domain.role.repositry.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(
            RoleRepository roleRepository,
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role custom = Role.builder()
                        .roleId(1L)
                        .name(RoleEnum.CUSTOM.getRoleName())
                        .build();

                Role admin = Role.builder()
                        .roleId(2L)
                        .name(RoleEnum.ADMIN.getRoleName())
                        .build();



                roleRepository.save(custom);
                roleRepository.save(admin);
            }

            if (memberRepository.count() == 0) {

                Optional<Role> adminRoleOptional = roleRepository.findByName(RoleEnum.ADMIN.getRoleName());

                if (adminRoleOptional.isPresent()) {
                    Role adminRole = adminRoleOptional.get();

                    Member member = Member.builder()
                            .email("admin")
                            .password(passwordEncoder.encode("1234"))
                            .name("관리자")
                            .roles(Collections.singleton(adminRole))
                            .build();

                    memberRepository.save(member);
                }
            }
        };
    }
}
