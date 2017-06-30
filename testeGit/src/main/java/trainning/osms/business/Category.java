package trainning.osms.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;



@Entity
@Table(name="CAT_CATEGORY",
uniqueConstraints=@UniqueConstraint(columnNames="CAT_NAME"))
public class Category implements Cloneable {
	private String name;
	private String description;
	private Integer identifier;
	
	
	//----------------------------------------------------------------------
	private List<Product> product = new ArrayList<Product>();
	
	@OneToMany(mappedBy="category", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	@BatchSize(size=10)
	public List<Product> getProduct() {
		return product;
	}


	public void setProduct(List<Product> product) {
		this.product = product;
	}

	
	
	/*
	 private List<Product> jpaProduct = new ArrayList<Product>();
	@Transient
	public List<Product> getProduct(){
		return Collections.unmodifiableList(jpaProduct);
	}
	
	@SuppressWarnings("unused")
	@OneToMany(mappedBy="category")
	private List<Product> getJpaProduct() {
		return jpaProduct;
	}


	@SuppressWarnings("unused")
	private void setJpaProduct(List<Product> jpaProduct) {
		this.jpaProduct = jpaProduct;
	}
	
	
	
	*/
	
	//---------------------------------------------------------------------
	
	@Size(min=1, max=200, message="Preencha o nome(no máximo 200 caracteres)")
	@Column(name="CAT_NAME")
	@NotNull
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Size(min=0, max=200)
	@Column(name="CAT_DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Id
	@Column(name="CAT_IDENTIFIER")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIdentifier() {
		return identifier;
	}
	
	
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}


	@Override
	public Category clone() {
		try{
			return (Category) super.clone();	
		}catch(CloneNotSupportedException e){
			throw new InternalError("FALHOU");
		}
		
	}
    /*
	public void removeProduct(Product product) {
		product.re move(product);
		
	}

	public void addProduct(Product product) {
		product.add(product);
		
	}
*/



	
	
}
