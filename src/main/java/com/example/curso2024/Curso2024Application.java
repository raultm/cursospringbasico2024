package com.example.curso2024;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.curso2024.services.PopulateDataService;

@SpringBootApplication
public class Curso2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Curso2024Application.class, args);
	}

	@Autowired
	PopulateDataService populateDataService;

	@EventListener(ApplicationReadyEvent.class)
	public void runOnStartUp() {
		populateDataService.execute();
	}

}
