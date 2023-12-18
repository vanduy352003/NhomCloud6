package hcmute.model;
import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VegetableCategoryModel{
	private int idCategory;
	private String name;	
	private Set<VegetableTypeModel> vegetableTypes;
	private Boolean isEdit = false;
}
