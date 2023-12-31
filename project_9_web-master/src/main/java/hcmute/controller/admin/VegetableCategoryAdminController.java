package hcmute.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.model.VegetableCategoryModel;

import hcmute.service.IVegetableCategoryService;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/category")
public class VegetableCategoryAdminController {

	@Autowired
	private IVegetableCategoryService vegetableCategoryService;

	@GetMapping("")
	public String indexViewCategory(ModelMap model) {
		List<VegetableCategoryEntity> categories = vegetableCategoryService.findAll();
		model.addAttribute("categories", categories); // Updated attribute name to "branches"
		return "admin/view/view-vegetable-category";
	}

	@GetMapping("add")
	public String add(ModelMap model) {
		VegetableCategoryModel category = new VegetableCategoryModel();
		category.setIsEdit(false);
		model.addAttribute("category", category);
		return "admin/customize/customize-vegetable-category";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") VegetableCategoryModel category,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/customize/customize-vegetable-category");
		}
		if (category != null) {
			VegetableCategoryEntity entity = new VegetableCategoryEntity();
			entity.setIdCategory(category.getIdCategory());
			System.out.println(category.getIdCategory());
			System.out.println(category.getName());
			if (category.getName() != null) {
				entity.setName(category.getName());
			}

			vegetableCategoryService.save(entity);
			String message = category.getIsEdit() ? "Danh mục đã được cập nhật thành công"
					: "Danh mục đã được thêm thành công";
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", "Không thể lưu  với dữ liệu null");
		}

		return new ModelAndView("redirect:/admin/category", model);
	}

	@GetMapping("edit/{idCategory}")
	public ModelAndView edit(ModelMap model, @PathVariable("idCategory") int idCategory) {
		Optional<VegetableCategoryEntity> opt = vegetableCategoryService.findById(idCategory);
		VegetableCategoryModel category = new VegetableCategoryModel();
		if (opt.isPresent()) {
			VegetableCategoryEntity entity = opt.get();
			BeanUtils.copyProperties(entity, category);
			category.setIsEdit(true);
			model.addAttribute("category", category);
			return new ModelAndView("admin/customize/customize-vegetable-category", model);
		}

		model.addAttribute("message", "Danh mục không tồn tại không tồn tại");
		return new ModelAndView("forward:/admin/category", model);
	}
}
