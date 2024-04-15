package com.atguigu.lease.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClassName:MinioProperties
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/15 15:55
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {

   private String endpoint;

   private String accessKey;

   private String secretKey;

   private String bucketName;
}
