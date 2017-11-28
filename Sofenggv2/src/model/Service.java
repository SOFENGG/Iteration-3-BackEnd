package model;

import java.math.BigDecimal;

public class Service {
	public static final String TABLE = "services";
	public static final String COLUMN_SERVICE_ID = "service_id";
	public static final String COLUMN_SERVICE_NAME = "service_name";
	public static final String COLUMN_PRICE = "price";
	
	private int serviceId;
	private String serviceName;
	private BigDecimal price;
	
	public Service(int serviceId, String serviceName, BigDecimal price) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.price = price;
	}

	public int getServiceId() {
		return serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public BigDecimal getPrice() {
		return price;
	}
		
}
