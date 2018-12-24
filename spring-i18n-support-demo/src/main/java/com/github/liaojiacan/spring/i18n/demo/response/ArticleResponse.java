package com.github.liaojiacan.spring.i18n.demo.response;

import com.github.liaojiacan.spring.i18n.demo.dto.ArticleDTO;
import com.github.liaojiacan.spring.support.i18n.annotation.I18n;

import java.util.List;

/**
 * @author liaojiacan
 * @date 2018/12/24
 */
public class ArticleResponse {

	@I18n
	private List<ArticleDTO> articles;

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}
}
