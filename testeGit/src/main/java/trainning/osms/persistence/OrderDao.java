package trainning.osms.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Order;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class OrderDao {
	@SuppressWarnings("unused")
	private @PersistenceUnit EntityManagerFactory factory;
	private @PersistenceContext EntityManager manager;
	
	public void insertOrder(Order order){
		
			manager.persist(order);
		}
	
}
