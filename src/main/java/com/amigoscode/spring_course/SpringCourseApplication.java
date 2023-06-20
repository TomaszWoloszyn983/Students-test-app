package com.amigoscode.spring_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;


@SpringBootApplication
public class SpringCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCourseApplication.class, args);

		try {
			// Replace the command with the appropriate command to run your external application
			Process process = Runtime.getRuntime().exec("D:/Kodowanie/Keycloak/keycloak-14.0.0/bin/standalone.bat");

			// Optionally, you can wait for the process to complete
			int exitCode = process.waitFor();
			System.out.println("External application finished with exit code: " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
