package dev.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.store.model.Food;
import dev.store.model.FoodDTO;
import dev.store.repository.FoodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class FoodServiceImpl implements FoodService{
	private final FoodRepository repository;
	@PersistenceContext EntityManager entityManager;
	
	public FoodServiceImpl(FoodRepository repository) {
		this.repository=repository;
	}
	@Override
	public FoodDTO create(FoodDTO dto) {
		Food food= new Food();
		mapToEntity(food,dto);				//mi prendo dto e do food 
		Food saved= repository.save(food);	// visto che food è mappato fa una refresh 
		entityManager.refresh(saved);
		return mapToDto(saved);
	}

	@Override
	public List<FoodDTO> findAll() {
		return repository.findAll().stream().map(this::mapToDto).toList();
	}

	
	@Override
	public Optional<FoodDTO> findByUuid(String uuid) {
		return repository.findByUuid(uuid).map(this::mapToDto);
	}

	
	@Override
	public Optional<FoodDTO> update(String uuid, FoodDTO dto) {
		Optional<Food> foodOpt= repository.findByUuid(uuid);
		if(foodOpt.isEmpty())
				return Optional.empty();
		Food food= foodOpt.get();			// se l'optional c'è , lo estraggo e trasferisco 
		mapToEntity(food,dto);
		return Optional.of(mapToDto(food));
	}

	
	@Override
	public boolean delete(String uuid) {
		Optional<Food>food= repository.findByUuid(uuid);
		if(food.isEmpty())
			return false;
			repository.delete(food.get());
		return true;
	}
	
	
//MAPPING	
	
	private FoodDTO mapToDto(Food food) {
		FoodDTO dto=new FoodDTO();
		if(food!=null) 
			dto.setUuid(food.getUuid());
			dto.setName(food.getName());
			dto.setCate(food.getCategory());
			dto.setCalo(food.getCalories());
			dto.setPric(food.getPrice());
			dto.setAvai(food.getAvailable());
			dto.setExpi(food.getExpirationDate());
			dto.setCreate(food.getCreatedAt());
		
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
