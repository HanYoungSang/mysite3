package com.bit2017.mysite.vo;

public class GalleryVo {

	private Long no;
	private String oriFile;
	private String saveFile;
	private String comments;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getOriFile() {
		return oriFile;
	}
	public void setOriFile(String oriFile) {
		this.oriFile = oriFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public String getComment() {
		return comments;
	}
	public void setComment(String comment) {
		this.comments = comment;
	}
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", oriFile=" + oriFile + ", saveFile="
				+ saveFile + ", comment=" + comments + "]";
	}
	
}
