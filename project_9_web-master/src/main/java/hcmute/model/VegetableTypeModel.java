package hcmute.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VegetableTypeModel{
	private int idType;
	private String name;
	private VegetableCategoryModel vegetableCategoryByMilkTeaType;
	private VegetableModel vegetables;
	private int idCategory;
	private Boolean isEdit = false;
	
}
