package com.chandara.phoneshop.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="product_import_history")
public class ProductImportHistories{
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "import_id")
	private Long id;
	@Column(name="date_import")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateImport;
	@Column(name="import_unit")
	private Integer import_unit;
	@Column(name="price_per_unit")
	private BigDecimal PricePerUnit;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

}
