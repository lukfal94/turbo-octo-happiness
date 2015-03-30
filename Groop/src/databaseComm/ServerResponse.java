package databaseComm;

import databaseComm.ServerResponse.ServerErrorMessage;

public class ServerResponse {
	private ServerErrorMessage errMsg;
	private int errorCode;
	
	private ServerErrorMessage[] errMsgs = ServerErrorMessage.values();
	
	public enum ServerErrorMessage {
		NO_ERROR, DATABASE_CONN, USER_TAKEN, INCORRECT_PASSWORD, USER_NOT_FOUND
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public ServerErrorMessage getServerErrorMessage() {
		// TODO Auto-generated method stub
		setErrMsg(errorCode);
		System.out.println("ServerErrorMessage " + errMsg);
		return errMsg;
	}
	
	private void setErrMsg(int errAsInt) {
		// TODO Auto-generated method stub
		this.errMsg = errMsgs[errAsInt];
	}
}
