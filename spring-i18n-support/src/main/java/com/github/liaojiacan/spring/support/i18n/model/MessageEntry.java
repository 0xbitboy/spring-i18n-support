package com.github.liaojiacan.spring.support.i18n.model;


/**
 * @author liaojiacan https://github.com/liaojiacan
 */
public final class MessageEntry {

	private String code;

	private String type;

	private String locale;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
