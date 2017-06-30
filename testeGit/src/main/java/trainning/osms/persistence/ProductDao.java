package trainning.osms.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Product;
import trainning.osms.business.ProductSearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductDao {
	@SuppressWarnings("unused")
	private @PersistenceUnit EntityManagerFactory factory;
	private @PersistenceContext EntityManager manager;
	
	
	public Product selectProduct(String productName){
		
		StringBuilder jpql = new StringBuilder("select b from ");
		jpql.append(Product.class.getName());
		jpql.append(" b where lower(b.name) = lower(");
		jpql.append(":productName");
		jpql.append(")");
			
			TypedQuery<Product> query = manager.createQuery(jpql.toString(), Product.class);
			query.setParameter("productName", productName);
			List<Product> result = query.getResultList();
			if(result.isEmpty()){
				return null;
			}
			else {
				return result.get(0);
			}
			
	
		}
		
	
	public void insertProduct(Product product){

	    	 if(product.getIdentifier()==null)
	    		manager.persist(product);
	    	else
	    		manager.merge(product);
	  
	}
	
	private String toLikeParameter(String string){
		return "%" + string + "%";
	}

	public List<Product> selectProduct(ProductSearchOptions options) {
	StringBuilder jpql = new StringBuilder ("select b from ");
	jpql.append(Product.class.getName());
	jpql.append(" b where 1 = 1");
	
	if (options.getName() != null && options.getName().length() > 0){
		jpql.append(" and lower(b.name) like lower(:ProductName)");
	}
	if( options.getDescricao() != null && options.getDescricao().length() > 0){
		jpql.append(" and lower(b.descricao) like lower(:ProductDescription)");
	}

	if(options.getPesode() !=null && options.getPesode() > 0 ){
		jpql.append(" and  b.peso >= (:ProductPesode)");
	}
	if(options.getPesoa() != null && options.getPesoa() > 0 ){
		jpql.append(" and b.peso <= (:ProductPesoa)");
	}
	if(options.getPricede() !=null && options.getPricede() > 0){
		jpql.append(" and b.price>= (:ProductPricede)");
	}
	if(options.getPriceof() !=null && options.getPriceof() > 0){
		jpql.append(" and b.price <= (:ProductPriceof)");
	}
	if(options.getAltura()!=null && options.getAltura() > 0){
		jpql.append(" and b.altura <= (:ProductAltura)");
	}
	if(options.getComprimento()!=null && options.getComprimento() > 0){
		jpql.append(" and b.comprimento <= (:ProductComprimento)");
	}
	if(options.getLargura()!=null && options.getLargura() > 0){
		jpql.append(" and b.largura <= (:ProductLargura)");
	}
	if(options.getQtde()!=null && options.getQtde() > 0){
		jpql.append(" and b.qtde >= (:ProductQtde)");
	}
	if(options.getQtdea()!=null && options.getQtdea() > 0){
		jpql.append(" and b.qtde <= (:ProductQtdeA)");
	}
	
	if(options.getCatID()!=null && options.getCatID()>0){
		jpql.append(" and b.category.identifier = (:ProductCatId)");
	}
	
	
	jpql.append(" order by b.identifier desc");
	
		
		TypedQuery<Product> query = manager.createQuery(jpql.toString(),Product.class); // cria um query. consulta a string comando para o banco
		
		if (options.getName() != null && options.getName().length() > 0){
			query.setParameter("ProductName",toLikeParameter(options.getName()));	
		}
		if( options.getDescricao() != null && options.getDescricao().length() > 0){			
			query.setParameter("ProductDescription",toLikeParameter(options.getDescricao()));
		}
		
		if(options.getPesode() !=null && options.getPesode() > 0 ){
			query.setParameter("ProductPesode", options.getPesode());
		}
		if(options.getPesoa() != null && options.getPesoa() > 0){
			query.setParameter("ProductPesoa", options.getPesoa());
		}
		if(options.getPricede() !=null && options.getPricede() > 0){
			query.setParameter("ProductPricede", options.getPricede());
		}
		if(options.getPriceof() !=null && options.getPriceof() > 0){
			query.setParameter("ProductPriceof", options.getPriceof());
		}
		if(options.getAltura()!=null && options.getAltura() > 0){
			query.setParameter("ProductAltura", options.getAltura());
		}
		if(options.getComprimento()!=null && options.getComprimento() > 0){
			query.setParameter("ProductComprimento", options.getComprimento());
		}
		if(options.getLargura()!=null && options.getLargura() > 0){
			query.setParameter("ProductLargura", options.getLargura());
		}
		if(options.getQtde()!=null && options.getQtde() > 0){
			query.setParameter("ProductQtde", options.getQtde());
		}
		if(options.getQtdea()!=null && options.getQtdea() > 0){
			query.setParameter("ProductQtdeA", options.getQtdea());
		}	
		
		if(options.getCatID()!=null && options.getCatID()>0){
			query.setParameter("ProductCatId", options.getCatID());
		}
		
		if (options.getFirstResult() != null) {
			query.setFirstResult(options.getFirstResult());
		}
		if (options.getMaxResults() != null) {
			query.setMaxResults(options.getMaxResults());
		}
		
		
		
		List<Product> result = query.getResultList(); // executa a consulta
		return result;
	
}
	

	public void updateProduct(Product product){
		insertProduct(product);
		
	}


	public int selectProductCount(ProductSearchOptions options) {
		StringBuilder jpql = new StringBuilder ("select count(b) from ");
		jpql.append(Product.class.getName());
		jpql.append(" b where 1 = 1");
		
		if (options.getName() != null && options.getName().length() > 0){
			jpql.append(" and lower(b.name) like lower(:ProductName)");
		}
		if( options.getDescricao() != null && options.getDescricao().length() > 0){
			jpql.append(" and lower(b.descricao) like lower(:ProductDescription)");
		}
		
		if(options.getPesode() !=null && options.getPesode() > 0 ){
			jpql.append(" and  b.peso >= (:ProductPesode)");
		}
		if(options.getPesoa() != null && options.getPesoa() > 0 ){
			jpql.append(" and b.peso <= (:ProductPesoa)");
		}
		if(options.getPricede() !=null && options.getPricede() > 0){
			jpql.append(" and b.price>= (:ProductPricede)");
		}
		if(options.getPriceof() !=null && options.getPriceof() > 0){
			jpql.append(" and b.price <= (:ProductPriceof)");
		}
		if(options.getAltura()!=null && options.getAltura() > 0){
			jpql.append(" and b.altura <= (:ProductAltura)");
		}
		if(options.getComprimento()!=null && options.getComprimento() > 0){
			jpql.append(" and b.comprimento <= (:ProductComprimento)");
		}
		if(options.getLargura()!=null && options.getLargura() > 0){
			jpql.append(" and b.largura <= (:ProductLargura)");
		}
		if(options.getQtde()!=null && options.getQtde() > 0){
			jpql.append(" and b.qtde >= (:ProductQtde)");
		}
		if(options.getQtdea()!=null && options.getQtdea() > 0){
			jpql.append(" and b.qtde <= (:ProductQtdeA)");
		}
		if(options.getCatID()!=null && options.getCatID()>0){
			jpql.append(" and b.category.identifier = (:ProductCatId)");
		}
		

			TypedQuery<Long> query = manager.createQuery(jpql.toString(),Long.class);
			
			if (options.getName() != null && options.getName().length() > 0){
				query.setParameter("ProductName",toLikeParameter(options.getName()));	
			}
			if( options.getDescricao() != null && options.getDescricao().length() > 0){			
				query.setParameter("ProductDescription",toLikeParameter(options.getDescricao()));
			}
			
			if(options.getPesode() !=null && options.getPesode() > 0){
				query.setParameter("ProductPesode", options.getPesode());
			}if(options.getPesoa() != null && options.getPesoa() > 0){
				query.setParameter("ProductPesoa", options.getPesoa());
			}
			if(options.getPricede() !=null && options.getPricede() > 0){
				query.setParameter("ProductPricede", options.getPricede());
			}
			if(options.getPriceof() !=null && options.getPriceof() > 0){
				query.setParameter("ProductPriceof", options.getPriceof());
			}
			if(options.getAltura()!=null && options.getAltura() > 0){
				query.setParameter("ProductAltura", options.getAltura());
			}
			if(options.getComprimento()!=null && options.getComprimento() > 0){
				query.setParameter("ProductComprimento", options.getComprimento());
			}
			if(options.getLargura()!=null && options.getLargura() > 0){
				query.setParameter("ProductLargura", options.getLargura());
			}
			if(options.getQtde()!=null && options.getQtde() > 0){
				query.setParameter("ProductQtde", options.getQtde());
			}	
			if(options.getQtdea()!=null && options.getQtdea() > 0){
				query.setParameter("ProductQtdeA", options.getQtdea());
			}
			if(options.getCatID()!=null && options.getCatID()>0){
				query.setParameter("ProductCatId", options.getCatID());
			}
			if (options.getFirstResult() != null) {
				query.setFirstResult(options.getFirstResult());
			}
			if (options.getMaxResults() != null) {
				query.setMaxResults(options.getMaxResults());
			}
			
			Long result = query.getSingleResult();
			return result.intValue();

	}


	public void deleteProduct(Product product) {
		
			Product attachedprod = manager.find(Product.class, product.getIdentifier());
			manager.remove(attachedprod);
		
	}


	
	
}


