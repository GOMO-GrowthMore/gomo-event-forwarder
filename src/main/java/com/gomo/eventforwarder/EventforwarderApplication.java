package com.gomo.eventforwarder;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EventforwarderApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(EventforwarderApplication.class, args);
		new CountDownLatch(1).await();
	}

}
