package com.atguigu.lease.common.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MinioConfiguration
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/15 15:57
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

   @Autowired
   private MinioProperties properties;

   @Bean
   public MinioClient minioClient() {
      return MinioClient.builder().endpoint(properties.getEndpoint()).credentials(properties.getAccessKey(), properties.getSecretKey()).build();
   }
}