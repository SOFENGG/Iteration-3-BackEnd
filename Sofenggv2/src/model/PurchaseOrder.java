package model;

import java.math.BigDecimal;
import java.sql.Date;

public class PurchaseOrder {
	public static final String TABLE = "purchase_orders";
	public static final String COLUMN_ORDER_ID = "order_id";
	public static final String COLUMN_SUPPLIER_CODE = "supplier_code";
	public static final String COLUMN_TOTAL_PRICE = "total_price";
	public static final String COLUMN_DATE_ORDERED = "date_ordered";
	public static final String COLUMN_IS_PENDING = "is_pending";
	public static final String COLUMN_DATE_RECEIVED = "date_received";
	
	private int orderID;
	private String supplierCode;
	private BigDecimal totalPrice;
	private Date dateOrdered;
	private int isPending;
	private Date dateReceived;
	
	public PurchaseOrder(int orderID, String supplierCode, BigDecimal totalPrice, Date dateOrdered, int isPending, Date dateReceived) {
		this.orderID = orderID;
		this.supplierCode = supplierCode;
		this.totalPrice = totalPrice;
		this.dateOrdered = dateOrdered;
		this.isPending = isPending;
		this.dateReceived = dateReceived;
	}
	
	@Override
	public String toString() {
		return "PurchaseOrder [orderID=" + orderID + ", supplierCode=" + supplierCode + ", totalPrice=" + totalPrice
				+ ", dateOrdered=" + dateOrdered + ", isPending=" + isPending + ", dateReceived=" + dateReceived + "]";
	}

	public int getOrderID() {
		return orderID;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public int getIsPending() {
		return isPending;
	}

	public Date getDateReceived() {
		return dateReceived;
	}
	
}
