package dev.store.services;

import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.store.model.Food;
import dev.store.repository.FoodRepository;

@Service
public class ErpService {
	private final FoodRepository repository;
	private final WebClient webClient;
	public ErpService(FoodRepository repository,WebClient webClient ) {
		this.repository=repository;
		this.webClient= WebClient.create("http://localhost:8081");
		
		}
	@Async
	public void sendToErp(Food food) {
		try {
			webClient
			.post()
			.uri("/api/foods")
			.bodyValue(food)
			.retrieve()
			.bodyToMono(String.class)
	//		.timeout(Duration.ofSecond(3))		
			.block();
			food.setErpStatus("SENT");
		}catch(Exception e){
			food.setErpStatus("FAILED");
		}
		repository.save(food);
	}
	
	
	public void deleteFromErp(Food food) {
		try {
			webClient
				.delete().uri("/api/foods/"+ food.getId()).retrieve().bodyToMono(Void.class).block();
			repository.delete(food);			
		}catch(Exception e) {
			food.setErpStatus("FAILED");
			repository.save(food);
		}
	}
}
