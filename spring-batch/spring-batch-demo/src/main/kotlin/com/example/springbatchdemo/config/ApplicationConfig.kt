package com.example.springbatchdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties
class ApplicationConfig {

    var eshost: String = ""
}