package com.QAndA;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.net.URI;
import java.net.URISyntaxException;

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
	public BasicDataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		System.out.println("URL : " + dbUrl);
		System.out.println("user : " + username);
		System.out.println("password : " + password);

		return basicDataSource;
	}

//	@Bean(name = "sessionFactory")
//	public SessionFactory sessionFactory(DataSource dataSource) {
//
//		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
//
//		//List persisted classes here
//
////		sessionBuilder.addAnnotatedClasses(User.class, Item.class);
//		sessionBuilder.addProperties(getHibernateProperties());
//
//		return sessionBuilder.buildSessionFactory();
//	}
//
//	private Properties getHibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.show_sql", "true");
//		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//		properties.put("hibernate.hbm2ddl.auto", "update");
//		return properties;
//	}
//
//	/**
//	 * Allows use of @Transactional tags
//	 * @param sessionFactory appropriate session factory
//	 * @return transactionManager
//	 */
//	@Bean
//	public HibernateTransactionManager TransactionManager(SessionFactory sessionFactory) {
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
//		return transactionManager;
//	}

}