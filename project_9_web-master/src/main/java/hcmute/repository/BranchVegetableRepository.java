package hcmute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmute.embeddedId.BranchVegetableId;
import hcmute.entity.BranchVegetable;

@Repository
public interface BranchVegetableRepository extends JpaRepository<BranchVegetable, BranchVegetableId> {
	@Query("SELECT COALESCE(b.remainQuantity, 0) FROM BranchVegetable b WHERE b.branchVegetableId.idBranch = :idBranch AND b.branchVegetableId.idVegetable = :idVegetable AND b.branchVegetableId.size = :size")
	Optional<Integer> findRemainQuantityByBranchIdAndVegetableId(int idBranch, int idVegetable, String size);
}
