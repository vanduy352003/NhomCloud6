package hcmute.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.entity.VegetableTypeEntity;
import hcmute.model.VegetableTypeModel;
import hcmute.service.IVegetableCategoryService;
import hcmute.service.IVegetableTypeService;

@Controller
@RequestMapping("admin/vegetable-type")
public class VegetableTypeAdminController {

	@Autowired
	private IVegetableTypeService vegetableTypeService;
	
	@Autowired
	private IVegetableCategoryService vegetableCategoryService;

	@GetMapping("")
	public String IndexViewVegetableType(ModelMap model) {
		List<VegetableTypeEntity> vegetableTypes = vegetableTypeService.findAll();
		model.addAttribute("vegetableTypes", vegetableTypes);
		return "admin/view/view-vegetable-type";
	}
	@GetMapping("add")
	public String add(ModelMap model) {
		VegetableTypeModel vegetableType = new VegetableTypeModel();
		vegetableType.setIsEdit(false);
		model.addAttribute("vegetableType", vegetableType);
		return "admin/customize/customize-vegetable-type";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("vegetableType") VegetableTypeModel vegetableType,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/customize/customize-vegetable-type");
		}
		if (vegetableType != null) {
            VegetableTypeEntity entity = new VegetableTypeEntity();
            entity.setIdType(vegetableType.getIdType());
            Optional<VegetableCategoryEntity> opt = vegetableCategoryService.findById(vegetableType.getIdCategory());
            if (vegetableType.getName() != null) {
                entity.setName(vegetableType.getName());
            }
            entity.setVegetableCategoryByVegetableType(opt.get());
            vegetableTypeService.save(entity);
            String message = vegetableType.getIsEdit() ? "vegetableType đã được cập nhật thành công" : "vegetableType đã được thêm thành công";
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Không thể lưu vegetableType với dữ liệu null");
        }
		return new ModelAndView("redirect:/admin/vegetable-type", model);
	}
}
