package trainning.osms.presentation;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.CategoryController;
import trainning.osms.business.Product;
import trainning.osms.business.ProductController;


@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NewProduct {
	private ProductPanel panel;
	//private Product product= new Product();
	private boolean productSaved;
	private @Autowired CategoryController catController;
	private @Autowired ProductController controller;

	
	
	public ProductPanel getPanel() {
		return panel;
	}


	public void setPanel(ProductPanel panel) {
		this.panel = panel;
	}
	
	public String initialize(){
		panel = new ProductPanel(catController);
		productSaved = false;
		return "newProduct";
	}


	public NewProduct(){
	}

	
	public void registerProduct(){
		FacesMessage message = new FacesMessage();
	
		
		//try{
			Product product = panel.getProduct();
			controller.registerProduct(product);
			productSaved = true;
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Produto registrado com sucesso");
			/*}
			catch(RuntimeException e){
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(e.getMessage());
				
			}*/
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,message);
		
	}

	public boolean isProductSaved() {
		return productSaved;
	}

	public void setProductSaved(boolean productSaved) {
		this.productSaved = productSaved;
	}

/*
	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	*/

}
