package hcmute.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



import hcmute.embeddedId.BranchVegetableId;
import hcmute.entity.BranchEntity;
import hcmute.entity.BranchVegetable;
import hcmute.entity.BranchVegetable;
import hcmute.entity.VegetableEntity;
import hcmute.model.BranchVegetableModel;

import hcmute.service.IBranchVegetableService;
import hcmute.service.IBranchService;
import hcmute.service.IVegetableService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("admin/branch-vegetable")
public class BranchVegetableAdminController {

	@Autowired
	private IBranchVegetableService branchVegetableService;
	
	@Autowired
	private IBranchService branchService;
	
	@Autowired
	private IVegetableService vegetableService;
	
	@GetMapping("")
	public String indexViewBranchVegetable(ModelMap model) {
		List<BranchVegetable> branchVegetables = branchVegetableService.findAll();
		model.addAttribute("branchVegetables", branchVegetables); // Updated attribute name to "branches"
		return "admin/view/view-branch-vegetable";
	}

	@GetMapping("add")
	public String add(ModelMap model) {
		BranchVegetableModel branchVegetable = new BranchVegetableModel();
		branchVegetable.setIsEdit(false);
		model.addAttribute("branchVegetable", branchVegetable);
		return "admin/customize/customize-branch-vegetable";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("branchVegetable") BranchVegetableModel branchVegetable,
			BindingResult result) {
		if (branchVegetable != null) {
			BranchVegetable entity = new BranchVegetable();
			BranchVegetableId branchVegetableId = new BranchVegetableId(branchVegetable.getIdBranch(),branchVegetable.getIdVegetable(),branchVegetable.getSize());
			entity.setBranchVegetableId(branchVegetableId);
			entity.setRemainQuantity(branchVegetable.getRemainQuantity());
			
			Optional<VegetableEntity> opt_vegetable = vegetableService.findById(branchVegetable.getIdVegetable());
			Optional<BranchEntity> opt_branch = branchService.findById(branchVegetable.getIdBranch());
			entity.setBranchByBranchVegetable(opt_branch.get());
			entity.setVegetableByBranchVegetable(opt_vegetable.get());
			branchVegetableService.save(entity);
			String message = branchVegetable.getIsEdit() ? "Branch Milk Tea đã được cập nhật thành công"
					: "Branch Milk Tea đã được thêm thành công";
			model.addAttribute("message", message);
		}else {
			model.addAttribute("message", "Không thể lưu Branch với dữ liệu null");
		}
		return new ModelAndView("redirect:/admin/branch-vegetable", model);
	}
	@GetMapping("edit/{idBranch}/{idVegetable}/{size}")
	public ModelAndView edit(ModelMap model, @PathVariable("idBranch") int idBranch,@PathVariable("idVegetable") int idVegetable,@PathVariable("size") String size) {
		BranchVegetableId branchVegetableId = new BranchVegetableId(idBranch,idVegetable,size);
		Optional<BranchVegetable> opt = branchVegetableService.findById(branchVegetableId);
		BranchVegetableModel branchVegetable = new BranchVegetableModel();
		if (opt.isPresent()) {
			BranchVegetable entity = opt.get();
			branchVegetable.setBranchByBranchVegetable(entity.getBranchByBranchVegetable());
			branchVegetable.setIdBranch(entity.getBranchByBranchVegetable().getIdBranch());
			branchVegetable.setVegetableByBranchVegetable(entity.getVegetableByBranchVegetable());
			branchVegetable.setIdVegetable(entity.getVegetableByBranchVegetable().getIdVegetable());
			branchVegetable.setSize(entity.getBranchVegetableId().getSize());
			branchVegetable.setRemainQuantity(entity.getRemainQuantity());
			branchVegetable.setIsEdit(true);
			model.addAttribute("branchVegetable", branchVegetable);
			return new ModelAndView("admin/customize/customize-branch-vegetable", model);
		}
		model.addAttribute("message", "Branch không tồn tại");
		return new ModelAndView("forward:/admin/branch-vegetable", model);
	}
}
