package com.github.liaojiacan.spring.support.i18n.manager;

import com.github.liaojiacan.spring.support.i18n.MessageSourceProvider;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaojiacan
 * @date 2018/12/24
 */
public class MessageSourceProviderManager implements InitializingBean {
	protected Collection<? extends MessageSourceProvider> providers = Collections.emptySet();

	protected Map<String,MessageSourceProvider> providerMap = new ConcurrentHashMap<>(16);

	public MessageSourceProvider getProvider(String providerName){
		return  providerMap.get(providerName);
	}

	public Collection<? extends MessageSourceProvider> loadProviders(){
		return this.providers;
	}

	public void  setProviders(Collection<? extends MessageSourceProvider> providers){
		this.providers = providers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<? extends MessageSourceProvider> providers = loadProviders();

		synchronized (this.providerMap) {
			this.providerMap.clear();
			Set<String> cacheNames = new LinkedHashSet<String>(providers.size());
			for (MessageSourceProvider provider : providers) {
				String name = provider.getName();
				this.providerMap.put(name, provider);
				cacheNames.add(name);
			}
		}
	}
}
