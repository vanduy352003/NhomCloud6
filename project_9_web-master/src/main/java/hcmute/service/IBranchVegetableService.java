package hcmute.service;

import java.util.List;
import java.util.Optional;

import hcmute.embeddedId.BranchVegetableId;
import hcmute.entity.BranchVegetable;

public interface IBranchVegetableService {

	Optional<Integer> findRemainQuantityByBranchIdAndVegetableId(int idBranch, int idVegetable, String size);

	Optional<BranchVegetable> findById(BranchVegetableId id);

	List<BranchVegetable> findAll();

	<S extends BranchVegetable> S save(S entity);

	void deleteAll();
}
