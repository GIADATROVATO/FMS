package dev.store.model;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name="foods")
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	//uuid è unico e non sarà modificabile(updatable ) 
	@Column(nullable=false,updatable=false,insertable=false)
	private String uuid;
	@Column(name="food_name", nullable=false)
	private String name;
	
	private String category; 
	private Integer calories; 
	private BigDecimal price; 
	private Boolean available;
	private LocalDate expirationDate;	 
	private LocalDateTime createdAt;

	public Food(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", uuid=" + uuid + ", name=" + name + ", category=" + category + ", calories="
				+ calories + ", price=" + price + ", available=" + available + ", expirationDate=" + expirationDate
				+ ", createdAt=" + createdAt + "]";
	}
}
