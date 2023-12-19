package hcmute.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import hcmute.entity.VegetableEntity;
import hcmute.service.IVegetableCategoryService;
import hcmute.service.IVegetableService;
import hcmute.service.IVegetableTypeService;

@Controller
@RequestMapping("header")
public class HeaderController {
	@Autowired
	IVegetableCategoryService vegetableCategoryService;
	@Autowired
	IVegetableTypeService vegetableTypeService;
	@Autowired
	IVegetableService vegetableService;

	@GetMapping("/search")
	public String showCategory(Model model, @RequestParam("page") Optional<Integer> page) {
		int count = (int) vegetableService.count();
		int currentPage = page.orElse(1);
		int pageSize = 8;

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id_vegetable"));
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
		return "user/search";
	}

	@RequestMapping("search/content={name}")
	public String getVegetableByNameContaining(@PathVariable("name") String encodedName, Model model,
			@RequestParam("page") Optional<Integer> page) {
		String name;
		try {
			name = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString());
			model.addAttribute("content", name);
			int count = vegetableService.countByNameContaining(name);
			int currentPage = page.orElse(1);
			int pageSize = 8;

			Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id_vegetable"));
			Page<VegetableEntity> resultpaPage = null;
			resultpaPage = vegetableService.findByNameContaining(name, pageable);
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
			model.addAttribute("vegetablesByNames", resultpaPage);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "user/search";
	}

	@RequestMapping("search/content={name}/method={method}")
	public String searchAndSort(@PathVariable("name") String encodedName, @PathVariable("method") String method,
			Model model, @RequestParam("page") Optional<Integer> page) {
		try {
			String name = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString());
			model.addAttribute("content", name);

			int count = vegetableService.countByNameContaining(name);
			int currentPage = page.orElse(1);
			int pageSize = 8;

			Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id_vegetable"));
			Page<VegetableEntity> resultPage = vegetableService.findByNameContaining(name, pageable);
			int totalPages = resultPage.getTotalPages();
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
			model.addAttribute("vegetableBySorts", resultPage);

			if ("outstanding".equals(method)) {
				List<VegetableEntity> vegetables = vegetableService.findByNameContaining(name);
				vegetableService.sortByOrderDetailQuantity(vegetables);
				// Chuyển đổi List<VegetableEntity> sang Page<VegetableEntity>
				// Tính toán index bắt đầu và kết thúc của trang
				int start = (int) pageable.getOffset();
				int end = Math.min((start + pageable.getPageSize()), vegetables.size());
				// Tạo một sublist của danh sách đã sắp xếp để giữ lại các phần tử của trang cụ thể
				List<VegetableEntity> pagedVegetables = vegetables.subList(start, end);
				// Tạo một đối tượng PageImpl mới với danh sách đã cắt và thông tin phân trang từ resultPage
				Page<VegetableEntity> vegetablePage = new PageImpl<>(pagedVegetables, pageable, vegetables.size());
				model.addAttribute("vegetableBySorts", vegetablePage);
				
			} else if ("low-to-high".equals(method)) {
				Pageable sortPageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("cost").ascending());
				Page<VegetableEntity> vegetables = vegetableService.findByNameContaining(name, sortPageable);
				model.addAttribute("vegetableBySorts", vegetables);
				
			} else if ("high-to-low".equals(method)) {
				Pageable sortPageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("cost").descending());
				Page<VegetableEntity> vegetables = vegetableService.findByNameContaining(name, sortPageable);
				model.addAttribute("vegetableBySorts", vegetables);
			}

			return "user/search";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@GetMapping("/moveToSearchPage")
	public RedirectView moveToSearchPage(RedirectAttributes redirectAttributes,
			@RequestParam("content") String content) {
		try {
			String encodedContent = URLEncoder.encode(content, StandardCharsets.UTF_8.toString());
			return new RedirectView("/header/search/content=" + encodedContent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new RedirectView("/error");
		}
	}

}
