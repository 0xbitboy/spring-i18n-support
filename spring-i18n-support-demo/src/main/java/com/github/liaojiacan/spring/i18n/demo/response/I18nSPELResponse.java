package com.github.liaojiacan.spring.i18n.demo.response;

import com.github.liaojiacan.spring.support.i18n.annotation.Translate;


public class I18nSPELResponse {

	private int id;
	@Translate(code = "i18n.spel.test.${id}")
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
