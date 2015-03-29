package databaseComm;

public class ServerResponse {
	private String callType;
	private int errorCode;
	private boolean boolResponse;
	
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isBoolResponse() {
		return boolResponse;
	}
	public void setBoolResponse(boolean boolResponse) {
		this.boolResponse = boolResponse;
	}

}
