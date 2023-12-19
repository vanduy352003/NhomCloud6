package hcmute.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hcmute.entity.VegetableCategoryEntity;
import hcmute.entity.VegetableEntity;
import hcmute.entity.VegetableTypeEntity;
import hcmute.service.IVegetableCategoryService;
import hcmute.service.IVegetableService;
import hcmute.service.IVegetableTypeService;

@Controller
@RequestMapping("products")
public class ProductsController {

	@Autowired
	IVegetableCategoryService vegetableCategoryService;
	@Autowired
	IVegetableTypeService vegetableTypeService;
	@Autowired
	IVegetableService vegetableService;

	@GetMapping("")
	public String showCategory(Model model, @RequestParam("page") Optional<Integer> page) {
		List<VegetableCategoryEntity> categories = vegetableCategoryService.findAll();
		List<List<VegetableTypeEntity>> types = new ArrayList<List<VegetableTypeEntity>>();
		model.addAttribute("categories", categories);
		for (VegetableCategoryEntity category : categories) {
			List<VegetableTypeEntity> categoriesWithTypes = vegetableTypeService
					.findAllByCategoryId(category.getIdCategory());
			types.add(categoriesWithTypes);
		}
		model.addAttribute("types", types);

		int count = (int) vegetableService.count();
		int currentPage = page.orElse(1);
		int pageSize = 6;

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("idVegetable"));
		Page<VegetableEntity> resultpaPage = null;

		resultpaPage = vegetableService.findAll(pageable);

		int totalPages = resultpaPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > count) {
				if (end == totalPages)
					start = end - count;
				else if (start == 1)
					end = start + count;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);

		}
		model.addAttribute("vegetables", resultpaPage);

		return "user/products";

	}

	@RequestMapping("type/{id}")
	public String getVegetableByType(Model model, @PathVariable("id") int typeId,
			@RequestParam("page") Optional<Integer> page) {
		List<VegetableCategoryEntity> categories = vegetableCategoryService.findAll();
		List<List<VegetableTypeEntity>> types = new ArrayList<List<VegetableTypeEntity>>();
		model.addAttribute("categories", categories);
		for (VegetableCategoryEntity category : categories) {
			List<VegetableTypeEntity> categoriesWithTypes = vegetableTypeService
					.findAllByCategoryId(category.getIdCategory());
			types.add(categoriesWithTypes);
		}
		model.addAttribute("types", types);

		int count = vegetableService.countByTypeId(typeId);
		int currentPage = page.orElse(1);
		int pageSize = 6;

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("idVegetable"));
		Page<VegetableEntity> resultpaPage = null;

		resultpaPage = vegetableService.findAllByTypeId(typeId, pageable);

		int totalPages = resultpaPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > count) {
				if (end == totalPages)
					start = end - count;
				else if (start == 1)
					end = start + count;
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);

		}
		model.addAttribute("vegetableByTypes", resultpaPage);
		model.addAttribute("idActive", typeId);
		return "user/products";
	}

}
