package com.matheus.cursoudemy.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "order_table")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instance;
	
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
	private Payment payment;
	
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "delivery_id")
	private Address deliveryAddress;
	
	@OneToMany(mappedBy="id.order")	
	private Set<OrderItem> itens = new HashSet<>(); 
	
	public Order() {
		
	}

	public Order(Integer id, Date instance, Client client, Address deliveryAddress) {
		super();
		this.id = id;
		this.instance = instance;
		this.client = client;
		this.deliveryAddress = deliveryAddress;
	}
	
	public double getTotalValue( ) {
		double sum = 0.0;
		for (OrderItem oi : itens) {
			sum = sum + oi.getSubTotal();
		}
		return sum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstance() {
		return instance;
	}

	public void setInstance(Date instance) {
		this.instance = instance;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Set<OrderItem> getItens() {
		return itens;
	}

	public void setItens(Set<OrderItem> itens) {
		this.itens = itens;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		StringBuilder builder = new StringBuilder();
		builder.append("Order nº: ");
		builder.append(getId());
		builder.append(", Instance: ");
		builder.append(sdf.format(getInstance()));
		builder.append(", Client: ");
		builder.append(getClient().getName());
		builder.append(", Payment state: ");
		builder.append(getPayment().getState().getDescription());
		builder.append("\nDetails: \n");
		for(OrderItem or : getItens()) {
			builder.append(or.toString());
		}
		builder.append("Total: ");
		builder.append(nf.format(getTotalValue()));
		return builder.toString();
	}
	
	
	

}
