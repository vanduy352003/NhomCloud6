package hcmute.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.repository.VegetableCategoryRepository;
import hcmute.service.IVegetableCategoryService;

@Service
public class VegetableCategoryServiceImpl implements IVegetableCategoryService{
	
	@Autowired
	VegetableCategoryRepository VegetableCategoryRepository;

	public VegetableCategoryServiceImpl(VegetableCategoryRepository VegetableCategoryRepository) {
		this.VegetableCategoryRepository = VegetableCategoryRepository;
	}
	
	@Override
    public List<VegetableCategoryEntity> findAll() {
        return VegetableCategoryRepository.findAll();
    }

	@Override
	public Optional<VegetableCategoryEntity> findById(int id) {
		return VegetableCategoryRepository.findById(id);
	}

	@Override
	public <S extends VegetableCategoryEntity> S save(S entity) {
		return VegetableCategoryRepository.save(entity);
	}


	
}
