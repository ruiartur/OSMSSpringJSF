package trainning.osms.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="PRO_PRODUCT",
uniqueConstraints=@UniqueConstraint(columnNames="PRO_NAME")
		)
public class Product implements Cloneable{
	private String name;
	private String descricao;
	private Double price;
	private Integer altura;
	private Integer largura;
	private Integer comprimento;
	private Integer qtde;
	private Integer peso;
	private String urlimage;
	private Integer identifier;
	private Category category;
	private String priceFormat;
	
	@Column(name="PRO_NAME")
	@Size(min=1, max=500, message="Preencha o nome(no máximo 500 caracteres)")
	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="PRO_DESCRICAO")
	@Size(min=1, max=10000, message="Preencha a descrição (no máximo 10000 caracteres)")
	@NotNull
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name="PRO_PRICE")
	@Min(value = (long) 0.1)
	@NotNull(message=" Preencha o preço ")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name="PRO_ALTURA")
	@Min(value = 1)
	@NotNull(message=" Preencha a altura ")
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	
	@Column(name="PRO_LARGURA")
	@Min(value = 1)
	@NotNull(message=" Preencha a largura ")
	public Integer getLargura() {
		return largura;
	}
	public void setLargura(Integer largura) {
		this.largura = largura;
	}
	
	@Column(name="PRO_COMPRIMENTO")
	@Min(value = 1)
	@NotNull(message=" Preencha o comprimento ")
	public Integer getComprimento() {
		return comprimento;
	}
	public void setComprimento(Integer comprimento) {
		this.comprimento = comprimento;
	}
	@Column(name="PRO_QTDE")
	@Min(value = 0)
	@NotNull(message=" Preencha a quantidade ")
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
	
	@Column(name="PRO_PESO")
	@Min(value = 1)
	@NotNull(message=" Preencha o peso ")
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Column(name="PRO_IMAGE")
	@Size(min=0, max=2000, message="Preencha este campo(no máximo 200 caracteres)")
	public String getUrlimage() {
		return urlimage;
	}
	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}
	
	@Id
	@Column(name="PRO_IDENTIFIER")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public Product clone() {
		try{
			return (Product) super.clone();	
		}catch(CloneNotSupportedException e){
			throw new InternalError("A jvm esta louca");
		}
		
	}
	
	@ManyToOne
	@JoinColumn(name="CAT_IDENTIFIER")
	@NotNull
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category newCategory){
		this.category = newCategory;
	}
	
	/*
	public void setCategory(Category newCategory) {
		Category currentcat = this.category;
		if (currentcat !=null){
			currentcat.removeProduct(this);
			
		}
		this.category = newCategory;
		category.addProduct(this);
	}*/
	
	@Transient
	public String getPriceFormat() {
		String aux;
		DecimalFormat nf = new DecimalFormat("##.00");
		aux = String.valueOf(nf.format(price));
		priceFormat = "R$ " + aux.replace('.', ',');
		return priceFormat;
	}


	public void setPriceFormat(String priceFormat) {
		this.priceFormat = priceFormat;
	}
	



	/************************************************************************/
	private List<Order> orders = new ArrayList<Order>();

	
	@ManyToMany(mappedBy ="products",  cascade=CascadeType.REMOVE)//Cascade foi para tornar o projeto mais pratico, 
	//no caso de uma loja real precisariamos verificar se o pedido ja foi finalizado ou existem pedidos pendentes e o estoque pra poder deletar um produto
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	
	/************************************************************************/

}
