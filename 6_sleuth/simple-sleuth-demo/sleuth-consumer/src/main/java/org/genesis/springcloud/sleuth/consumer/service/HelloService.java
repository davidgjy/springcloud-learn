package org.genesis.springcloud.sleuth.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sleuth-provider", url = "localhost:8083")
public interface HelloService {
    @RequestMapping("/sayHello")
    String sayHello(@RequestParam("name") String name);
}
