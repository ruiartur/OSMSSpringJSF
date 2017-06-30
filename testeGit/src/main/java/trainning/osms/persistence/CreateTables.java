package trainning.osms.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CreateTables {

	public static void main(String[] args) {
		EntityManagerFactory factory = EntityManagerFactoryLocator.getFactory();
		
		
		@SuppressWarnings("unused")
		EntityManager manager = factory.createEntityManager();
		
	}
}
