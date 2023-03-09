package com.api.videostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VideoStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoStoreApplication.class, args);
	}

}
