# spring-i18n-support
&emsp;&emsp;本项目的目的是对Spring国际化组件的扩展，自定义MessageSource，支持注解等。spring 的i18n更多的是在view视图层进行转换的，例如在jsp，velocity, freemarker 等使用相应的标签输出就可以达到翻译的效果。但是对于Restful并没有比较好的支持。

&emsp;&emsp;通过ResponseBodyAdvice我们拦截Controller中的方法，并在返回前将指定的字段进行翻译（通过注解标记）

- 实现一个支持从数据库读取配置的MessageSource
- 支持Restful的多语言注解，支持spel表达式动态组成翻译的key

## 更新记录

- 2018-12-24 支持多数据源

## 使用
&emsp;&emsp; 参考spring-i18n-support-demo项目进行一些必要的Bean配置后，我们就可以在项目中使用这些特性。主要是关于@I18n和@Translate这2个注解的使用。

- @I18n 标记Controller方法或者Bean属性，标明该方法需要进行多语言的翻译，或者该Bean属性下需要进行下一层次的翻译
- @Translate 标记String字段，code参数可以使用spring的spel表达式。
```
public class I18nSPELResponse {

	private int id;
	@Translate(code = "i18n.spel.test.${id}")
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

```

```
@RequestMapping("/i18n/spel/{id}")  
@ResponseBody
@I18n
public I18nSPELResponse i18nSpelResponse(@PathVariable("id") Integer id) {
	I18nSPELResponse response = new I18nSPELResponse();
	response.setId(id);
	response.setMessage("default");
	return response;
}

```
## 语言环境的切换
配置 LocaleChangeInterceptor 可以在url带上参数进行语言环境的切换
```
@Bean
public LocaleChangeInterceptor localeChangeInterceptor() {
	LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	interceptor.setParamName("locale");
	return interceptor;
}
```
```
$ curl http://localhost:8080/i18n/spel/1?locale=en
{"id":1,"message":"i18n spel code,id =1"}
```

## 多数据源（自定义）的配置
&emsp;&emsp;默认的实现（JdbcMessageSourceProvider）都是在i18n_message这张表以key-value的形式配置的,若需要根据不同的业务隔离配置源，可以使用CustomMessageSourceProvider来配置列形式的映射关系。

假如存在一个业务表的结构如下(article)

| id | a     | b     | title    | description |
| -- | ----- | ----- | -------- | ----------- |
| 1  | A | B | 标题 | 描述 |

如果需要对该业务进行一个多语言的支持，那么我们把需要多语言翻译的字段提取到一个多语言表（i18n_article）中，并新增一个字段locale。

| locale | id | a     | b     | title    | description |
| ------ | -- | ----- | ----- | -------- | ----------- |
| en     | 1  | 英文A | 英文B | 英文标题 | 英文描述 |
| zh     | 1  | 中文A | 中文B | 中文标题 | 中文描述 |

CustomMessageSourceProvider配置如下

```
@Bean
public JdbcMessageSourceProvider jdbcMessageSourceProvider(){
	JdbcMessageSourceProvider provider = new JdbcMessageSourceProvider("default",jdbcTemplate);
	provider.setJdbcTemplate(jdbcTemplate);
	return provider;
}


@Bean
public MessageSourceProviderManager messageSourceProviderManager(){
	MessageSourceProviderManager providerManager = new MessageSourceProviderManager();
	MessageSourceProvider articleI18nProvider = new CustomMessageSourceProvider("articleI18nProvider",jdbcTemplate,"i18n_article",
			Stream.of("title","description","a","b").collect(Collectors.toSet()), "id","article");
	providerManager.setProviders(Arrays.asList(jdbcMessageSourceProvider(),articleI18nProvider));
	return providerManager;
}

@Bean
public RefreshableMessageSource refreshableMessageSource(){
	RefreshableMessageSource messageSource = new RefreshableMessageSource(messageSourceProviderManager());
	messageSource.setReturnUnresolvedCode(true);
	return messageSource;
}
```
DTO中@Translate的使用,code的格式与CustomMessageSourceProvider的配置有关，${codePrefix}.${value(keyColumn)}.${name(column)}
```
public class ArticleDTO {

	@JsonIgnore
	private Integer id;
	@Translate(code = "article.${id}.title")
	private String title;
	@Translate(code = "article.${id}.description")
	private String description;
	@Translate(code = "article.${id}.a")
	private String a;
	@Translate(code = "article.${id}.b")
	private String b;
}
```

