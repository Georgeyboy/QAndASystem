package com.QAndA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by George on 26/02/2015.
 * http://www.mkyong.com/spring-security/spring-security-hibernate-annotation-example/
 */
@Configuration
@EnableWebSecurity
public class SecurityLoginConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		auth.inMemoryAuthentication().withUser("g").password("g").roles("ADMIN"); //For testing without a db
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
//		Protected (admin) pages to go here if necessary
		http
				.authorizeRequests()

				.antMatchers("/**", "/basic/**").permitAll()
				.antMatchers("/css/**", "/js/**", "/avatars/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin().failureUrl("/?loginerror")
				.loginPage("/")
				.defaultSuccessUrl("/basic/")
				.permitAll()
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll();

	}



	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
