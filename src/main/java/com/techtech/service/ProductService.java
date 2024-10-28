package com.techtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.ProductRepository;
import com.techtech.dto.ProductDTO;
import com.techtech.entity.ProductEntity;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void deleteById(int id) {
		productRepository.deleteById(id);
	}

	public List<ProductDTO> findAll() { 
		List<ProductEntity> listEntity = productRepository.findAll();
		return convertEntityDTO(listEntity);
	}

	public void save(ProductDTO productDTO) { 
		//data coming from controller
		//convert productDTO to ProductEntity
		//dataflow: dto to entity
		ProductEntity entity = new ProductEntity();//Respository can take input in ProductEntity format only
		BeanUtils.copyProperties(productDTO, entity);//copy data from ProductDTO to entity
		productRepository.save(entity);
	}
	
	public List<ProductDTO> searchProduct(String stext) {		
		List<ProductEntity> listEntity = productRepository.findByNameContainingOrCategoryContaining(stext, stext);
		return convertEntityDTO(listEntity);
	}
	
	//common method
	private List<ProductDTO> convertEntityDTO(List<ProductEntity> listEntity) { //convert entity to DTO
		//data is coming from DB so you need DTO
		//convert list of ProductEntity to ProductDTO
		//dataflow: repository to entity
		List<ProductDTO> dtos = new ArrayList<>();
		for(ProductEntity entity : listEntity) {
			ProductDTO dto = new ProductDTO();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}
}
