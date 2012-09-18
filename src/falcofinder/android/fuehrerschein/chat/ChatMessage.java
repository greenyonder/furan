package falcofinder.android.fuehrerschein.chat;

import java.util.Date;

public class ChatMessage {

	public String getKeyfather() {
		return keyfather;
	}
	public void setKeyfather(String keyfather) {
		this.keyfather = keyfather;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getHideemail() {
		return hideemail;
	}
	public void setHideemail(Boolean hideemail) {
		this.hideemail = hideemail;
	}
	public String getEmailto() {
		return emailto;
	}
	public void setEmailto(String emailto) {
		this.emailto = emailto;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	private String keyfather="";
	private Date date;
	private String email="";
	private Boolean hideemail;
	private String emailto="";
	private String qid="";
	private String subject="";
	private String body="";
	public Boolean empty = false;
	
	public String getKeystring() {
		return keystring;
	}
	public void setKeystring(String keystring) {
		this.keystring = keystring;
	}
	private String keystring;

	public boolean isVisibilityButtons() {
		return visibilityButtons;
	}
	private boolean visibilityButtons;
	public void setVisibilityButtons(boolean vis) {
		// TODO Auto-generated method stub
		visibilityButtons = vis;
	}
	

	
}
