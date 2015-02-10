package com.QAndA;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.TemplateResolver;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URI;

@EnableAutoConfiguration
@ComponentScan
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



	@Bean
	public BasicDataSource dataSource() throws Exception{
		BasicDataSource basicDataSource = new BasicDataSource();
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}

}