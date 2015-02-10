package com.QAndA;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.util.Properties;


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


	/**
	 * Heroku specific Basic Data Source
	 * @return
	 * @throws Exception
	 */
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

//	@Bean
//	public SessionFactory sessionFactory() throws Exception{
//		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
//		sf.setDataSource(dataSource());
//
//		Properties hibernateProperties = new Properties();
//		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		hibernateProperties.setProperty("hibernate.hbm2ddl", "update");
//
//		sf.setHibernateProperties(hibernateProperties);
//
//		return sf;
//	}

	@Bean
	public JpaTransactionManager transactionManager() throws Exception{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		transactionManager.setEntityManagerFactory(entityManagerFactory());

		return transactionManager;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws Exception{
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl", "update");
		emf.setJpaProperties(hibernateProperties);

		return (EntityManagerFactory) emf;
	}

}