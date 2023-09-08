package com.chandara.phoneshop.Service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.chandara.phoneshop.DTO.ProductImportDTO;
import com.chandara.phoneshop.entity.Product;

public interface ProductService {
	Product create(Product product);
	Product getById(Long id);
	Product findByModelIdAndColorId(Long modelId,Long colorId);

	void importProduct(ProductImportDTO productImportDTO);
	void setSellPrice(Long productId, BigDecimal price);
	void validateStock(Long productId,Integer numberofUnit);
	Map<Integer, String> uploadProduct(MultipartFile file);
}
