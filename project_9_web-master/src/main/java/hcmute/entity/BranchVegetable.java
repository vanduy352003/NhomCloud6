package hcmute.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hcmute.embeddedId.BranchVegetableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branch_vegetable")
public class BranchVegetable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private BranchVegetableId branchVegetableId;
	
	@Column(name = "remain_quantity")
	private int remainQuantity;
	
	@ManyToOne
	@JoinColumn(name = "id_branch",insertable = false, updatable = false)
	private BranchEntity branchByBranchVegetable;
	
	@ManyToOne
	@JoinColumn(name = "id_vegetable",insertable = false, updatable = false)
	private VegetableEntity vegetableByBranchVegetable;

	public BranchVegetableId getBranchVegetableId() {
		return branchVegetableId;
	}

	public void setBranchVegetableId(BranchVegetableId branchVegetableId) {
		this.branchVegetableId = branchVegetableId;
	}

	public int getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(int remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public BranchEntity getBranchByBranchVegetable() {
		return branchByBranchVegetable;
	}

	public void setBranchByBranchVegetable(BranchEntity branchByBranchVegetable) {
		this.branchByBranchVegetable = branchByBranchVegetable;
	}

	public VegetableEntity getVegetableByBranchVegetable() {
		return vegetableByBranchVegetable;
	}

	public void setVegetableByBranchVegetable(VegetableEntity vegetableByBranchVegetable) {
		this.vegetableByBranchVegetable = vegetableByBranchVegetable;
	}

	public BranchVegetable(BranchVegetableId branchVegetableId, int remainQuantity, BranchEntity branchByBranchVegetable,
			VegetableEntity vegetableByBranchVegetable) {
		super();
		this.branchVegetableId = branchVegetableId;
		this.remainQuantity = remainQuantity;
		this.branchByBranchVegetable = branchByBranchVegetable;
		this.vegetableByBranchVegetable = vegetableByBranchVegetable;
	}

	public BranchVegetable() {
		super();
	}
	
}
