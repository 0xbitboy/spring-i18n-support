package com.github.liaojiacan.spring.support.i18n.provider;

import com.github.liaojiacan.spring.support.i18n.MessageSourceProvider;
import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Locale;

public class JdbcMessageSoucreProvider implements MessageSourceProvider {

	private JdbcTemplate template;

	@Override
	public List<MessageEntry> load() {
		return null;
	}

	@Override
	public int addMessage(Locale locale, String code, String type, String message) {
		return 0;
	}

	@Override
	public int updateMessage(Locale locale, String code, String type, String message) {
		return 0;
	}

	@Override
	public int deleteMessage(Locale locale, String code) {
		return 0;
	}
}
