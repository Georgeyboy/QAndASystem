package com.QAndA.Controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
public class WelcomeController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
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
		tr.setPrefix("/web/pages/");
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


	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver vr = new ThymeleafViewResolver();
		vr.setTemplateEngine(templateEngine());

		return vr;
	}


	@Bean
	public URI dbUrl() throws Exception{
		URI uri = new URI("${DATABASE_URL}");
		return uri;
	}

	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("${database.driverClassName}");
		bds.setUrl("#{ 'jdbc:postgresql://' + @dbUrl.getHost() + ':' + @dbUrl.getPort() + @dbUrl.getPath() }");
		bds.setUsername("#{ @dbUrl.getUserInfo().split(':')[0] }");
		bds.setPassword("#{ @dbUrl.getUserInfo().split(':')[1] }");

		return bds;
	}

}