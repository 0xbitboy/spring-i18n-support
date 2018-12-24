package com.github.liaojiacan.spring.support.i18n.provider;

import com.github.liaojiacan.spring.support.i18n.MessageSourceProvider;
import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Locale;

/**
 * @author liaojiacan
 * @refer https://github.com/synyx/messagesource/blob/master/src/main/java/org/synyx/messagesource/jdbc/JdbcMessageProvider.java
 */
public class JdbcMessageSourceProvider implements MessageSourceProvider {

	private JdbcTemplate jdbcTemplate;

	protected static final String QUERY_TPL_INSERT_MESSAGE_ENTRY =
			"INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)";
	protected static final String QUERY_TPL_DELETE_MESSAGE_ENTRY = "DELETE FROM %s WHERE %s = ? and %s= ?";
	protected static final String QUERY_TPL_SELECT_MESSAGE_ENTRIES = "SELECT %s,%s,%s,%s FROM %s";
	protected static final String QUERY_TPL_UPDATE_MESSAGE_ENTRY = "UPDATE %s set %s=?,%s=?,%s=? WHERE %s=? and %s=?";

	private String localeColumn = "locale";
	private String typeColumn = "type";
	private String codeColumn = "code";
	private String messageColumn = "message";
	private String tableName = "i18n_message";
	private String delimiter = "`";

	private String providerName;

	public JdbcMessageSourceProvider( String providerName,JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.providerName = providerName;
	}

	@Override
	public String getName() {
		return this.providerName;
	}

	@Override
	public List<MessageEntry> load() {
		// @formatter:off
		String sql = String.format(getQueryTplSelectMessageEntries(),
				addDelimiter(getCodeColumn()),
				addDelimiter(getLocaleColumn()),
				addDelimiter(getTypeColumn()),
				addDelimiter(getMessageColumn()),
				addDelimiter(getTableName()));
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(MessageEntry.class));
		// @formatter:on
	}

	@Override
	public int addMessage(Locale locale, String code, String type, String message) {
		// @formatter:off
		String sql = String.format(getQueryTplInsertMessageEntry(),
				addDelimiter(getTableName()),
				addDelimiter(getLocaleColumn()),
				addDelimiter(getCodeColumn()),
				addDelimiter(getTypeColumn()),
				addDelimiter(getMessageColumn()));
		// @formatter:on
		return jdbcTemplate.update(sql,locale.toLanguageTag(),code,type,message);
	}


	@Override
	public int updateMessage(Locale locale, String code, String type, String message) {
		// @formatter:off
		String sql = String.format(getQueryTplUpdateMessageEntry(),
				addDelimiter(getTableName()),
				addDelimiter(getLocaleColumn()),
				addDelimiter(getTypeColumn()),
				addDelimiter(getMessageColumn()),
				addDelimiter(getCodeColumn()),addDelimiter(getLocaleColumn()));
		// @formatter:on
		return jdbcTemplate.update(sql,locale.toLanguageTag(),type,message,code,locale.toLanguageTag());
	}

	@Override
	public int deleteMessage(Locale locale, String code) {
		String sql = String.format(getQueryTplDeleteMessageEntry(),addDelimiter(getTableName()),addDelimiter(getCodeColumn()),addDelimiter(getLocaleColumn()));
		return jdbcTemplate.update(sql,code,locale.toLanguageTag());
	}

	/**
	 * Method that "wraps" a field-name (or table-name) into the delimiter.
	 * @param name the name of the field/table
	 * @return the wrapped field/table
	 */
	protected String addDelimiter(String name) {
		return String.format("%s%s%s", delimiter, name, delimiter);
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static String getQueryTplInsertMessageEntry() {
		return QUERY_TPL_INSERT_MESSAGE_ENTRY;
	}

	public static String getQueryTplDeleteMessageEntry() {
		return QUERY_TPL_DELETE_MESSAGE_ENTRY;
	}

	public static String getQueryTplSelectMessageEntries() {
		return QUERY_TPL_SELECT_MESSAGE_ENTRIES;
	}

	public static String getQueryTplUpdateMessageEntry() {
		return QUERY_TPL_UPDATE_MESSAGE_ENTRY;
	}

	public String getLocaleColumn() {
		return localeColumn;
	}

	public void setLocaleColumn(String localeColumn) {
		this.localeColumn = localeColumn;
	}

	public String getTypeColumn() {
		return typeColumn;
	}

	public void setTypeColumn(String typeColumn) {
		this.typeColumn = typeColumn;
	}

	public String getCodeColumn() {
		return codeColumn;
	}

	public void setCodeColumn(String codeColumn) {
		this.codeColumn = codeColumn;
	}

	public String getMessageColumn() {
		return messageColumn;
	}

	public void setMessageColumn(String messageColumn) {
		this.messageColumn = messageColumn;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
