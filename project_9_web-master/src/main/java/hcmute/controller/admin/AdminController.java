package hcmute.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import hcmute.entity.BranchEntity;
import hcmute.entity.UserEntity;
import hcmute.service.IBranchService;
import hcmute.service.IVegetableService;
import hcmute.service.IOrderDetailService;
import hcmute.service.IOrderService;
import hcmute.service.IUserRoleService;

@Controller
@RequestMapping("admin")
public class AdminController {
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IVegetableService vegetableService;
	@Autowired IOrderDetailService orderDetailService;
	
	@GetMapping("index")
	public String Index(ModelMap model) throws JsonProcessingException, UnsupportedEncodingException {
		int countUser = userRoleService.countUser();
		int countOrder = orderService.count();
		int countProduct = (int) vegetableService.count();
		
		model.addAttribute("countUser", countUser );
		model.addAttribute("countOrder", countOrder);
		model.addAttribute("countProduct", countProduct);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
        List<Object[]> revenueDataByDay = orderService.getRevenueByDay();
//        String revenueDataByDayJson = objectMapper.writeValueAsString(revenueDataByDay);
        List<Map<String, Object>> formattedRevenueDataByDay = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Object[] data : revenueDataByDay) {
            Map<String, Object> formattedData = new HashMap<>();
            Date orderDate = (Date) data[0]; // Assume data[0] is a java.sql.Date
            formattedData.put("order_date", dateFormat.format(orderDate));
            formattedData.put("total_price", data[1]);
            formattedRevenueDataByDay.add(formattedData);
        }
        
        String revenueDataByDayJson = objectMapper.writeValueAsString(formattedRevenueDataByDay);
        
        List<Object[]> revenueDataByMonth = orderService.getRevenueByMonth();
        String revenueDataByMonthJson = objectMapper.writeValueAsString(revenueDataByMonth);
        
        List<Object[]> quantityDataOfVegetableType = orderDetailService.getQuantityByVegetableType();
        String quantityDataOfVegetableTypeJson = objectMapper.writeValueAsString(quantityDataOfVegetableType);
        // Encode the Vietnamese characters in the JSON string
        quantityDataOfVegetableTypeJson = URLEncoder.encode(quantityDataOfVegetableTypeJson, "UTF-8");
        
        model.addAttribute("revenueDataByDayJson", revenueDataByDayJson);
        model.addAttribute("revenueDataByMonthJson", revenueDataByMonthJson);
        model.addAttribute("quantityDataOfVegetableTypeJson", quantityDataOfVegetableTypeJson);
		
		return "admin/index";
	}

}
