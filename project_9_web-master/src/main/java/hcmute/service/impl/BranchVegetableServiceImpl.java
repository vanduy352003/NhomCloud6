package hcmute.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.embeddedId.BranchVegetableId;
import hcmute.entity.BranchVegetable;
import hcmute.repository.BranchVegetableRepository;
import hcmute.service.IBranchVegetableService;

@Service
public class BranchVegetableServiceImpl implements IBranchVegetableService{
	@Autowired
	BranchVegetableRepository branchVegetableRepository;

	public BranchVegetableServiceImpl(BranchVegetableRepository branchVegetableRepository) {
		this.branchVegetableRepository = branchVegetableRepository;
	}
	
	@Override
	public Optional<Integer> findRemainQuantityByBranchIdAndVegetableId(int idBranch, int idVegetable, String size) {
		return branchVegetableRepository.findRemainQuantityByBranchIdAndVegetableId(idBranch, idVegetable, size);
	}

	@Override
	public List<BranchVegetable> findAll() {
		return branchVegetableRepository.findAll();
	}

	@Override
	public Optional<BranchVegetable> findById(BranchVegetableId id) {
		return branchVegetableRepository.findById(id);
	}

	@Override
	public void deleteAll() {
		branchVegetableRepository.deleteAll();
	}

	@Override
	public <S extends BranchVegetable> S save(S entity) {
		return branchVegetableRepository.save(entity);
	}

	public void deleteById(BranchVegetableId id) {
		branchVegetableRepository.deleteById(id);
	}
	
}
