package com.group308.socialmedia;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.group308.socialmedia.core.model.repository")
public class DBConfig {

}