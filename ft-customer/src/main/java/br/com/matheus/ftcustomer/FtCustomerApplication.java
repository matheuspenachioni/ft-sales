package br.com.matheus.ftcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class FtCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtCustomerApplication.class, args);
	}

}
