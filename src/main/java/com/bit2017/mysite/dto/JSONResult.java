package com.bit2017.mysite.dto;

public class JSONResult {
	private String result ; // success -> 통신 성공
							// failed -> 통신 실패
	private static String message;
	private static Object data;
	
	private JSONResult() {}
	
	private JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public static JSONResult success( Object object ){
		return new JSONResult("success", null, object);
	}
	public static JSONResult fail( Object object ){
		return new JSONResult("fail", message, null);
	}
	
//	
	public String getResult() {
		return result;
	}
//	public void setResult(String result) {
//		this.result = result;
//	}
	public String getMessage() {
		return message;
	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
	public Object getData() {
		return data;
	}
//	public void setData(Object data) {
//		this.data = data;
//	}
//	
	
}
