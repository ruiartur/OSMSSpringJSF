package trainning.osms.business;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORD_ORDERS")
public class Order {
	private List<Product> products;
	private Integer orderdId;
	private Double total;
	private Date orderDate;

	/*
	public Order(List<Product> product){
		setProducts(product);
		
	}*/
	
	@NotNull
	@ManyToMany
	@JoinTable(
			name="ORD_ORDER_PRODUCT",
			joinColumns=@JoinColumn(name="ORD_IDENTIFIER"),
			inverseJoinColumns=@JoinColumn(name="PRO_IDENTIFIER")
		)
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Id
	@Column(name = "ORD_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getOrderdId() {
		return orderdId;
	}

	public void setOrderdId(Integer orderdId) {
		this.orderdId = orderdId;
	}

	@NotNull
	@Column(name ="ORD_TOTAL")
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@NotNull
	@Column(name="ORD_ORDERDATE")
	@Temporal(TemporalType.DATE)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
