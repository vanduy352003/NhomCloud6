package hcmute.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hcmute.entity.BranchEntity;
import hcmute.entity.VegetableEntity;
import hcmute.entity.VegetableTypeEntity;
import hcmute.model.BranchModel;
import hcmute.model.VegetableModel;
import hcmute.service.IVegetableService;
import hcmute.service.IVegetableTypeService;
import hcmute.service.IStorageService;

@Controller
@RequestMapping("admin/vegetable")
public class VegetableAdminController {

	@Autowired
	private IVegetableService vegetableService;

	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private IVegetableTypeService vegetableTypeService;

	@GetMapping("")
	public String IndexViewVegetable(ModelMap model) {
		List<VegetableEntity> vegetables = vegetableService.findAll();
		model.addAttribute("vegetables", vegetables);
		return "admin/view/view-vegetable";
	}
	@GetMapping("add")
	public String add(ModelMap model) {
		VegetableModel vegetable = new VegetableModel();
		vegetable.setIsEdit(false);
		model.addAttribute("vegetable", vegetable);
		return "admin/customize/customize-vegetable";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("vegetable") VegetableModel vegetable,
			BindingResult result,@RequestParam("imageFile") MultipartFile imageFile) {
		
		if (vegetable != null) {
			VegetableEntity entity = new VegetableEntity();
			if (vegetable.getName() != null) {
				entity.setName(vegetable.getName());
			}
			entity.setCost(vegetable.getCost());
			if (vegetable.getDescription() != null) {
				entity.setDescription(vegetable.getDescription());
			}
			if (vegetable.getImage() != null) {
				entity.setImage(vegetable.getImage());
			}
			Optional<VegetableTypeEntity> opt = vegetableTypeService.findById(vegetable.getVegetableTypeId());
			entity.setVegetableTypeByVegetable(opt.get());
			if(!vegetable.getImageFile().isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				entity.setImage(storageService.getStorageFilename(vegetable.getImageFile(), uuString));
				storageService.store(vegetable.getImageFile(), entity.getImage());
			}
			vegetableService.save(entity);
			String message = vegetable.getIsEdit() ? "vegetable đã được cập nhật thành công"
					: "vegetable đã được thêm thành công";
			model.addAttribute("message", message);
		} else {
			model.addAttribute("message", "Không thể lưu vegetable với dữ liệu null");
		}
		return new ModelAndView("redirect:/admin/vegetable", model);
	}

	@GetMapping("/image/{filename:.+}")
	public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	@GetMapping("edit/{idBranch}")
	public ModelAndView edit(ModelMap model, @PathVariable("idBranch") int idBranch) {
		Optional<VegetableEntity> opt = vegetableService.findById(idBranch);
		VegetableModel vegetable = new VegetableModel();
		if (opt.isPresent()) {
			VegetableEntity entity = opt.get();
			BeanUtils.copyProperties(entity, vegetable);
			vegetable.setIsEdit(true);
			model.addAttribute("vegetable", vegetable);
			return new ModelAndView("admin/customize/customize-vegetable", model);
		}

		model.addAttribute("message", "Branch không tồn tại");
		return new ModelAndView("forward:/admin/vegetable", model);
	}

}
