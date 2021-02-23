package io.assignment4.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
class GatewayApplication {

	static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args)
	}

	@Bean
	MyZuulFilter myZuulFilter()
	{
		return new MyZuulFilter();
	}
}
