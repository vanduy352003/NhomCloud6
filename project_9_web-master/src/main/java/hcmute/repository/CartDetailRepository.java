package hcmute.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hcmute.embeddedId.CartDetailId;
import hcmute.entity.CartDetailEntity;
import hcmute.entity.VegetableEntity;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, CartDetailId> {
	List<CartDetailEntity> findByCartByCartDetailIdCart(int idCart);

    @Query("SELECT cd.idCartDetail FROM CartDetailEntity cd WHERE cd.cartByCartDetail.idCart = :idCart")
    List<CartDetailId> findVegetableByCartId(int idCart);
    
    // Add new product to cart
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart_detail (id_cart, id_vegetable, size) VALUES (:idCart, :idVegetable, :size)", nativeQuery = true)
    void addProductToCart(@Param("idCart") int idCart, @Param("idVegetable") int idVegetable, @Param("size") String size);
 // Add new product to favorite
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart_detail (id_cart, id_vegetable) VALUES (:idCart, :idVegetable)", nativeQuery = true)
	void addProductToFavorite(@Param("idCart") int idCart, @Param("idVegetable") int idVegetable);
}
