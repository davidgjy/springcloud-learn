package org.genesis.springcloud.sleuth.consumer.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配置类, 用于注册RestTemplate和ExecutorService;
 */
@Configuration
public class ConsumerConfiguration {
    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExecutorService executorService() {
        // 简单起见, 我们注册固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        return new TraceableExecutorService(this.beanFactory, executorService);
    }
}

