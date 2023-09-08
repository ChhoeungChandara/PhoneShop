package com.chandara.phoneshop.Service;
import com.chandara.phoneshop.DTO.SaleDTO;
import com.chandara.phoneshop.entity.Sale;
public interface SaleService {
	void sell(SaleDTO saleDTO);
	Sale getById(Long saleId);
	void cancelSale(Long saleId);

}
