package hcmute.controller.user;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import hcmute.embeddedId.CartDetailId;
import hcmute.entity.BranchEntity;
import hcmute.entity.CartDetailEntity;
import hcmute.entity.CartEntity;
import hcmute.entity.VegetableEntity;
import hcmute.model.VegetableModel;
import hcmute.model.OrderProduct;
import hcmute.model.OrderProduct.OrderItem;
import hcmute.service.IBranchVegetableService;
import hcmute.service.IBranchService;
import hcmute.service.ICartDetailService;
import hcmute.service.ICartService;
import hcmute.service.IVegetableService;
import hcmute.service.IStorageService;
import hcmute.service.impl.CookieServiceImpl;

@Controller
@RequestMapping("cart")
public class CartController {

	@Autowired
	ICartDetailService cartDetailService;

	@Autowired
	IVegetableService vegetableService;

	@Autowired
	IBranchService branchService;

	@Autowired
	IBranchVegetableService branchVegetableService;
	
	@Autowired
	CookieServiceImpl cookieServiceImpl;
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	private IStorageService storageService;
	
	private int getCartId(int idUser) {
		Optional<CartEntity> cartOpt = cartService.findCartsByUserId(idUser);
		CartEntity cart = cartOpt.get();
		return cart.getIdCart();
	}

	private List<VegetableModel> getList() {
		int idUser = Integer.parseInt(cookieServiceImpl.getValue("USER_ID"));
		int idCart = getCartId(idUser);
		List<CartDetailId> vegetables = cartDetailService.findVegetableByCartId(idCart);
		List<VegetableModel> listvegetables = new ArrayList<VegetableModel>();
		for (CartDetailId result : vegetables) {
			Optional<VegetableEntity> vegetable = vegetableService.findByIdVegetable(result.getIdVegetable());
			if (vegetable.isPresent()) {
				VegetableEntity entity = vegetable.get();
				String size = result.getSize();
				VegetableModel vegetableModel = new VegetableModel();
				BeanUtils.copyProperties(entity, vegetableModel);
				vegetableModel.setSize(size);
				listvegetables.add(vegetableModel);
			}
		}
		return listvegetables;
	}

	@GetMapping("")
	public String list(ModelMap model, @RequestParam(value = "status", required = false) String status) {
		model.addAttribute("listvegetables", this.getList());
		model.addAttribute("status", status);
		if (status != null) {
			if ("success".equals(status)) {
				model.addAttribute("message", "Xóa thành công");
			} else {
				model.addAttribute("message", "Xóa thất bại");
			}
		}
		return "user/cart";
	}

	@GetMapping("/check")
	public String check(ModelMap model, @RequestParam("data") String data, @RequestParam("noChoose") String noChoose) {
		if (noChoose.equals("true")) {
			model.addAttribute("message", "Quý khách chưa chọn sản phẩm để đặt hàng!");
			model.addAttribute("status", "fail");
			model.addAttribute("listvegetables", this.getList());
			return "user/cart";
		}
		String dataEncoded = data;
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		data = new String(decodedBytes, StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();
		List<BranchEntity> listBranches = branchService.findAll();
		List<Integer> listBranchesEligible = new ArrayList<Integer>();
		try {
			OrderProduct orderProduct = objectMapper.readValue(data, OrderProduct.class);
			Boolean isSuccess = false;
			for (BranchEntity branch : listBranches) {
				Boolean isChecked = true;
				for (OrderItem item : orderProduct.getList()) {
					int idVegetable = Integer.parseInt(item.getIdVegetable());
					Optional<VegetableEntity> entity = vegetableService.findByIdVegetable(idVegetable);
					if (entity.isPresent()) {
						int idBranch = branch.getIdBranch();
						Optional<Integer> remainQuantityOptional = branchVegetableService
								.findRemainQuantityByBranchIdAndVegetableId(idBranch, idVegetable, item.getSize());
						if (remainQuantityOptional.isPresent()) {
							if (remainQuantityOptional.get() < Integer.parseInt(item.getQuantity())) {
								isChecked = false;
								break;
							}
						} else {
							isChecked = false;
							break;
						}
					}
				}
				if (isChecked) {
					listBranchesEligible.add(branch.getIdBranch());
					isSuccess = true;
				} 
			}
			if (isSuccess) {
				String json = objectMapper.writeValueAsString(listBranchesEligible);
				byte[] bytes = json.getBytes();
				String base64Encoded = Base64.getEncoder().encodeToString(bytes);
				return "redirect:/payment?data=" + dataEncoded + "&listBranch=" + base64Encoded;
			} else {
				model.addAttribute("message",
						"Xin lỗi quý khách! Hiện tại toàn bộ các chi nhánh không có đủ số lượng đáp ứng cho toàn bộ sản phẩm bạn đã đặt hàng!");
				model.addAttribute("status", "fail");
				model.addAttribute("listvegetables", this.getList());
				return "user/cart";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@GetMapping("/delete")
	public String delete(ModelMap model, @RequestParam("idVegetable") int idVegetable, @RequestParam("size") String size) {
		int idUser = Integer.parseInt(cookieServiceImpl.getValue("USER_ID"));
		int idCart = getCartId(idUser);
		CartDetailId cartDetailId = new CartDetailId(idCart, idVegetable, size);
		Optional<CartDetailEntity> cartDetail = cartDetailService.findById(cartDetailId);
		if (cartDetail.isPresent()) {
			CartDetailEntity cartDetailEntity = cartDetail.get();
			cartDetailService.delete(cartDetailEntity);
			model.addAttribute("listvegetables", this.getList());
			return "redirect:/cart?status=success";
		} else {
			model.addAttribute("listvegetables", this.getList());
			return "redirect:/cart?status=fail";
		}
	}
	@GetMapping("/image/{filename:.+}")
	public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
