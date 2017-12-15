package model;

public class ItemOrder {
	public static final String TABLE = "items_orders";
	public static final String COLUMN_ITEM_ORDER_ID = "item_order_id";
	public static final String COLUMN_ORDER_ID = "order_id";
	public static final String COLUMN_ITEM_CODE = "item_code";
	public static final String COLUMN_QUANTITY = "quantity";
	
	private int orderID;
	private String itemCode;
	private int quantity;
	
	public ItemOrder(int orderID, String itemCode, int quantity) {
		this.orderID = orderID;
		this.itemCode = itemCode;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [orderID=" + orderID + ", itemCode=" + itemCode + ", quantity=" + quantity + "]";
	}

	public int getOrderID() {
		return orderID;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public int getQuantity() {
		return quantity;
	}
}
