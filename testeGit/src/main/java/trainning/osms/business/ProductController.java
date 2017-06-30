package trainning.osms.business;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.persistence.ProductDao;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ProductController {
private @Autowired ProductDao productDao;
	
	
	public ProductController(){
	       
	}


	public ProductController(ProductDao productDao) {
		this.productDao= productDao;
	}
	
	@Transactional
	public void registerProduct(Product product){
		Integer productIdentifier = product.getIdentifier();
		String productName = product.getName();
		Product databaseProduct = productDao.selectProduct(productName);
		
		// Existe algum categoria no banco de dados com o nome category.getName() ?
		if (databaseProduct != null) {
			// Sim, existe!
			Integer databaseProdIdentifier = databaseProduct.getIdentifier();
			
			// São categorias diferentes?
			if (equals(databaseProdIdentifier, productIdentifier) == false) {
				// Categorias diferentes não podem ter o mesmo nome! Levante uma exceção
				throw new BusinessException("Já existe um produto com o nome " + productName);	
			}
		}

		if (productIdentifier == null) {
			productDao.insertProduct(product);
		} else {
			productDao.updateProduct(product);
		}
	}
	
	
	private boolean equals(Object object1, Object object2) {
		if (object1 == null) {
			return object2 == null;
		} else {
			return object1.equals(object2);
		}
	}


	public List<Product> searchProduct(ProductSearchOptions options) {
		return productDao.selectProduct(options);
	}


	public int searchProductCount(ProductSearchOptions options) {
		 return productDao.selectProductCount(options);
	}


	@Transactional
	public void deleteProduct(Product product) {
	   productDao.deleteProduct(product);
		
	}


}