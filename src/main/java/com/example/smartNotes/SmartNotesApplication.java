package com.example.smartNotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//This combines 3 @configuration for marking this class as the source bean. Also @EnableAutoConfiguration for configuring other classes that are not manually configured and annotated with @configuration, finally we have the @componenet scan which helps in discovering the other service classes, component classes and other controllers
@SpringBootApplication
public class SmartNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartNotesApplication.class, args);
	}

}
