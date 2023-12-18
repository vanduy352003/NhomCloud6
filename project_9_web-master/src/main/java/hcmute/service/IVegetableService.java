package hcmute.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.query.Param;

import hcmute.entity.VegetableEntity;

public interface IVegetableService {
	Page<VegetableEntity> findAll(Pageable pageable);

	long count();

	//Page<VegetableEntity> findByNameContaining(@Param("name") String name, Pageable pageable);

	List<VegetableEntity> findFourProductsOutstanding();

	List<VegetableEntity> findAllByTypeId(int typeId);

	Optional<VegetableEntity> findByIdVegetable(int id);

	List<VegetableEntity> findRelevantProducts(@Param("typeId") int typeId, @Param("vegetableId") int vegetableId);

	List<VegetableEntity> findFiveProduct();

	List<VegetableEntity> findFiveProductOutstanding();

	Page<VegetableEntity> findByNameContainingAndSortAscendingByCost(@Param("name") String name, Pageable pageable);
	Page<VegetableEntity> findByNameContainingAndSortDescendingByCost(@Param("name") String name, Pageable pageable);


	List<VegetableEntity> findAll();
	
	List<VegetableEntity> findByNameContaining(@Param("name") String name);
	
	Page<VegetableEntity> findByNameContaining(@Param("name") String name, Pageable pageable);

	void sortByOrderDetailQuantity(List<VegetableEntity> vegetableList);

	int countByTypeId(int typeId);

	int countByNameContaining(@Param("name") String name);

	Page<VegetableEntity> findAllByTypeId(int idType, Pageable pageable);

	<S extends VegetableEntity> S save(S entity);
	Optional<Integer> findRemainQuantityByIdVegetableAndIdBranch(int idVegetable, int idBranch);

	Optional<VegetableEntity> findById(Integer id);

}
