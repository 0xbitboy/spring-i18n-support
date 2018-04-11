package com.github.liaojiacan;

import com.github.liaojiacan.config.BaseConfig;
import com.github.liaojiacan.spring.support.i18n.RefreshableMessageSource;
import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSoucreProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Locale;

public class RefreshableMessageSourceTest extends AbstractTest {



	@Autowired
	private RefreshableMessageSource refreshableMessageSource;


	@Test
	public void testGetMessage(){
		String message = refreshableMessageSource.getMessage("i18n.test",null, Locale.SIMPLIFIED_CHINESE);
		System.out.println(message);
	}


	@Configuration
	@Import(BaseConfig.class)
	public static class RefreshableMessageSourceTestConfig{

		@Autowired
		private JdbcMessageSoucreProvider jdbcMessageSoucreProvider;

		@Bean
		public RefreshableMessageSource refreshableMessageSource(){
			return  new RefreshableMessageSource(jdbcMessageSoucreProvider);
		}
	}
}
