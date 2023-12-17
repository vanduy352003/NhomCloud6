package hcmute.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vegetable")
public class VegetableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vegetable")
	private int idVegetable;

	@Column(name = "name", columnDefinition = "nvarchar(100)")
	private String name;

	@Column(name = "cost")
	private int cost;

	@Column(name = "description", columnDefinition = "nvarchar(1000)")
	private String description;

	@Column(name = "image", columnDefinition = "varchar(1000)")
	private String image;

	@ManyToOne
	@JoinColumn(name = "id_type")
	private VegetableTypeEntity vegetableTypeByVegetable;

	@OneToMany(mappedBy = "vegetableByCartDetail")
	private Set<CartDetailEntity> cartDetails;

	@OneToMany(mappedBy = "vegetableByOrderDetail")
	private Set<OrderDetailEntity> orderDetails;
	
	@OneToMany(mappedBy = "vegetableByBranchVegetable")
	private Set<BranchVegetable> branchVegetables;
}