# spring-i18n-support
&emsp;&emsp;本项目的目的是对Spring国际化组件的扩展，自定义MessageSource，支持注解等。spring 的i18n更多的是在view视图层进行转换的，例如在jsp，velocity, freemarker 等使用相应的标签输出就可以达到翻译的效果。但是对于Restful并没有比较好的支持。

&emsp;&emsp;通过ResponseBodyAdvice我们拦截Controller中的方法，并在返回前将指定的字段进行翻译（通过注解标记）

- 实现一个支持从数据库读取配置的MessageSource
- 支持Restful的多语言注解，支持spel表达式动态组成翻译的key

# 使用
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
#语言环境的切换
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


