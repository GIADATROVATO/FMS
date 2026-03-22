package dev.store.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.store.model.Food;
import dev.store.model.FoodDTO;
import dev.store.repository.FoodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;




@Service
@Transactional
public class FoodServiceImpl implements FoodService{
	private final FoodRepository repository;
	private final ErpService erpService;
	
	public FoodServiceImpl(FoodRepository repository, ErpService erpService) {
		this.repository=repository;
		this.erpService=erpService;
	}
	
	
	
	// ===================================
	//	CREATE  
	// ===================================
	@Override
	public FoodDTO create(FoodDTO dto) {
		Food food= new Food();
		mapToEntity(food,dto);			
		food.setErpStatus("PENDING");
		Food saved= repository.save(food);	 
		erpService.sendToErp(saved);
		return mapToDto(saved);
	}

	

	// ===================================
	//	UPDATE 
	// ===================================
	@Override
	public Optional<FoodDTO> update(String uuid, FoodDTO dto) {
		Optional<Food> foodOpt= repository.findByUuid(uuid);
		if(foodOpt.isEmpty())
				return Optional.empty();
		Food food= foodOpt.get();			
		mapToEntity(food,dto);			//aggiorno, quindi sovrascrivo 
		food.setErpStatus("PENDING");
		repository.save(food);
		erpService.sendToErp(food);
		return Optional.of(mapToDto(food));
	}
	
	
	// ===================================
	//	DELETE  
	// ===================================
	@Override
	public boolean delete(String uuid) {
		Optional<Food>foodOpt= repository.findByUuid(uuid);
		if(foodOpt.isEmpty())
			return false;
		Food food=foodOpt.get();
		try {
			erpService.deleteFromErp(food);
			repository.delete(food);	
			return true;
		}catch(Exception e) {
			food.setErpStatus("FAILED");
			repository.save(food);
			return false;
		}
	}
		/*
		 * 	Il db locale è il mio gestionale, l'ERP è un sistema esterno, se cancello dal DB locale ma non dall'ERP, 
		 * 	rimane sporco nell'ERP, quindi devo sincronizzare anche l'ERP 
		 */
	
	
	// ===================================
	//	RETRY   		riprovo a inviare all'ERP solo quelli che prima sono falliti
	// ===================================	
	public void retryFailed() {
		List<Food> failed= repository.findByErpStatus("FAILED");
		for(Food f: failed) {
			erpService.sendToErp(f);
		}
	}
	
/*	
	// ===================================
	//	SEND TO ERP (riutilizzabile)   
	// ===================================
	
	@Async
	public void sendToErp(Food food) {
		try {
			webClient
					.post()
					.uri("/api/foods")
					.bodyValue(mapToDto(food))
					.retrieve()
					.bodyToMono(String.class)
			//		.timeout(Duration.ofSecond(3))		
					.block();
		
			food.setErpStatus("SENT");
		}catch(Exception e) {		
			food.setErpStatus("FAILED");
		}
		repository.save(food);
	}
		
*/
	// ===================================
	//	READ  
	// ===================================
	@Override
	public List<FoodDTO> findAll() {
		return repository.findAll().stream().map(this::mapToDto).toList();
	}

	@Override
	public Optional<FoodDTO> findByUuid(String uuid) {
		return repository.findByUuid(uuid).map(this::mapToDto);
	}
	
	// ===================================
	//	MAPPING   
	// ===================================
	FoodDTO mapToDto(Food food) {
		FoodDTO dto=new FoodDTO();
		if(food!=null) {
			dto.setUuid(food.getUuid());
			dto.setName(food.getName());
			dto.setCate(food.getCategory());
			dto.setCalo(food.getCalories());
			dto.setPric(food.getPrice());
			dto.setAvai(food.getAvailable());
			dto.setExpi(food.getExpirationDate());
			dto.setCreate(food.getCreatedAt());
		}
		return dto;
	}
	private void mapToEntity(Food food, FoodDTO dto) { 		//l'entità esiste già, quindi passo anche Food, però uuid e created At non devono essere modificati
		
		food.setName(dto.getName());
		food.setCategory(dto.getCate());
		food.setCalories(dto.getCalo());
		food.setPrice(dto.getPric());
		food.setAvailable(dto.getAvai());
		food.setExpirationDate(dto.getExpi());

	}

}
