package hcmute.model;
import javax.persistence.Column;

import hcmute.embeddedId.BranchVegetableId;
import hcmute.entity.BranchEntity;
import hcmute.entity.VegetableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchVegetableModel {
	private BranchVegetableId branchVegetableId;
	private BranchEntity branchByBranchVegetable;
	private VegetableEntity vegetableByBranchVegetable;
	private int remainQuantity;
	private Boolean isEdit = false;
	private int idBranch;
	private int idVegetable;
	private String size;
}
