package com.example.javakafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@EnableKafka
@SpringBootApplication
@RestController
public class JavaKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaKafkaApplication.class, args);
    }

    @Resource
    KafkaProducerService kafkaProducerService;

    @GetMapping("send")
    public Object o(String topic, String key, String msg) throws ExecutionException, InterruptedException, TimeoutException {
        kafkaProducerService.sendMessageSync(topic, key, msg);
        return "send!";
    }

}
