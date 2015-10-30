package com.soul.project.application.bean;

public class GridViewBean {

	String imageUrl; // 图片地址
	String title;	// 标题
	String description; // 描述
	String number; // 编号
	String author;   // 作者
	int ballot;   // 票数
	int grade;		//等级
	int type; //标签类型    0/无   1/VIP  2/精品    3/火热
	
	
	
	public GridViewBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getBallot() {
		return ballot;
	}
	public void setBallot(int ballot) {
		this.ballot = ballot;
	}

	public GridViewBean(String imageUrl, String title, String description,
			String number, String author, int ballot, int grade, int type) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.description = description;
		this.number = number;
		this.author = author;
		this.ballot = ballot;
		this.grade = grade;
		this.type = type;
	}


	
	
}
