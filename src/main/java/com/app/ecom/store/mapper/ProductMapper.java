package com.app.ecom.store.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import com.app.ecom.store.dto.ProductDto;
import com.app.ecom.store.dto.jaxb.ProductType;
import com.app.ecom.store.dto.jaxb.ProductsType;
import com.app.ecom.store.model.Product;
import com.app.ecom.store.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	public List<ProductDto> productsToProductDtos(List<Product> products) {
		List<ProductDto> productDtos = new ArrayList<>();
		products.stream().forEach(product -> productDtos.add(productToProductDto(product)));
		return productDtos;
	}

	public ProductDto productToProductDto(Product product) {
		return productToProductDto(product, false);
	}

	public ProductDto productToProductDto(Product product, boolean isCart) {
		if(null == product) {
			return null;
		}
		
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setCode(product.getCode());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setBrandName(product.getBrandName());
		productDto.setCategoryId(product.getCategoryId());
		productDto.setQuantity(isCart ? 1 : product.getQuantity());
		productDto.setAlertQuantity(product.getAlertQuantity());
		productDto.setPurchasePrice(product.getPurchasePrice());
		productDto.setPerProductPrice(product.getPerProductPrice());
		productDto.setBase64Image(Base64.getEncoder().encodeToString(product.getImage()));
		return productDto;
	}

	public Product productDtoToProduct(ProductDto productDto) {
		if(null == productDto) {
			return null;
		}
		
		Product product = new Product();
		product.setId(productDto.getId());
		product.setCode(productDto.getCode());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setBrandName(productDto.getBrandName());
		product.setCategoryId(productDto.getCategoryId());
		product.setQuantity(productDto.getQuantity());
		product.setAlertQuantity(productDto.getAlertQuantity());
		product.setPurchasePrice(productDto.getPurchasePrice());
		product.setPerProductPrice(productDto.getPerProductPrice());
		try {
			product.setImage(null != productDto.getImage() ? productDto
					.getImage().getBytes() : null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> convertProductsTypeToProducts(ProductsType productsType) {
		if (productsType == null) {
			return Collections.emptyList();
		}

		List<Product> products = new ArrayList<>();
		productsType.getProductTypes().stream().forEach(productType -> products.add(convertProductTypeToProduct(productType)));
		return products;
	}
	
	public Product convertProductTypeToProduct(ProductType productType) {
		if(null == productType) {
			return null;
		}
		
		Product product = new Product();
		product.setName(productType.getName());
		product.setCode(productType.getCode());
		product.setBrandName(productType.getBrandName());
		product.setImage("".getBytes());
		product.setCategoryId(productCategoryService.getCategoryIdByName(productType.getCategory()));
		product.setQuantity(Integer.parseInt(productType.getQuantity()));
		product.setAlertQuantity(Integer.parseInt(productType.getAlertQuantity()));
		product.setPurchasePrice(Double.parseDouble(productType.getPurchasePrice()));
		product.setPerProductPrice(Double.parseDouble(productType.getSellPrice()));
		return product;
	}
	
	public List<Product> convertCsvFileToProducts(InputStream is) {
		List<Product> products = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))){
			String line = null;
			int count = 1;
			while ((line = reader.readLine()) != null) {
				String[] attrs = line.split(",");
				if(count != 1){
					Product product = new Product();
					product.setCategoryId(productCategoryService.getCategoryIdByName(attrs[0]));
					product.setName(attrs[1]);
					product.setCode(attrs[2]);
					product.setBrandName(attrs[3]);
					product.setImage("".getBytes());
					product.setQuantity(Integer.parseInt(attrs[4]));
					product.setAlertQuantity(Integer.parseInt(attrs[5]));
					product.setPurchasePrice(Double.parseDouble(attrs[6]));
					product.setPerProductPrice(Double.parseDouble(attrs[7]));
					products.add(product);
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}
}
