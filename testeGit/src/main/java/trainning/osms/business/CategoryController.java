package trainning.osms.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.persistence.CategoryDao;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CategoryController {
	private @Autowired CategoryDao ctgDao;
	
	public CategoryController(){
	       
	}

	public CategoryController(CategoryDao ctgDao) {
		this.ctgDao= ctgDao;
	}
		
	@Transactional
	public void registerCategory(Category category){
		Integer categoryIdentifier = category.getIdentifier();
		String categoryName = category.getName();
		Category databaseCategory = ctgDao.selectCategory(categoryName);
		
		// Existe algum categoria no banco de dados com o nome category.getName() ?
		if (databaseCategory != null) {
			// Sim, existe!
			Integer databaseCatIdentifier = databaseCategory.getIdentifier();
			
			// São categorias diferentes?
			if (equals(databaseCatIdentifier, categoryIdentifier) == false) {
				// Categorias diferentes não podem ter o mesmo nome! Levante uma exceção
				throw new BusinessException("Já existe uma categoria com o nome " + categoryName);	
			}
		}

		if (categoryIdentifier == null) {
			ctgDao.insertCategory(category);
		} else {
			ctgDao.updateCategory(category);
		}		
	}

	
	
	
	
	public List<Category> searchCategory(CategorySearchOptions options) {
		return ctgDao.selectCategory(options);
	}
	
	private boolean equals(Object object1, Object object2) {
		if (object1 == null) {
			return object2 == null;
		} else {
			return object1.equals(object2);
		}
	}

	@Transactional
	public void deleteCategory(Category categoria) {
		ctgDao.deleteCategory(categoria);
		
	}

	public int searchCategoryCount(CategorySearchOptions options) {
		return ctgDao.selectCategoryCount(options);
	}
	
	public List<Category> searchAllCategories(){
		return ctgDao.searcAllCategories();
	}


}
