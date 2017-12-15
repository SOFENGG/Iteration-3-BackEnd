package model;

import java.math.BigDecimal;

public class ItemLog {
	public static final String TABLE = "items_log";
	public static final String COLUMN_SALE_ID = "sale_id";
	public static final String COLUMN_ITEM_CODE = "item_code";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_TRANSACTION_ID = "transaction_ID";
	public static final String COLUMN_QUANTITY_SOLD = "quantity_sold";
	public static final String COLUMN_ORIGINAL_PRICE = "original_price";
	public static final String COLUMN_PRICE_SOLD = "price_sold";
	
	private String saleId;
	private String itemCode;
	private String transactionId;
	private String type;
	private int quantitySold;
	private BigDecimal originalPrice;
	private BigDecimal priceSold;
	
	public ItemLog(String saleId, String itemCode, String transactionId, String type, int quantitySold,
			BigDecimal originalPrice, BigDecimal priceSold) {
		this.saleId = saleId;
		this.itemCode = itemCode;
		this.transactionId = transactionId;
		this.type = type;
		this.quantitySold = quantitySold;
		this.originalPrice = originalPrice;
		this.priceSold = priceSold;
	}

	public String getSaleId() {
		return saleId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getType() {
		return type;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public BigDecimal getPriceSold() {
		return priceSold;
	}
	
}
