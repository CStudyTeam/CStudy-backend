package com.CStudy.global.initializer;

import com.CStudy.domain.role.entity.Role;
import com.CStudy.domain.role.enums.RoleEnum;
import com.CStudy.domain.role.repositry.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
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
        };
    }
}
