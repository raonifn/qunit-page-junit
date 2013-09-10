package com.github.raonifn.qunitpage.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class JUnitMethodCaller {

	private Class<?> clazz;

	private Object target;

	private Method before;

	private Method beforeClass;

	private Method after;

	private Method afterClass;

	private Method driverProvider;

	private QUnitTests annotation;

	public JUnitMethodCaller(Class<?> clazz) {
		this.clazz = clazz;

		for (Method method : clazz.getMethods()) {
			beforeMethod(method);
			afterMethod(method);
			beforeClassMethod(method);
			afterClassMethod(method);
			driverProviderMethod(method);
		}

		annotation = clazz.getAnnotation(QUnitTests.class);
		if (annotation == null) {
			return;
		}

	}

	private void driverProviderMethod(Method method) {
		if (driverProvider == null
				&& method.isAnnotationPresent(Provider.class)
				&& DriverProvider.class
						.isAssignableFrom(method.getReturnType())) {
			this.driverProvider = method;
		}
	}

	public String getDir() {
		return annotation.dir();
	}

	public String getRoot() {
		return annotation.root();
	}

	public DriverProvider getDriverProvider() {
		return (DriverProvider) invoke(target, driverProvider);
	}

	private void beforeMethod(Method method) {
		if (before == null && method.isAnnotationPresent(Before.class)) {
			this.before = method;
		}
	}

	private void afterMethod(Method method) {
		if (after == null && method.isAnnotationPresent(After.class)) {
			this.after = method;
		}
	}

	private void beforeClassMethod(Method method) {
		if (beforeClass == null
				&& method.isAnnotationPresent(BeforeClass.class)) {
			this.beforeClass = method;
		}
	}

	private void afterClassMethod(Method method) {
		if (afterClass == null && method.isAnnotationPresent(AfterClass.class)) {
			this.afterClass = method;
		}
	}

	public void instanciate() {
		target = instanciate(clazz);
	}

	private <T> T instanciate(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Object getTarget() {
		return target;
	}

	public void before() {
		if (before != null) {
			invoke(target, before);
		}
	}

	public void beforeClass() {
		if (beforeClass != null) {
			invoke(null, beforeClass);
		}
	}

	public void after() {
		if (after != null) {
			invoke(target, after);
		}
	}

	public void afterClass() {
		if (afterClass != null) {
			invoke(null, afterClass);
		}
	}

	private Object invoke(Object target, Method method) {
		try {
			return method.invoke(target, new Object[0]);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}