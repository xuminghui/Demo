package com.example.controller;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckCodeController {
	@RequestMapping(value = "/init/InitRegValCode", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		RandomFontFactory ff = new RandomFontFactory();
		ff.setMinSize(30);
		ff.setMaxSize(30);
		RandomWordFactory rwf = new RandomWordFactory();
		rwf.setMinLength(4);
		rwf.setMaxLength(4);
		cs.setWordFactory(rwf);
		cs.setFontFactory(ff);
		cs.setHeight(30);
		cs.setWidth(140);

		try {
			ServletOutputStream stream = response.getOutputStream();
			String validate_code = EncoderHelper.getChallangeAndWriteImage(cs, "png", stream);
			request.getSession().setAttribute("REG_VAL_CODE", validate_code);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
