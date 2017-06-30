package trainning.osms.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.CategoryController;
import trainning.osms.business.Order;
import trainning.osms.business.Product;
import trainning.osms.business.ProductController;
import trainning.osms.business.ProductSearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchProduct {
	private static final int PROD_PER_PAGE = 2;
	
	private @Autowired CategoryController catController;
	private @Autowired ProductController controller;
	private ProductPanel panel;
	private ProductSearchOptions options;
	private List<Product> products;
	private List<Integer> pages;
	private boolean prosaved;
	private boolean prodeleted;
	
	public String initialize() {
		options = new ProductSearchOptions();
		panel = new ProductPanel(catController);
		products = null;
		prosaved = false;
		setPages(null);
		return "searchProduct";

	}
	
	
	public SearchProduct(){
		
	}
	
	public ProductSearchOptions getOptions() {
		return options;
	}
	public void setOptions(ProductSearchOptions options) {
		this.options = options;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<Integer> getPages() {
		return pages;
	}
	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	public boolean isProsaved() {
		return prosaved;
	}
	public void setProsaved(boolean prosaved) {
		this.prosaved = prosaved;
	}
	public boolean isProdeleted() {
		return prodeleted;
	}
	public void setProdeleted(boolean prodeleted) {
		this.prodeleted = prodeleted;
	}
	
	public void searchProduct(){
		int prodcount = controller.searchProductCount(options);
		pages = new ArrayList<Integer>();
		int page = 1;
		while (prodcount > 0) {
			pages.add(page);
			++page;
			prodcount -= PROD_PER_PAGE;
		}

		if (pages.isEmpty()) {
			products = null;
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Nenhum Produto encontrado");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
		} else{
			goToPage(1);
		}

	}
	
	public void goToPage(int page){

		int firstResult = (page-1)* PROD_PER_PAGE;
		options.setFirstResult(firstResult);
		options.setMaxResults(PROD_PER_PAGE);
		products = controller.searchProduct(options);
		
	}
	
	public String editProduct(Product product){
		product = product.clone();
		panel.setProduct(product);
		this.prosaved = false;
		return "editProduct";
		
	}
	
	public void registerProduct() {
		FacesMessage message = new FacesMessage();
		//try {
			Product product = panel.getProduct();
			controller.registerProduct(product);
			prosaved = true;
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Produto alterado com sucesso");
/*		} catch (BusinessException e) {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(e.getMessage());

		}*/

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}
	
	public String deleteProduct(Product product) {
		this.prodeleted = false;
		panel.setProduct(product);
		return "deleteProduct";

	}

	public void deleteProduct() {
		FacesMessage message = new FacesMessage();
		//try {
			Product product = panel.getProduct();
			controller.deleteProduct(product);
			prodeleted = true;
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Produto removido com sucesso");
		/*} catch (BusinessException e) {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(e.getMessage());

		}*/

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}


	public ProductPanel getPanel() {
		return panel;
	}


	public void setPanel(ProductPanel panel) {
		this.panel = panel;
	}
	
	public String confirmarDelete(){
		Product product = panel.getProduct();
		List<Order> ords = product.getOrders();
		if(ords.isEmpty()){
			deleteProduct();
		}else
			return "Delete";
		return "";
	}

}
