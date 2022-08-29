package br.com.stefanini.stefaninifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
//@EnableSpringDataWebSupport - PAGINAÇÃO
@EnableCaching
public class StefaninifoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(StefaninifoodApplication.class, args);
	}

}
