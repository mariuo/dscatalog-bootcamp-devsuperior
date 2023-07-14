package com.devsuperior.dscatalog.tests.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.factory.ProductFactory;

//@SpringBootTest
//@Transactional
public class ProductServiceIT {
	
	@Autowired
	private ProductService service;
	
	private long existingId;
	private long nonExistingId;
	
	
	private ProductDTO productDTO;
	private PageRequest pagerequest;
	
	private long countTotalProducts;
	private long countPCGamerProducts;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 1000L;
		productDTO = ProductFactory.createProductDTO();	
		countTotalProducts = 25L;
		countPCGamerProducts = 21L;
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		Long categoryId=0L;
		String name = "";
		PageRequest pageRquest = PageRequest.of(0,10);
		
		Page<ProductDTO> result = service.findAllPaged(categoryId, name, pageRquest);
		
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());					
	}
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.findById(existingId);
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.findById(nonExistingId);
		});
		
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.update(nonExistingId, productDTO);
		});
	}
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		ProductDTO dto = new ProductDTO();
		
		ProductDTO result = service.update(existingId,dto);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingId);
		});		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(()->{
			service.delete(existingId);			
		});		
	}
	@Test
	public void findAllPagedShouldReturnAllProductsWhenNameIsEmpty() {
		String name = "";
		
		Page<ProductDTO> result = service.findAllPaged(0L, name, pagerequest);
	
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnProductsWhenNameExistsIgnoringCase() {
		String name = "pc gAMeR";
		
			
		Page<ProductDTO> result = service.findAllPaged(0L, name, pagerequest);
	
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnProductsWhenNameExists() {
		String name = "PC Gamer";
			
		Page<ProductDTO> result = service.findAllPaged(0L, name, pagerequest);
	
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
	}

}
