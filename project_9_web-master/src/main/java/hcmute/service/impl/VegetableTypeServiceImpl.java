package hcmute.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.entity.VegetableTypeEntity;
import hcmute.repository.VegetableTypeRepository;
import hcmute.service.IVegetableTypeService;

@Service
public class VegetableTypeServiceImpl implements IVegetableTypeService{
	@Autowired
	VegetableTypeRepository VegetableTypeRepository;

	@Override
	public long count() {
		return VegetableTypeRepository.count();
	}

	@Override
	public Page<VegetableTypeEntity> findAll(Pageable pageable) {
		return VegetableTypeRepository.findAll(pageable);
	}

	@Override
	public Page<VegetableTypeEntity> findByidTypeContaining(int idType, Pageable pageable) {
		return VegetableTypeRepository.findByidTypeContaining(idType, pageable);
	}

	@Override
	public List<VegetableTypeEntity> findAllByCategoryId(int categoryId) {
		return VegetableTypeRepository.findAllByCategoryId(categoryId);
	}

	@Override
	public <S extends VegetableTypeEntity> S save(S entity) {
		return VegetableTypeRepository.save(entity);
	}
	
	@Override
	public Optional<VegetableTypeEntity> findById(Integer id) {
		return VegetableTypeRepository.findById(id);
	}

	@Override
	public List<VegetableTypeEntity> findAll() {
		return VegetableTypeRepository.findAll();
	}
	
	
	
}
