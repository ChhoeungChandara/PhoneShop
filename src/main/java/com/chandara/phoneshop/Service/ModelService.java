package com.chandara.phoneshop.Service;
import java.util.List;

import com.chandara.phoneshop.entity.Model;

public interface ModelService {
	Model save(Model model);
	List<Model> getByBrand(Long brandId);
	Model getById(Long id);
	void delete(Long id);
}
