package hcmute.embeddedId;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BranchVegetableId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "id_branch")
	private int idBranch;
	
	@Column(name = "id_vegetable")
	private int idVegetable;
	
	@Column(name = "size", columnDefinition = "nvarchar(50)")
	private String size;
}
