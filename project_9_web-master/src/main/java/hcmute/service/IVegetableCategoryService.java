package hcmute.service;

import java.util.List;
import java.util.Optional;

import hcmute.entity.VegetableCategoryEntity;

public interface IVegetableCategoryService {

	List<VegetableCategoryEntity> findAll();
	
	Optional<VegetableCategoryEntity> findById(int id);
	
	<S extends VegetableCategoryEntity> S save(S entity);
}

	
