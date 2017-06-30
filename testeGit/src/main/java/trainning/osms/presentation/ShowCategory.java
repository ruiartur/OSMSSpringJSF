package trainning.osms.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Category;
import trainning.osms.business.CategoryController;
import trainning.osms.business.CategorySearchOptions;
import trainning.osms.business.Product;
import trainning.osms.business.ProductController;
import trainning.osms.business.ProductSearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ShowCategory {
	private static final int PROD_PER_PAGE = 20;

	private Category category;
	private List<Category> cats2;
	private String name;
	private List<Product> products;
	private List<Product> productsIndex;
	private @Autowired CategoryController controller;
	private @Autowired ProductController prodController;
	private List<Integer> pages;
	private ProductSearchOptions options;

	public ProductSearchOptions getOptions() {
		return options;
	}

	public void setOptions(ProductSearchOptions options) {
		this.options = options;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public void setCategoryName(String categoryName) {
		products = null;
		pages =  new ArrayList<Integer>();
		CategorySearchOptions options = new CategorySearchOptions();
		options.setName(categoryName);
		List<Category> cats = controller.searchCategory(options);
		if (cats.size() > 0) {
			category = cats.get(0);
			products = category.getProduct();
			this.options = new ProductSearchOptions();
			this.options.setCatID(category.getIdentifier());
			searchProduct();
		} else {
			category = null;
			products = null;
		}
	}

	public String getCategoryName() {
		if (category == null) {
			return null;
		} else {
			return category.getName();
		}
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public List<Category> getCats2() {
		cats2 = controller.searchAllCategories();
		return cats2;
	}

	public void setCats2(List<Category> cats2) {
		this.cats2 = cats2;
	}

	public String searchProduct(String name) {
		if (name == null || name.isEmpty()) {
			this.name = new String();
			return "resultNullSearch";
		} else {
			this.name = name;
			ProductSearchOptions options = new ProductSearchOptions();
			options.setName(name);
			products = prodController.searchProduct(options);
			if(products.isEmpty()){
				return "resultSearchEmpty";
			} else{
			this.name = new String();	
			return "resultSearch";
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void searchProduct(){
		int prodcount = prodController.searchProductCount(this.options);
		pages =  new ArrayList<Integer>();
		int page = 1;
		while (prodcount > 0) {
			pages.add(page);
			++page;
			prodcount -= PROD_PER_PAGE;
		}

		if (pages.isEmpty()) {
			products = null;
		} else{
			goToPage(1);
		}

	}
	
	public void goToPage(int page){

		int firstResult = (page-1)* PROD_PER_PAGE;
		options.setFirstResult(firstResult);
		options.setMaxResults(PROD_PER_PAGE);
		products = prodController.searchProduct(options);
		
	}
	

	public List<Product> getProductsIndex() {
		this.options = new ProductSearchOptions();
		searchProduct();
		productsIndex = products;
		return productsIndex;
	}

	public void setProductsIndex(List<Product> productsIndex) {
		this.productsIndex = productsIndex;
	}


}
