package com.chandara.phoneshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chandara.phoneshop.entity.Brand;
import com.chandara.phoneshop.entity.Product;
public interface ProductRepository extends JpaRepository<Product,Long> ,JpaSpecificationExecutor<Product>{
	
	List<Brand>findByName(String name);
	
   Optional<Product>findByModelIdAndColorId(Long modelId,Long colorId);
}
