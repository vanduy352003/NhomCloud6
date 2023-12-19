package hcmute.model;

import java.io.Serializable;

import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailModel{
	private String size;
	private CartModel cartByCartDetail;
	private VegetableModel vegetableByCartDetail;
	private int id_cart;
	private int id_vegetable;
	private Boolean isEdit = false;
}
