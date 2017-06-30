package trainning.osms.presentation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Order;
import trainning.osms.business.OrderController;
import trainning.osms.business.Product;


@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ShoppingCart {
	private List<Product> carrinho = new ArrayList<Product>();
	private Double total = 0.00 ;
	private String conv;
	private Order order;
	private boolean finalized;
	private @Autowired OrderController controller;
	
	
	public ShoppingCart(){
	}
	
	public List<Product> getCarrinho() {
		return carrinho;
	}


	public void setCarrinho(List<Product> carrinho) {
		this.carrinho = carrinho;
	}


	public String addProduct(Product product){
		finalized =false;
		carrinho.add(product);
		total = total + product.getPrice();
		return "Carrinho";
		
	}
	
	public String removeProduct(Product product){
		carrinho.remove(product);
		total = total - product.getPrice();
		if(carrinho.isEmpty()){
			finalized =true;
			conv = new String();
		}
		return "Carrinho";
	}

	public Double getTotal() {
		if(carrinho==null){
			return null;
		}		
		
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	

	public String getConv() {
		if(total > 0.0){
		String aux;
		DecimalFormat nf = new DecimalFormat("##.00"); 
		aux = String.valueOf(nf.format(total));
		conv = "Total : " + aux.replace('.', ',');
		}else{
			conv = "Seu carrinho está vázio                ";
		}
		return conv;
		
	}

	public void setConv(String conv) {
		this.conv = conv;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void saveOrder(){
		FacesMessage message = new FacesMessage();	
		order = new Order();
		order.setProducts(carrinho);
		order.setTotal(total);
		order.setOrderDate(new Date());
		controller.insertOrder(order);
		carrinho =null;
		total = 0.0;
		conv = new String();
		finalized = true;
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Compra finalizada!");
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,message);
		carrinho = new ArrayList<Product>();
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

}
