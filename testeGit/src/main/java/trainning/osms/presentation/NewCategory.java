package trainning.osms.presentation;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import trainning.osms.business.Category;
import trainning.osms.business.CategoryController;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewCategory {
	private @Autowired CategoryController catController;
	
	private Category category;
	private boolean catSaved;
	
	public NewCategory(){
		category = new Category();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void registerCategory(){
		FacesMessage message = new FacesMessage();
	
		//try{
			catController.registerCategory(category);
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary("Categoria registrada com sucesso");
			catSaved = true;
			/*}
			catch(RuntimeException e){
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary(e.getMessage());
				
			}*/
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,message);
		}

	public boolean isCatSaved() {
		return catSaved;
	}

	public void setCatSaved(boolean catSaved) {
		this.catSaved = catSaved;
	}
		
		
		
	}
	


