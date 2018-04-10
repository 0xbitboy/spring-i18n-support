package com.github.liaojiacan.spring.support.i18n;

import com.github.liaojiacan.spring.support.i18n.model.MessageEntry;
import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

public class RefreshableMessageSource extends AbstractMessageSource implements InitializingBean{

	private MessageSourceProvider provider;

	/**
	 * Setting return origin code when the message not found.
	 */
	protected Boolean returnUnresolvedCode = false;

	/**
	 * The MessageFormat cache
	 */
	private Map<String,Map<Locale,MessageFormat>> messageEntryMap = Collections.emptyMap();

	public RefreshableMessageSource(MessageSourceProvider provider) {
		this.provider = provider;
	}

	private void refresh(){
		List<MessageEntry> messageEntries = provider.load();
		if(!CollectionUtils.isEmpty(messageEntries)){
			final Map<String,Map<Locale,MessageFormat>> finalMap = new HashMap<>();
			messageEntries.forEach(messageEntry -> {
				String code  = messageEntry.getCode();
				Locale locale = LocaleUtils.toLocale(messageEntry.getLang());
				Map<Locale, MessageFormat> localeMapping = finalMap.get(code);
				if(localeMapping == null){
					localeMapping = new HashMap<>();
				}
				localeMapping.put(locale,createMessageFormat(messageEntry.getMessage(),locale));
			});
			messageEntryMap = finalMap;
		}
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		Map<Locale, MessageFormat> localeMessageMap = messageEntryMap.get(code);
		if(localeMessageMap != null ){
			MessageFormat mf = localeMessageMap.get(locale);
			if(mf!=null){
				return  mf;
			}
		}
		if(returnUnresolvedCode){
			return createMessageFormat(code,locale);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.refresh();
	}

	public MessageSourceProvider getProvider() {
		return provider;
	}

	public void setProvider(MessageSourceProvider provider) {
		this.provider = provider;
	}

	public Boolean getReturnUnresolvedCode() {
		return returnUnresolvedCode;
	}

	public void setReturnUnresolvedCode(Boolean returnUnresolvedCode) {
		this.returnUnresolvedCode = returnUnresolvedCode;
	}
}
