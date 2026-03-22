package dev.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.store.models.Food;

public interface FoodRepositories extends JpaRepository<Food,Long>{

	Optional<Food> findByUuid(String uuid);
	
	void deleteByUuid(String uuid);
	boolean existsByUuid(String uuid);

}
