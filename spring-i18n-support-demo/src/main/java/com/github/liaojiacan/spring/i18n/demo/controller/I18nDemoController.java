package com.github.liaojiacan.spring.i18n.demo.controller;

import com.github.liaojiacan.spring.i18n.demo.response.I18nSPELResponse;
import com.github.liaojiacan.spring.i18n.demo.response.I18nSimpleResponse;
import com.github.liaojiacan.spring.support.i18n.annotation.I18n;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class I18nDemoController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/i18n/simple")
	@ResponseBody
	@I18n
	public I18nSimpleResponse i18nSimpleResponse() {
		I18nSimpleResponse response = new I18nSimpleResponse();
		response.setId(1);
		response.setMessage("default");
		return response;
	}

	@RequestMapping("/i18n/spel/{id}")
	@ResponseBody
	@I18n
	public I18nSPELResponse i18nSpelResponse(@PathVariable("id") Integer id) {
		I18nSPELResponse response = new I18nSPELResponse();
		response.setId(id);
		response.setMessage("default");
		return response;
	}

	@RequestMapping("/i18n/spel/{id}/list")
	@ResponseBody
	@I18n
	public List<I18nSPELResponse> i18nSpelResponses(@PathVariable("id") Integer id) {
		List<I18nSPELResponse> list = new ArrayList<>();
		I18nSPELResponse response = new I18nSPELResponse();
		response.setId(id);
		response.setMessage("default");
		list.add(response);
		return list;
	}

}
