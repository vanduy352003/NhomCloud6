package hcmute.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hcmute.entity.VegetableTypeEntity;

@Repository
public interface VegetableTypeRepository extends JpaRepository<VegetableTypeEntity, Integer> {
	@Query("SELECT mt FROM VegetableTypeEntity mt WHERE mt.VegetableCategoryByVegetableType.idCategory = :categoryId")
	List<VegetableTypeEntity> findAllByCategoryId(int categoryId);
	
	long count();
	Page<VegetableTypeEntity> findByidTypeContaining(int idType, Pageable pageable);
	Page<VegetableTypeEntity> findAll(Pageable pageable);
}
