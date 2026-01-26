package dev.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.store.model.FoodDTO;
import dev.store.services.FoodServiceImpl;



@RestController
@RequestMapping("/api/foods")
//@CrossOrigin(origins="http://127.0.0.1.:5500")

public class FoodController {
	private final FoodServiceImpl foodService;
	public FoodController(FoodServiceImpl fs) {
		this.foodService=fs;
	}
	
	@GetMapping("/{uuid}")
	public FoodDTO getByUuid(@PathVariable String uuid) {
		return foodService.findByUuid(uuid)
				.orElseThrow(()-> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Food not found"
				));
	}
	
	@GetMapping
	public List<FoodDTO> findAll(){
		return foodService.findAll();
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public FoodDTO create(@RequestBody FoodDTO dto) {
		return foodService.create(dto);
	}
	@DeleteMapping("/{uuid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(String uuid) {
		boolean deleted= foodService.delete(uuid);
		if(!deleted) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	@PutMapping("/{uuid}")
	public FoodDTO update(@PathVariable String uuid, @RequestBody FoodDTO dto) {
		return foodService.update(uuid,dto)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
	}
}
