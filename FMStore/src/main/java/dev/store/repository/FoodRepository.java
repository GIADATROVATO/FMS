package dev.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.store.model.Food;
import jakarta.persistence.LockModeType;
@Repository
public interface FoodRepository extends JpaRepository<Food,Long>{

	Optional<Food> findByUuid(String uuid);
	
	void deleteByUuid(String uuid);
	boolean existsByUuid(String uuid);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT f FROM Food f WHERE f.erpStatus = :status")
	List<Food> findByErpStatus(String string);
	/*
	 *   in questo modo 2 thread non possono leggere lo stesso record FAILED contemporaneamente 
	 *   List<Food> failedFoods= repository.findByErpStatus("FAILED")
	 *   se 2 thread fanno qeusta query nello stesso momento, entrambi ottengono lo stesso oggetto con stesso ID, mandando all'ERP doppio invio
	 *   --> un thread blocca finche non termina. 
	 *   Solo il primo thread aggiorna-> il secondo vede già lo stato cambiato -> non reinvia 
	 */
}
