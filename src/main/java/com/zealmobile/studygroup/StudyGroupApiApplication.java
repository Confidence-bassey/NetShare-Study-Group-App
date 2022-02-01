package com.zealmobile.studygroup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.zealmobile.studygroup")
public class StudyGroupApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyGroupApiApplication.class, args);
	}

}
