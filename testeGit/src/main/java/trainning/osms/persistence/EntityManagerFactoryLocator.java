package trainning.osms.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryLocator {

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}	
	
	
	private static final EntityManagerFactory factory= Persistence.createEntityManagerFactory("osms");

	public static EntityManagerFactory getFactory() {
		return factory;
	}
	


	
}
