package hcmute.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hcmute.embeddedId.CartDetailId;
import hcmute.entity.VegetableEntity;
import hcmute.model.VegetableModel;
import hcmute.service.ICartDetailService;
import hcmute.service.IVegetableService;

@Controller
@RequestMapping("product_favorite")
public class ProductFavoriteController {

	@Autowired
	ICartDetailService cartDetailService;

	@Autowired
	IVegetableService vegetableService;

	private List<VegetableModel> getLists() {
		List<CartDetailId> vegetables = cartDetailService.findVegetableByCartId(1);
		List<VegetableModel> listvegetables = new ArrayList<VegetableModel>();
		for (CartDetailId result : vegetables) {
			Optional<VegetableEntity> vegetable = vegetableService.findByIdVegetable(result.getIdVegetable());
			if (vegetable.isPresent()) {
				VegetableEntity entity = vegetable.get();
				VegetableModel vegetableModel = new VegetableModel();
				BeanUtils.copyProperties(entity, vegetableModel);
				listvegetables.add(vegetableModel);
			}
		}
		return listvegetables;
	}

	@GetMapping("")
	public String list(ModelMap model, @RequestParam(value = "status", required = false) String status) {
		model.addAttribute("listvegetables", this.getLists());
		model.addAttribute("status", status);
		return "user/favorite";
	}
}
