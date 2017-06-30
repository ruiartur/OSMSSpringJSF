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

import trainning.osms.business.Category;
import trainning.osms.business.CategorySearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CategoryDao {
	@SuppressWarnings("unused")
	private @PersistenceUnit
	EntityManagerFactory factory;
	private @PersistenceContext
	EntityManager manager;

	public Category selectCategory(String categoryName) {

		StringBuilder jpql = new StringBuilder("select b from ");
		jpql.append(Category.class.getName());
		jpql.append(" b where lower(b.name) = lower(");
		jpql.append(":categoryName");
		jpql.append(")");

		TypedQuery<Category> query = manager.createQuery(jpql.toString(),
				Category.class);
		query.setParameter("categoryName", categoryName);
		List<Category> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}

	}

	public void insertCategory(Category category) {

		if (category.getIdentifier() == null) {
			manager.persist(category);
		} else
			manager.merge(category);

	}

	private String toLikeParameter(String string) {
		return "%" + string + "%";
	}

	public List<Category> selectCategory(CategorySearchOptions options) {

		StringBuilder jpql = new StringBuilder("select b from ");
		jpql.append(Category.class.getName());
		jpql.append(" b where 1 = 1");

		if (options.getName() != null && options.getName().length() > 0) {
			jpql.append(" and lower(b.name) like lower(:categoryName)");
		}

		if (options.getDescription() != null
				&& options.getDescription().length() > 0) {
			jpql.append(" and lower(b.description) like lower(:categoryDescription)");
		}

		jpql.append(" order by b.name asc");

		TypedQuery<Category> query = manager.createQuery(jpql.toString(),
				Category.class); // cria um query. consulta a string comando
									// para o banco
		if (options.getName() != null && options.getName().length() > 0) {
			query.setParameter("categoryName",
					toLikeParameter(options.getName()));
		}
		if (options.getDescription() != null
				&& options.getDescription().length() > 0) {
			query.setParameter("categoryDescription",
					toLikeParameter(options.getDescription()));
		}
		if (options.getFirstResult() != null) {
			query.setFirstResult(options.getFirstResult());
		}
		if (options.getMaxResults() != null) {
			query.setMaxResults(options.getMaxResults());
		}
		List<Category> result = query.getResultList();
		return result;

	}

	public void updateCategory(Category category) {
		insertCategory(category);

	}

	public void deleteCategory(Category categoria) {

		Category attachedcat = manager.find(Category.class,
				categoria.getIdentifier());
		manager.remove(attachedcat);

	}

	// FALTA ACABAR , FALTA ACABAR , FALTA ACABAR
	public int selectCategoryCount(CategorySearchOptions options) {
		StringBuilder jpql = new StringBuilder("select count(b) from ");
		jpql.append(Category.class.getName());
		jpql.append(" b where 1 = 1");

		if (options.getName() != null && options.getName().length() > 0) {
			jpql.append(" and lower(b.name) like lower(:categoryName)");
		}

		if (options.getDescription() != null
				&& options.getDescription().length() > 0) {
			jpql.append(" and lower(b.description) like lower(:categoryDescription)");
		}

		TypedQuery<Long> query = manager.createQuery(jpql.toString(),
				Long.class);
		if (options.getName() != null && options.getName().length() > 0) {
			query.setParameter("categoryName",
					toLikeParameter(options.getName()));
		}
		if (options.getDescription() != null
				&& options.getDescription().length() > 0) {
			query.setParameter("categoryDescription",
					toLikeParameter(options.getDescription()));
		}
		Long result = query.getSingleResult();
		return result.intValue();

	}

	public List<Category> searcAllCategories() {

		StringBuilder jpql = new StringBuilder("select b from ");
		jpql.append(Category.class.getName());
		jpql.append(" b ");
		TypedQuery<Category> query = manager.createQuery(jpql.toString(),
				Category.class);

		List<Category> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result;
		}

	}
}
