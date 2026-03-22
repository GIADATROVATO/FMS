package dev.store.services;

import java.util.List;
import java.util.Optional;

import dev.store.models.FoodDTO;

public interface FoodService {
	FoodDTO create(FoodDTO dto);
	List<FoodDTO> findAll();
	Optional<FoodDTO> findByUuid(String uuid);
	Optional<FoodDTO> update(String uuid, FoodDTO dto);
	boolean delete(String uuid);
}
