package hcmute.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vegetable_category")
public class VegetableCategoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_category")
	private int idCategory;
	
	@Column(name = "name",columnDefinition = "nvarchar(100)")
	private String name;	
	
	@OneToMany(mappedBy = "vegetableCategoryByVegetableType")
	private Set<VegetableTypeEntity> vegetableTypes;

}
