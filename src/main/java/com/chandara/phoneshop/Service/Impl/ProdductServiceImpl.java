package com.chandara.phoneshop.Service.Impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chandara.phoneshop.DTO.ProductImportDTO;
import com.chandara.phoneshop.Service.ProductService;
import com.chandara.phoneshop.entity.Product;
import com.chandara.phoneshop.entity.ProductImportHistories;
import com.chandara.phoneshop.exception.ApiException;
import com.chandara.phoneshop.exception.ResourceNotFoundException;
import com.chandara.phoneshop.mapper.ProductMapper;
import com.chandara.phoneshop.repository.ProductImportHistoryRepository;
import com.chandara.phoneshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ProdductServiceImpl implements ProductService{
	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository importHistoryRepository;
	private final ProductMapper productMapper;

	@Override
	public Product create(Product product) {
	String name = "%s%s ".formatted(product.getModel().getName(),product.getColor().getName());
		product.setName(name);
		return productRepository.save(product);
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findById(id)
				                .orElseThrow(()-> new ResourceNotFoundException("product", id));
	}

	@Override
	public void importProduct(ProductImportDTO productImportDTO) {  
		Product product = getById(productImportDTO.getProductId());
		
		Integer availableUnit = 0;
		if(product.getAvialableUnit()!=null) {
			availableUnit=product.getAvialableUnit();
		}
		product.setAvialableUnit(availableUnit+productImportDTO.getProductUnit());
		
		productRepository.save(product);
		// save product import history
		ProductImportHistories ImportHistories = productMapper.toProductImportHistories(productImportDTO, product);
	    importHistoryRepository.save(ImportHistories);
	}

	@Override
	public void setSellPrice(Long productId, BigDecimal price) {
		Product  product = getById(productId);
		product.setSalePrice(price);
		productRepository.save(product);
	}

	@Override
	public void validateStock(Long productId, Integer numberofUnit) {
		
	}

	@Override
	public Product findByModelIdAndColorId(Long modelId, Long colorId) {
		return productRepository.findByModelIdAndColorId(modelId,colorId)
                .orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST,"bad request"));
	}

	@Override
	public Map<Integer, String> uploadProduct(MultipartFile file) {
		Map<Integer, String> map = new HashedMap();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheet("product");
			Iterator<Row> rowIterator = sheet.iterator();
			
			rowIterator.next(); // @TODO improve checking error
			
			while(rowIterator.hasNext()) {
				Integer rowNumber = 0;
				try {
					Row row = rowIterator.next();
					int cellIndex = 0;
					
					Cell cellNo = row.getCell(cellIndex++);
					rowNumber = (int) cellNo.getNumericCellValue();
					
					Cell cellModelId = row.getCell(cellIndex++);
					Long modelId =  (long) cellModelId.getNumericCellValue();
					
					Cell cellColorId = row.getCell(cellIndex++); 
					Long colorId =  (long) cellColorId.getNumericCellValue();
					
					Cell cellImportPrice = row.getCell(cellIndex++);
					Double importPrice =  cellImportPrice.getNumericCellValue();
					
					Cell cellImportUnit = row.getCell(cellIndex++);
					Integer importUnit =  (int) cellImportUnit.getNumericCellValue();
					if(importUnit < 1) {
						throw new ApiException(HttpStatus.BAD_REQUEST, "Unit must be greater than 0");
					}
					
					Cell cellImportDate = row.getCell(cellIndex++);
					LocalDateTime importDate = cellImportDate.getLocalDateTimeCellValue();
					
					Product product = findByModelIdAndColorId(modelId, colorId);
					
					
					//System.out.println(modelId);
					Integer availableUnit = 0;
					if(product.getAvialableUnit() != null) {
						availableUnit = product.getAvialableUnit();
					}
					product.setAvialableUnit(availableUnit + importUnit);
					productRepository.save(product);
					
					// save product import history
					//ProductImportHistory importHistory = productMapper.toProductImportHistory(importDTO, product);
					ProductImportHistories importHistory = new ProductImportHistories();
					importHistory.setDateImport(importDate);
					importHistory.setImport_unit(importUnit);
					importHistory.setPricePerUnit(BigDecimal.valueOf(importPrice));
					importHistory.setProduct(product);
					importHistoryRepository.save(importHistory);
				}catch(Exception e) {
					map.put(rowNumber, e.getMessage());
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}


	


}
