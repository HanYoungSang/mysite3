package com.bit2017.mysite.vo;

public class BoardVo {

	private Long rn;
	private Long no;
	private String title;
	private String content;
	private Long hit;
	private Long gNo;
	private Long oNo;
	private Long depth;
	private String regDate;
	private String userName;
	private Long userNo;
	
	public Long getRn() {
		return rn;
	}
	public void setRn(Long rn) {
		this.rn = rn;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userNname) {
		this.userName = userNname;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public Long getGNo() {
		return gNo;
	}
	public void setGNo(Long gNo) {
		this.gNo = gNo;
	}
	public Long getONo() {
		return oNo;
	}
	public void setONo(Long oNo) {
		this.oNo = oNo;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "BoardVo [rn=" + rn + ", no=" + no + ", title=" + title
				+ ", content=" + content + ", hit=" + hit + ", gNo=" + gNo
				+ ", oNo=" + oNo + ", depth=" + depth + ", regDate=" + regDate
				+ ", userNname=" + userName + ", userNo=" + userNo + "]";
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
