package hcmute.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hcmute.entity.VegetableEntity;

@Repository
public interface VegetableRepository extends JpaRepository<VegetableEntity, Integer> {
	// find a product by id
	@Query("SELECT mt FROM VegetableEntity mt WHERE mt.VegetableTypeByVegetable.idType = :typeId")
	List<VegetableEntity> findAllByTypeId(int typeId);

	Optional<VegetableEntity> findByIdVegetable(int id);

	@Query(value = "SELECT * FROM vegetable WHERE id_vegetable IN "
			+ "(SELECT TOP 5 id_vegetable FROM order_detail GROUP BY id_vegetable ORDER BY SUM(quantity) DESC)", nativeQuery = true)
	List<VegetableEntity> findFiveProductOutstanding();

	List<VegetableEntity> findAll();

	Page<VegetableEntity> findAll(Pageable pageable);

	// find relevant products
	// choose 4 products has the same type except the current product
	@Query("SELECT m FROM VegetableEntity m WHERE m.VegetableTypeByVegetable.idType = :typeId AND m.idVegetable <> :VegetableId")
	List<VegetableEntity> findRelevantProducts(@Param("typeId") int typeId, @Param("VegetableId") int VegetableId);

	long count();

	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI)", nativeQuery = true)
	List<VegetableEntity> findByNameContaining(@Param("name") String name);

	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI) "
			+ "ORDER BY cost ASC", nativeQuery = true)
	List<VegetableEntity> findByNameContainingAndSortAscendingByCost(@Param("name") String name);

	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI) "
			+ "ORDER BY cost DESC", nativeQuery = true)
	List<VegetableEntity> findByNameContainingAndSortDescendingByCost(@Param("name") String name);

	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI)", nativeQuery = true)
	Page<VegetableEntity> findBynameContaining(@Param("name") String name, Pageable pageable);
	
	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI) "
			+ "ORDER BY cost ASC", nativeQuery = true)
	Page<VegetableEntity> findByNameContainingAndSortAscendingByCost(@Param("name") String name, Pageable pageable);

	@Query(value = "SELECT * " + "FROM vegetable " + "WHERE "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE Vietnamese_CI_AI) OR "
			+ "(LOWER(name) LIKE CONCAT('%', :name, '%') COLLATE SQL_Latin1_General_CP1253_CI_AI) "
			+ "ORDER BY cost DESC", nativeQuery = true)
	Page<VegetableEntity> findByNameContainingAndSortDescendingByCost(@Param("name") String name, Pageable pageable);

	@Query("SELECT COUNT(mt) FROM VegetableEntity mt WHERE mt.VegetableTypeByVegetable.idType = :typeId")
	int countByTypeId(int typeId);

	@Query(value = "SELECT COUNT(*) FROM vegetable WHERE LOWER(cast(name as varchar(1000)) collate SQL_Latin1_General_Cp1251_CS_AS) LIKE LOWER(CONCAT('%', cast(:name as varchar(1000)) collate SQL_Latin1_General_Cp1251_CS_AS, '%'))", nativeQuery = true)
	int countByNameContaining(@Param("name") String name);

	@Query("SELECT mt FROM VegetableEntity mt WHERE mt.VegetableTypeByVegetable.idType = :idType")
	Page<VegetableEntity> findAllByTypeId(int idType, Pageable pageable);
}
