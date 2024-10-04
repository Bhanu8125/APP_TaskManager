package org.springboot.taskmanager;

import org.modelmapper.ModelMapper;
import org.springboot.taskmanager.service.JwtTokenService;
import org.springboot.taskmanager.service.TokenService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public TokenService tokenService(){
		return new JwtTokenService();
	}
}
