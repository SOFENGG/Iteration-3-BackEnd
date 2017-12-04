package model;

import java.math.BigDecimal;

public class CartItem {
	private CartItemType type;
	
	//item var
	private String itemCode;
	private String name;
	private int quantity;
	private BigDecimal price_sold;
	private BigDecimal original_price;
	
	//service var
	private int serviceId;
	private int workerId;
	
	//used for item
	public CartItem(CartItemType type, String itemCode, String name, BigDecimal original_price, int quantity){
		this.type = type;
		this.itemCode = itemCode;
		this.name = name;
		this.quantity = quantity;
		this.original_price = original_price;
		this.price_sold = original_price;
	}
	
	//used for service
	public CartItem(CartItemType type, int serviceId, int workerId, String name, BigDecimal original_price, int quantity){
		this.type = type;
		this.serviceId = serviceId;
		this.workerId = workerId;
		this.name = name;
		this.quantity = quantity;
		this.original_price = original_price;
		this.price_sold = original_price;
	}
	
	public BigDecimal getOriginalPrice(){
		return original_price;
	}
	
	public BigDecimal getPriceSold(){
		return price_sold;
	}
	
	public String getItemCode(){
		return itemCode;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setPriceSold(BigDecimal newPrice){
		this.price_sold = newPrice;
	}
	
	public void setQuantity(int newQuantity){
		quantity = newQuantity;
	}
	
	public String getName(){
		return name;
	}
	
	public CartItemType getType(){
		return type;
	}
	
	public int getServiceId(){
		return serviceId;
	}
	
	public int getWorkerId(){
		return workerId;
	}
}
