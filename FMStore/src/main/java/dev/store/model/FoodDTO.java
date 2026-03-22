package dev.store.model;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodDTO {
	@NotNull
	private String uuid;
	@NotNull
	private String name;
	private String cate; 
	@Positive
	private Integer calo; 
	@Positive
	private BigDecimal pric; 
	private Boolean avai;
	private LocalDate expi;	
	private LocalDateTime create;

	public FoodDTO(){}

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

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public Integer getCalo() {
		return calo;
	}

	public void setCalo(Integer calo) {
		this.calo = calo;
	}

	public BigDecimal getPric() {
		return pric;
	}

	public void setPric(BigDecimal pric) {
		this.pric = pric;
	}

	public Boolean getAvai() {
		return avai;
	}

	public void setAvai(Boolean avai) {
		this.avai = avai;
	}

	public LocalDate getExpi() {
		return expi;
	}

	public void setExpi(LocalDate expi) {
		this.expi = expi;
	}

	public LocalDateTime getCreate() {
		return create;
	}

	public void setCreate(LocalDateTime create) {
		this.create = create;
	}
}
