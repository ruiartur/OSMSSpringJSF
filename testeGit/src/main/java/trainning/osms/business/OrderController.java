package trainning.osms.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.persistence.OrderDao;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class OrderController {
private @Autowired OrderDao dao;
	
	
	public OrderController(){
	       this(new OrderDao());
	}


	public OrderController(OrderDao orderDao) {
		this.dao= orderDao;
	}
	
	@Transactional
	public void insertOrder(Order order){
		dao.insertOrder(order);
	}

}
