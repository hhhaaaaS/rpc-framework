/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.producer
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-26  15:48
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }
}