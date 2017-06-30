package trainning.osms.presentation;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Product;
import trainning.osms.business.ProductController;
import trainning.osms.business.ProductSearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ShowProduct {
	private Product product;
	private String priceFormat;
	private @Autowired ProductController controller;

	public void setProductName(String productName){
		ProductSearchOptions options = new ProductSearchOptions();
		options.setName(productName);
		List<Product> prods = controller.searchProduct(options);
		if (prods.size() > 0) {
			product = prods.get(0);
		} else {
			product = null;
		}
	}
	
	
	public String getProductName(){
		if (product == null) {
			return null;
		} else {
			return product.getName();
		}
	}
	
	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	public String getPriceFormat() {
		String aux;
		DecimalFormat nf = new DecimalFormat("##.00"); 
		aux = String.valueOf(nf.format(product.getPrice()));
		priceFormat = aux.replace('.', ',');
		return priceFormat;
	}


	public void setPriceFormat(String priceFormat) {
		this.priceFormat = priceFormat;
	}
	
	
	

}
