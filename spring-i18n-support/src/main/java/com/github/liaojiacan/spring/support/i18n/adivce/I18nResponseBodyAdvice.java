package com.github.liaojiacan.spring.support.i18n.adivce;


import com.github.liaojiacan.spring.support.i18n.RefreshableMessageSource;
import com.github.liaojiacan.spring.support.i18n.annotation.I18n;
import com.github.liaojiacan.spring.support.i18n.annotation.Translate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An interceptor for translating response body
 * @author liaojiacan
 */
@ControllerAdvice
public class I18nResponseBodyAdvice implements ResponseBodyAdvice {


	@Autowired
	private RefreshableMessageSource messageSource;

	private static final Logger log = LoggerFactory.getLogger(I18nResponseBodyAdvice.class);

	private final static Pattern SPEL_EXPRESSION_REGEX = Pattern.compile("[\\$\\#]\\{(.+?)\\}");

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), I18n.class) ||
				returnType.hasMethodAnnotation(I18n.class));
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		translate(body);

		return body;
	}

	private void translate(Object bean){
		if(bean!=null){
			Class<? extends Object> clazz = bean.getClass();
			//process collection field
			if(bean instanceof Iterable){
				Iterable iterable = (Iterable)bean;
				iterable.forEach((ofBean)->{
					translate(ofBean);
				});
			}
			Set<Field> nextTranslateFields = findFields(clazz, I18n.class);
			if(!nextTranslateFields.isEmpty()){
				nextTranslateFields.forEach((translateField)->{
					ReflectionUtils.makeAccessible(translateField);
					Object fieldValue = ReflectionUtils.getField(translateField, bean);
					translate(fieldValue);
				});
			}
			Set<Field> translateFields = findFields(clazz, Translate.class);
			if(translateFields.isEmpty()){
				return;
			}
			ExpressionParser parser = new SpelExpressionParser();
			EvaluationContext context = new StandardEvaluationContext(bean);

			translateFields.forEach((translateField)->{
				Translate translate = translateField.getAnnotation(Translate.class);
				String code = translate.code();
				if(code.contains("$")||code.contains("#")){
					Matcher matcher = SPEL_EXPRESSION_REGEX.matcher(code);
					while (matcher.find()){
						String expStr = matcher.group(1);
						Expression exp = parser.parseExpression(expStr);
						String value = String.valueOf(exp.getValue(context));
						code = code.replace("${"+expStr+"}",value);
					}
				}
				String message =null;
				Locale locale = LocaleContextHolder.getLocale();
				message = getMessage(code, locale);
				if(!StringUtils.isEmpty(message)){
					ReflectionUtils.makeAccessible(translateField);
					ReflectionUtils.setField(translateField,bean,message);
				}else {
					if(log.isDebugEnabled()){
						log.debug("Ignore translation,because can not find message in english,code={}.",code);
					}
				}

			});

		}
	}

	private String getMessage(String code, Locale locale) {
		String message = null;
		Object[] args = new Object[0];
		try{
			message = messageSource.getMessage(code,args, locale);
		}catch (NoSuchMessageException e){
			if(!Locale.ENGLISH.equals(locale)){
				if(log.isDebugEnabled()){
					log.debug("Can not find message, code = {},locale = {},return English!",code,locale.toLanguageTag());
				}
				return getMessage(code,Locale.ENGLISH);
			}
		}
		return message;
	}


	public static Set<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {
		Set<Field> set = new HashSet<>();
		Class<?> c = classs;
		while (c != null) {
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(ann)) {
					set.add(field);
				}
			}
			c = c.getSuperclass();
		}
		return set;
	}

}
