package com.hamitmizrak.security.config;

import com.hamitmizrak.security.jwt.JwtProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
JwtProps : @ConfigurationProperties(prefix = "app.jwt") çalışması için eklendi
 */
@Configuration
@EnableConfigurationProperties(JwtProps.class)
public class JwtConfiguration {
}
