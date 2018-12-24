package com.github.liaojiacan.spring.support.i18n.provider;

import com.github.liaojiacan.spring.support.i18n.MessageSourceProvider;
import com.github.liaojiacan.spring.support.i18n.exception.NotSupportedOperationException;
import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.sql.RowSet;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liaojiacan
 * @date 2018/12/24
 */
public class CustomMessageSourceProvider implements MessageSourceProvider {

	protected static final String QUERY_TPL_SELECT_MESSAGE_ENTRIES = "SELECT locale , %s ,%s FROM %s";

	/**
	 * The format of code
	 * like : codePrefix.value(keyColumn).name(keyColumn)
	 */
	protected static final String CODE_TPL = "%s.%s.%s";

	private static final  String DELIMITER = "`";

	private String providerName;
	private JdbcTemplate jdbcTemplate;
	private String tableName;
	private Set<String> columns;
	private String keyColumn;
	private String codePrefix;

	public CustomMessageSourceProvider(String providerName, JdbcTemplate jdbcTemplate, String tableName, Set<String> columns, String keyColumn, String codePrefix) {
		this.providerName = providerName;
		this.jdbcTemplate = jdbcTemplate;
		this.tableName = tableName;
		this.columns = columns;
		this.keyColumn = keyColumn;
		this.codePrefix = codePrefix;
	}


	public CustomMessageSourceProvider(String providerName, JdbcTemplate jdbcTemplate, String tableName, Set<String> columns, String keyColumn) {
		this.providerName = providerName;
		this.jdbcTemplate = jdbcTemplate;
		this.tableName = tableName;
		this.columns = columns;
		this.keyColumn = keyColumn;
		this.codePrefix = providerName;
	}

	@Override
	public String getName() {
		return this.providerName;
	}

	@Override
	public List<MessageEntry> load() {

		String sql = String.format(QUERY_TPL_SELECT_MESSAGE_ENTRIES, this.keyColumn,String.join(",",this.columns.stream().map(col->addDelimiter(col)).collect(Collectors.toList())),this.tableName);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

		List<MessageEntry> entries = new ArrayList<>(this.columns.size());

		while (rowSet.next()){
			String identity = String.valueOf(rowSet.getString(this.keyColumn));
			String locale = String.valueOf(rowSet.getString("locale"));
			Assert.notNull(identity,"KeyColumn value can not be null");
			this.columns.forEach(column -> {
				String code = String.format(CODE_TPL,this.codePrefix,identity,column);
				String message = String.valueOf(rowSet.getString(column));
				MessageEntry messageEntry = new MessageEntry();
				messageEntry.setCode(code);
				messageEntry.setLocale(locale);
				messageEntry.setMessage(message);
				messageEntry.setType(tableName);
				entries.add(messageEntry);
			});
		}
		return entries;

	}

	protected String addDelimiter(String name) {
		return String.format("%s%s%s", DELIMITER, name, DELIMITER);
	}


	@Override
	public int addMessage(Locale locale, String code, String type, String message) {
		throw new NotSupportedOperationException("ADD");
	}

	@Override
	public int updateMessage(Locale locale, String code, String type, String message) {
		throw new NotSupportedOperationException("UPDATE");
	}

	@Override
	public int deleteMessage(Locale locale, String code) {
		throw new NotSupportedOperationException("DELETE");
	}
}
