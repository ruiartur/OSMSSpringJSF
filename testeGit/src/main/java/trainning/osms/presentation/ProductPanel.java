package trainning.osms.presentation;

import java.util.List;

import javax.validation.constraints.NotNull;

import trainning.osms.business.Category;
import trainning.osms.business.CategoryController;
import trainning.osms.business.CategorySearchOptions;
import trainning.osms.business.Product;


public class ProductPanel {
	private List<Category> category;
	private Product product;
	@SuppressWarnings("unused")
	private CategoryController controller;
	
	
	public ProductPanel(CategoryController controller){
		this.controller = controller;
		
		product = new Product();
		
		CategorySearchOptions options = new CategorySearchOptions();
		category = controller.searchCategory(options);	
	}
	
	
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	
	
	public void setCategoryIdentifier(Integer categoriaIdentifier) {		
		for (Category categoria : category) {
			if (categoria.getIdentifier().equals(categoriaIdentifier)) {
			   product.setCategory(categoria);
			}
		}
	}

	@NotNull(message="Por favor selecione uma categoria")
	public Integer getCategoryIdentifier() {
		Category categoria = product.getCategory();
		if (categoria == null) {
			return null;
		} else {
			return categoria.getIdentifier();
		}
	}
	
	
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

}
