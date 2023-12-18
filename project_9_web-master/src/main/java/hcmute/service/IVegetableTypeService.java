package hcmute.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import hcmute.entity.VegetableTypeEntity;

public interface IVegetableTypeService {

	Page<VegetableTypeEntity> findByidTypeContaining(int idType, Pageable pageable);

	Page<VegetableTypeEntity> findAll(Pageable pageable);

	long count();
	
	List<VegetableTypeEntity> findAllByCategoryId(int categoryId);

	<S extends VegetableTypeEntity> S save(S entity);

	Optional<VegetableTypeEntity> findById(Integer id);

	List<VegetableTypeEntity> findAll();

}
