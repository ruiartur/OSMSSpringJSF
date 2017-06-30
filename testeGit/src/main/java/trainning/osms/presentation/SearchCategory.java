package trainning.osms.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Category;
import trainning.osms.business.CategoryController;
import trainning.osms.business.CategorySearchOptions;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchCategory {
	private static final int CATS_PER_PAGE = 2;
	
	private @Autowired CategoryController controller;

	private Category categoria;
	private CategorySearchOptions options;
	private List<Category> categorias;
	private List<Integer> pages;
	private boolean catsaved;
	private boolean catdeleted;

	public String initialize() {
		options = new CategorySearchOptions();
		categoria = null;
		categorias = null;
		catsaved = false;
		setPages(null);
		return "searchCategory";

	}

	public Category getCategoria() {
		return categoria;
	}

	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}

	public CategorySearchOptions getOptions() {
		return options;
	}

	public void setOptions(CategorySearchOptions options) {
		this.options = options;
	}

	public List<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
	}

	public SearchCategory() {
		initialize();
	}

	public void searchCategory() {

		
		int catcount = controller.searchCategoryCount(options);
		pages = new ArrayList<Integer>();
		int page = 1;
		while (catcount > 0) {
			pages.add(page);
			++page;
			catcount -= CATS_PER_PAGE;
		}

		if (pages.isEmpty()) {
			categorias = null;
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Nenhuma Categoria encontrada");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
		} else{
			goToPage(1);
		}

	}

	public String editCategory(Category category) {
		this.categoria = category;
		this.categoria = category.clone();
		this.catsaved = false;
		return "editCategory";
	}

	public void registerCategory() {
		
		FacesMessage message = new FacesMessage();
		//try {
			controller.registerCategory(categoria);
			catsaved = true;
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Categoria alterada com sucesso");
		/*} catch (BusinessException e) {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(e.getMessage());

		}*/

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}

	public boolean getCatsaved() {
		return catsaved;
	}

	public void setCatsaved(boolean catsaved) {
		this.catsaved = catsaved;
	}

	public boolean getCatdeleted() {
		return catdeleted;
	}

	public void setCatdeleted(boolean catdeleted) {
		this.catdeleted = catdeleted;
	}

	public String deleteCategory(Category category) {
		this.catdeleted = false;
		this.categoria = category;
		return "deleteCategory";

	}

	public void deleteCategory() {
		
		FacesMessage message = new FacesMessage();
		//try {
			controller.deleteCategory(categoria);
			catdeleted = true;
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Categoria removida com sucesso");
		/*} catch (BusinessException e) {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(e.getMessage());

		}*/

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);

	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	
	public void goToPage(int page){
		
		int firstResult = (page-1)* CATS_PER_PAGE;
		options.setFirstResult(firstResult);
		options.setMaxResults(CATS_PER_PAGE);
		categorias = controller.searchCategory(options);
		
	}
}


