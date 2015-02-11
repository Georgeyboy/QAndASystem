package com.QAndA;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@ComponentScan("com.QAndA")
@Configuration
public class Config {




	/**
	 * Server Start point
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}
		System.setProperty("server.port", webPort);
		SpringApplication.run(Config.class, args);
	}



}