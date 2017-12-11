package com.javalec.ex;

public class ex20_MemberDTO {


	private String seq;
	private String title;
	private String content_text;
	private String ins_dt;
	private String upd_dt;

	public ex20_MemberDTO(String seq, String title, String content_text, String ins_dt, String upd_dt) {
		this.seq = seq;
		this.title = title;
		this.content_text = content_text;
		this.ins_dt = ins_dt;
		this.upd_dt = upd_dt;

	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent_text() {
		return content_text;
	}

	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}

	public String getIns_dt() {
		return ins_dt;
	}

	public void setIns_dt(String ins_dt) {
		this.ins_dt = ins_dt;
	}

	public String getUpd_dt() {
		return upd_dt;
	}

	public void setUpd_dt(String upd_dt) {
		this.upd_dt = upd_dt;
	}


	
}
