/**
 * @BelongsProject: rpc-framework
 * @BelongsPackage: com.hym.consumer
 * @Author: 黄勇铭
 * @CreateTime: 2024-11-26  15:49
 * @Description: TODO
 * @Version: 1.0
 */
package com.hym.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerApplication.class,args);
    }
}