package dev.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/foods")

public class FoodController {
	private final FoodServiceImpl foodService;
	public FoodController(FoodServiceImpl fs) {
		this.foodService=fs;
	}
	
	
	// =========================
	// CREATE 
	// =========================
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FoodDTO create(@RequestBody @Valid FoodDTO dto) {	
		return foodService.create(dto);
	}
		/*	Le validazioni nel DTO servono a controllare i dati in ingresso prima che arrivino al livello di persistenza
		 *  evitando errori lato database e fornendo risposte HTTP più chiare e immediate al client.
		 */
	
	
	// =========================
	// RETRY 
	// =========================	
	@GetMapping("/retry")
	public String retry() {
		foodService.retryFailed();
		return "Retry eseguito";
	}
	
	// =========================
	// GET BY UUID
	// =========================
	@GetMapping("/{uuid}")
	public FoodDTO getByUuid(@PathVariable String uuid) {
		return foodService.findByUuid(uuid)
			.orElseThrow(()->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found"));
	}	
	
	// =========================
	// GET ALL
	// =========================
	@GetMapping
	public List<FoodDTO> findAll(){
		return foodService.findAll();
	}
	
	// =========================
	// UPDATE
	// =========================
	@PutMapping("/{uuid}")
	public FoodDTO update(@PathVariable String uuid, @RequestBody FoodDTO dto) {
		return foodService.update(uuid,dto)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
	}
	
	// =========================
	// DELETE
	// =========================
	@DeleteMapping("/{uuid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String uuid) {
		boolean deleted= foodService.delete(uuid);
		if(!deleted) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	/*
	 *  ResponseStatusException è una class di Spring che permette di lanciare un'eccezione direttamente associata
	 *  ad un HTTP Status.
	 *  Se qualcosa va storto restituisci questo codice http e magari un messaggio
	 */
}
