package com.example.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class GeneralTest {
	private Service<String, Integer> abService;
	private Service<Long, Object> cdService;

	private List<List<String>> list;

	private Map<String, Map<String, Integer>> map;

	private List<String>[] array;

	private HashMap<String, List<String>> method() {
		return null;
	}

	// 通过如上API，可以得到类型的ResolvableType，
	// 如果类型被Spring
	// AOP进行了CGLIB代理，请使用ClassUtils.getUserClass(ABService.class)得到原始类型。
	@Test
	public void testClass() {
		ResolvableType resolvableType1 = ResolvableType.forClass(ABService.class);
		Class clazz = resolvableType1.getInterfaces()[0].getGeneric(0).resolve();
		System.out.println(clazz);
	}

	@Test
	public void testField() {
		ResolvableType resolvableType2 = ResolvableType
				.forField(ReflectionUtils.findField(GeneralTest.class, "cdService"));
		System.out.println(resolvableType2.getGeneric(0).resolve());

		ResolvableType resolvableType3 = ResolvableType.forField(ReflectionUtils.findField(GeneralTest.class, "list"));
		System.out.println(resolvableType3.getGeneric(0).getGeneric(0).resolve());

		// 简单写法
		resolvableType3.getGeneric(0, 0).resolve(); // List<List<String>>
													// 即String

		ResolvableType resolvableType4 = ResolvableType.forField(ReflectionUtils.findField(GeneralTest.class, "map"));
		resolvableType4.getGeneric(1).getGeneric(1).resolve();

		ResolvableType resolvableType5 = ResolvableType
				.forMethodReturnType(ReflectionUtils.findMethod(GeneralTest.class, "method"));
		resolvableType5.getGeneric(1, 0).resolve();

		ResolvableType resolvableType6 = ResolvableType
				.forConstructorParameter(ClassUtils.getConstructorIfAvailable(Const.class, List.class, Map.class), 1);
		resolvableType6.getGeneric(1, 0).resolve();

		ResolvableType resolvableType7 = ResolvableType
				.forField(ReflectionUtils.findField(GeneralTest.class, "array"));
		resolvableType7.isArray();// 判断是否是数组
		resolvableType7.getComponentType().getGeneric(0).resolve();
	}
}

class Const {
	public Const(List<List<String>> list, Map<String, Map<String, Integer>> map) {
	}
}