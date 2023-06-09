package com.atypon.master;

import com.atypon.master.LoadBalancer.LoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterApplication.class, args);
		// Load nodes in docker network
		LoadBalancer.loadNodes();

	}

}
