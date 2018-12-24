package com.github.liaojiacan.spring.support.i18n.exception;

/**
 * @author liaojiacan
 * @date 2018/12/24
 */
public class NotSupportedOperationException extends RuntimeException {

	public NotSupportedOperationException(String action) {
		super(String.format("%s operation was not supported in this provider"));
	}
}
