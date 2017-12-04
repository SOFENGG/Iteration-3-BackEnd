package model;

import java.sql.Date;

public class ServiceLog {

	public static final String TABLE = "service_log";
	public static final String COLUMN_SERVICE_LOG_ID = "service_log_id";
	public static final String COLUMN_SERVICE_ID = "service_id";
	public static final String COLUMN_WORKER_ID = "worker_id";
	public static final String COLUMN_TRANSACTION_ID = "transaction_id";
	
	private int serviceLogID;
	private int serviceID;
	private int workerID;
	private int transactionID;
	
	public ServiceLog(int serviceLogID, int serviceID, int workerID, int transactionID) {
		this.serviceLogID = serviceLogID;
		this.serviceID = serviceID;
		this.workerID = workerID;
		this.transactionID = transactionID;
	}
	
	public int getServiceLogID(){
		return serviceLogID;
	}
	
	public int getServiceID() {
		return serviceID;
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

	public int getWorkerID() {
		return workerID;
	}

	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
		
}
