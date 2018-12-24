package com.github.liaojiacan;


import com.alibaba.fastjson.JSON;
import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSourceProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Locale;

/**
 * @author liaojiacan
 */
public class JdbcMessageSourceProviderTest extends AbstractTest{

	@Autowired
	private JdbcMessageSourceProvider jdbcMessageSourceProvider;

	@Test
	@Rollback(false)
	public void testAddMessageEntry(){
		jdbcMessageSourceProvider.addMessage(Locale.SIMPLIFIED_CHINESE,"i18n.simple.test",null,"多语言测试");
		jdbcMessageSourceProvider.addMessage(Locale.ENGLISH,"i18n.simple.test",null,"i18n test simple");
		jdbcMessageSourceProvider.addMessage(Locale.ENGLISH,"i18n.spel.test.1",null,"i18n spel code,id =1");
		jdbcMessageSourceProvider.addMessage(Locale.ENGLISH,"i18n.spel.test.2",null,"i18n spel code,id =2");
	}


	@Test
	public void testLoadMessageEntries(){
		List<MessageEntry> list = jdbcMessageSourceProvider.load();
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	@Rollback(false)
	public void testUpdateMessageEntry(){
		jdbcMessageSourceProvider.updateMessage(Locale.SIMPLIFIED_CHINESE,"i18n.test",null,"多语言更新测试");
	}



}
