package com.mustr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;

import com.mustr.cluster.annotation.EnableMScheduling;

@SpringBootApplication(exclude = QuartzAutoConfiguration.class)
@EnableMScheduling
public class MustrQuartzBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MustrQuartzBootApplication.class, args);
	}

}
