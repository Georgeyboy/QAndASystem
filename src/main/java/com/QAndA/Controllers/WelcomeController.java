package com.QAndA.Controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.apache.commons.dbcp2.BasicDataSource;

import java.net.URI;

@Controller
@EnableAutoConfiguration
@ComponentScan("com.QAndA")
@Configuration
public class WelcomeController {

	@RequestMapping("/")
	String home() {
		return "index";
	}


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
		SpringApplication.run(WelcomeController.class, args);
	}

	@Bean
	public TemplateResolver templateResolver(){
		ServletContextTemplateResolver tr = new ServletContextTemplateResolver();
		tr.setPrefix("/templates");
		tr.setSuffix(".html");
		tr.setTemplateMode("HTML5");

		return tr;
	}

	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine te = new SpringTemplateEngine();
		te.setTemplateResolver(templateResolver());

		return te;
	}
//
//
//	@Bean
//	public ThymeleafViewResolver thymeleafViewResolver(){
//		ThymeleafViewResolver vr = new ThymeleafViewResolver();
//		vr.setTemplateEngine(templateEngine());
//
//		return vr;
//	}


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