package hcmute.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.entity.VegetableTypeEntity;
import hcmute.service.IVegetableCategoryService;
import hcmute.service.IVegetableService;
import hcmute.service.IVegetableTypeService;

@Controller
@RequestMapping("")
public class RedirectController {

	@Autowired
	IVegetableCategoryService vegetableCategoryService;
	@Autowired
	IVegetableTypeService vegetableTypeService;
	@Autowired
	IVegetableService vegetableService;

	@GetMapping("")
	public String LoadHeader(Model model) {
		List<VegetableCategoryEntity> categories = vegetableCategoryService.findAll();
		List<List<VegetableTypeEntity>> types = new ArrayList<List<VegetableTypeEntity>>();
		model.addAttribute("categories", categories);
		for (VegetableCategoryEntity category : categories) {
			List<VegetableTypeEntity> categoriesWithTypes = vegetableTypeService
					.findAllByCategoryId(category.getIdCategory());
			types.add(categoriesWithTypes);
		}
		model.addAttribute("types", types);
		return "user/header/header";
	}

	@RequestMapping("/")
	public String Index() {
		return "redirect:/security/login";
	}

	@GetMapping(value = "/test")
	public String IndexTest() {
		return "user/test";
	}
//	@GetMapping(value="/admin/city/add")
//	public String CustomizeCity() {
//		return "admin/customize/customize-city";
//	}
//	@GetMapping(value="/admin/city")
//	public String ViewCity() {
//		return "admin/view/view-city";
//	}
}
