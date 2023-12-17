package hcmute.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import hcmute.embeddedId.CartDetailId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vegetable_type")
public class VegetableTypeEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_type")
	private int idType;
	
	@Column(name = "name",columnDefinition = "nvarchar(100)")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "id_category")
	private VegetableCategoryEntity vegetableCategoryByVegetableType;
	
	@OneToMany(mappedBy = "vegetableTypeByVegetable")
	private Set<VegetableEntity> vegetables;
}
