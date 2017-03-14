package com.bit2017.mysite.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class GuestbookVo {

	private Long no;
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	@NotEmpty
	private String content;
	private String regDate;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password="
				+ password + ", content=" + content + ", regDate=" + regDate
				+ "]";
	}	
	
	
}
