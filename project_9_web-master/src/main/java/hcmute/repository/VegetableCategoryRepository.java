package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.entity.VegetableEntity;

@Repository
public interface VegetableCategoryRepository extends JpaRepository<VegetableCategoryEntity, Integer>{
	List<VegetableCategoryEntity> findAll();
}
