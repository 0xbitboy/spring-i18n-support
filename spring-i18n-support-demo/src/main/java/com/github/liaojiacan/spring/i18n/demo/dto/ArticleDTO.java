package com.github.liaojiacan.spring.i18n.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.liaojiacan.spring.support.i18n.annotation.Translate;

/**
 * @author liaojiacan
 * @date 2018/12/24
 */
public class ArticleDTO {

	@JsonIgnore
	private Integer id;
	@Translate(code = "article.${id}.title")
	private String title;
	@Translate(code = "article.${id}.description")
	private String description;
	@Translate(code = "article.${id}.a")
	private String a;
	@Translate(code = "article.${id}.a")
	private String b;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}
}
