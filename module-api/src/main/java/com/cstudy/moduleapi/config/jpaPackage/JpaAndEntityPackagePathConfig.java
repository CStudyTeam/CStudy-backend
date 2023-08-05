package com.cstudy.moduleapi.config.jpaPackage;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages =  "com.cstudy.modulecommon.repository")
@EntityScan(basePackages = "com.cstudy.modulecommon.domain")
@Configuration
public class JpaAndEntityPackagePathConfig {
}
