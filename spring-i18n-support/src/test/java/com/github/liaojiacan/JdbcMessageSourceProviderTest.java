package com.github.liaojiacan;


import com.alibaba.fastjson.JSON;
import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSoucreProvider;
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
	private JdbcMessageSoucreProvider jdbcMessageSoucreProvider;

	@Test
	@Rollback(false)
	public void testAddMessageEntry(){
		jdbcMessageSoucreProvider.addMessage(Locale.SIMPLIFIED_CHINESE,"i18n.simple.test",null,"多语言测试");
		jdbcMessageSoucreProvider.addMessage(Locale.ENGLISH,"i18n.simple.test",null,"i18n test simple");
		jdbcMessageSoucreProvider.addMessage(Locale.ENGLISH,"i18n.spel.test.1",null,"i18n spel code,id =1");
		jdbcMessageSoucreProvider.addMessage(Locale.ENGLISH,"i18n.spel.test.2",null,"i18n spel code,id =2");
	}


	@Test
	public void testLoadMessageEntries(){
		List<MessageEntry> list = jdbcMessageSoucreProvider.load();
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	@Rollback(false)
	public void testUpdateMessageEntry(){
		jdbcMessageSoucreProvider.updateMessage(Locale.SIMPLIFIED_CHINESE,"i18n.test",null,"多语言更新测试");
	}



}
