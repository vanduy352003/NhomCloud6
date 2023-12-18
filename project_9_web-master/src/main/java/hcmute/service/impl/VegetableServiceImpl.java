package hcmute.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hcmute.entity.VegetableEntity;
import hcmute.entity.OrderDetailEntity;
import hcmute.repository.VegetableRepository;
import hcmute.repository.OrderDetailRepository;
import hcmute.service.IVegetableService;

@Service
public class VegetableServiceImpl implements IVegetableService {
	@Autowired
	VegetableRepository VegetableRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	public VegetableServiceImpl(VegetableRepository VegetableRepository, OrderDetailRepository orderDetailRepository) {
		this.VegetableRepository = VegetableRepository;
		this.orderDetailRepository = orderDetailRepository;
	}


	@Override
	public Optional<VegetableEntity> findByIdVegetable(int id) {
		return VegetableRepository.findByIdVegetable(id);
	}

	@Override
	public List<VegetableEntity> findAll() {
		return VegetableRepository.findAll();
	}

	@Override
	public List<VegetableEntity> findRelevantProducts(int typeId, int VegetableId) {
		return VegetableRepository.findRelevantProducts(typeId, VegetableId);
	}

	@Override
	public List<VegetableEntity> findFiveProduct() {
		List<VegetableEntity> temp = VegetableRepository.findAll();
		List<VegetableEntity> list = new ArrayList<>();
		if (temp.size() >= 5) {
			list.addAll(temp.subList(0, 5));
		} else {
			list.addAll(temp);
		}
		return list;
	}

	@Override
	public List<VegetableEntity> findFiveProductOutstanding() {
		return VegetableRepository.findFiveProductOutstanding();
	}

	@Override
	public List<VegetableEntity> findFourProductsOutstanding() {
		return null;
	}


	@Override
	public long count() {
		return VegetableRepository.count();
	}

	@Override
	public Page<VegetableEntity> findAll(Pageable pageable) {
		return VegetableRepository.findAll(pageable);
	}
	
	@Override
	public List<VegetableEntity> findAllByTypeId(int typeId) {
		return VegetableRepository.findAllByTypeId(typeId);
	}



	@Override
	public int countByTypeId(int typeId) {
		return VegetableRepository.countByTypeId(typeId);
	}


	@Override
	public Page<VegetableEntity> findAllByTypeId(int idType, Pageable pageable) {
		return  VegetableRepository.findAllByTypeId(idType, pageable);
	}

	private int getQuantity(VegetableEntity Vegetable) {
		List<OrderDetailEntity> oderDetails = orderDetailRepository.findOrderDetailsByIDVegetable(Vegetable.getIdVegetable());
		if (oderDetails != null && !oderDetails.isEmpty()) {
			int res = 0;
			for (OrderDetailEntity orderDetail : oderDetails) {
				res += orderDetail.getQuantity();
			}
			return res;
		}
		return 0;
	}

	@Override
	public void sortByOrderDetailQuantity(List<VegetableEntity> VegetableList) {
		try {
			int n = VegetableList.size();
	        for (int i = 0; i < n - 1; i++) {
	            for (int j = 0; j < n - i - 1; j++) {
	                int quantity1 = getQuantity(VegetableList.get(j));
	                int quantity2 = getQuantity(VegetableList.get(j + 1));

	                if (quantity1 < quantity2) {
	                    VegetableEntity temp = VegetableList.get(j);
	                    VegetableList.set(j, VegetableList.get(j + 1));
	                    VegetableList.set(j + 1, temp);
	                }
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public <S extends VegetableEntity> S save(S entity) {
		if (entity.getIdVegetable() == 0) {
			return VegetableRepository.save(entity);
		} else {
			Optional<VegetableEntity> opt = findById(entity.getIdVegetable());
			if (opt.isPresent()) {
				if (StringUtils.isEmpty(entity.getImage())) {
					entity.setImage(opt.get().getImage());
				} else {
					entity.setImage(entity.getImage());
				}
			}
			return VegetableRepository.save(entity);
		}
	}
	
	@Override
	public Optional<Integer> findRemainQuantityByIdVegetableAndIdBranch(int idVegetable, int idBranch) {
		return null;
	}


	@Override
	public Page<VegetableEntity> findByNameContaining(String name, Pageable pageable) {
		return VegetableRepository.findBynameContaining(name, pageable);
	}


	@Override
	public int countByNameContaining(String name) {
		return VegetableRepository.countByNameContaining(name);
	}


	@Override
	public List<VegetableEntity> findByNameContaining(String name) {
		return VegetableRepository.findByNameContaining(name);
	}


	@Override
	public Page<VegetableEntity> findByNameContainingAndSortAscendingByCost(String name, Pageable pageable) {
		return VegetableRepository.findByNameContainingAndSortAscendingByCost(name, pageable);
	}


	@Override
	public Page<VegetableEntity> findByNameContainingAndSortDescendingByCost(String name, Pageable pageable) {
		return VegetableRepository.findByNameContainingAndSortDescendingByCost(name, pageable);
	}


	@Override
	public Optional<VegetableEntity> findById(Integer id) {
		return VegetableRepository.findById(id);
	}

}
