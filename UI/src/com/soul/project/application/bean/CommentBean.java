package com.soul.project.application.bean;

public class CommentBean {
	String commentText;
	String commentAuthor;
	String commentPubDate;
	String commentAuthorHeadUrl;
	
	
	public CommentBean(String commentText, String commentAuthor,
			String commentPubDate, String commentAuthorHeadUrl) {
		super();
		this.commentText = commentText;
		this.commentAuthor = commentAuthor;
		this.commentPubDate = commentPubDate;
		this.commentAuthorHeadUrl = commentAuthorHeadUrl;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCommentAuthor() {
		return commentAuthor;
	}
	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
	public String getCommentPubDate() {
		return commentPubDate;
	}
	public void setCommentPubDate(String commentPubDate) {
		this.commentPubDate = commentPubDate;
	}
	public String getCommentAuthorHeadUrl() {
		return commentAuthorHeadUrl;
	}
	public void setCommentAuthorHeadUrl(String commentAuthorHeadUrl) {
		this.commentAuthorHeadUrl = commentAuthorHeadUrl;
	}
	
	
}
