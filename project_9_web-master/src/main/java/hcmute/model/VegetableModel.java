package hcmute.model;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import hcmute.entity.BranchVegetable;
import hcmute.entity.CartDetailEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VegetableModel {
	private int idVegetable;
	private String name;
	private int cost;
	private String description;
	private int orderQuantity;
	private String image;
	private MultipartFile imageFile;

	// use for product detail
	private String vegetableType;
	private String size;
	private int vegetableTypeId;
	private int branchId;

	private VegetableTypeModel vegetableTypeByVegetable;
	private Set<CartDetailEntity> cartDetails;;
	private Set<BranchVegetable> branchVegetables;
	private Boolean isEdit = false;
}
