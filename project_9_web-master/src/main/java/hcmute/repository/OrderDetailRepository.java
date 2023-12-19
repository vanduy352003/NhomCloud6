package hcmute.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hcmute.embeddedId.OrderDetailId;
import hcmute.entity.VegetableEntity;
import hcmute.entity.OrderDetailEntity;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, OrderDetailId>{
	@Query(value = "SELECT * FROM order_detail WHERE id_vegetable = :VegetableID", nativeQuery = true)
    List<OrderDetailEntity> findOrderDetailsByIDVegetable(@Param("VegetableID") int VegetableID);
	
	// Statistics quantity of each vegetable type in this month
	@Query(value = "SELECT mt.name, SUM(od.quantity)"
			+ "FROM order_detail od "
			+ "JOIN vegetable mt ON od.id_vegetable = mt.id_vegetable "
			+ "JOIN user_order o ON od.id_order = o.id_order "
			+ "WHERE MONTH(o.order_day) = MONTH(GETDATE()) AND YEAR(o.order_day) = YEAR(GETDATE()) "
			+ "GROUP BY mt.id_vegetable, mt.name", nativeQuery = true)
	List<Object[]> getQuantityByVegetableType();
}
