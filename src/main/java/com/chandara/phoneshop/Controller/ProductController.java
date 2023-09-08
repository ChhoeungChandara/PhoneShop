package com.chandara.phoneshop.Controller;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chandara.phoneshop.DTO.PriceDTO;
import com.chandara.phoneshop.DTO.ProductDTO;
import com.chandara.phoneshop.DTO.ProductImportDTO;
import com.chandara.phoneshop.Service.ProductService;
import com.chandara.phoneshop.entity.Product;
import com.chandara.phoneshop.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {
	private final ProductService productService;
	private final ProductMapper  productMapper;
	
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ProductDTO productDTO ){
	Product product = productMapper.toProduct(productDTO);
	 product = productService.create(product);
	return ResponseEntity.ok(product);
	}
	@PostMapping("importProduct")
	public ResponseEntity<?> importProduct(@RequestBody @Valid ProductImportDTO productImportDTO ){
		productService.importProduct(productImportDTO);
	return ResponseEntity.ok("import success");
	}
	
	
	@PostMapping("{productId}/setPrice")
	public ResponseEntity<?> setSellPrice(@PathVariable Long productId,@RequestBody PriceDTO dto){
		productService.setSellPrice(productId, dto.getPrice());
	return ResponseEntity.ok("import success");
	}
	
	@PostMapping("uploadProduct")
	public ResponseEntity<?> uploadProduct(@RequestParam("file") MultipartFile file){
		Map<Integer, String> errorMap = productService.uploadProduct(file);
		return ResponseEntity.ok(errorMap);
	}
	
	
}
