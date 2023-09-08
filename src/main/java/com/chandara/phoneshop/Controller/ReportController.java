package com.chandara.phoneshop.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chandara.phoneshop.Service.SaleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("report")
public class ReportController {
	
	private final SaleService saleService;
	
	@GetMapping("{startDate}/{endDate}")
	public ResponseEntity<?> productSold(){
		
		return ResponseEntity.ok().build();
	}

}
