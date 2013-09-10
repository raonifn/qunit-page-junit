package com.github.raonifn.qunitpage.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

public class QUnitRunnerTest {

	private QUnitRunner runner;

	@QUnitTests(dir = "src/test/webapp/test/page", root = "test/page")
	public static class Tester {

	}

	@Before
	public void init() throws Exception {
		runner = new QUnitRunner(Tester.class);
	}

	@Test
	public void testGetChildren() {
		List<QUnitTestCase> result = runner.getChildren();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetDescription() {
		List<QUnitTestCase> result = runner.getChildren();
		List<String> names = getNames(result);

		System.out.println(names);
		assertTrue(names.contains("test/page/test-login.js"));
		assertTrue(names.contains("test/page/innerTest/test-login.js"));
	}

	private List<String> getNames(List<QUnitTestCase> result) {
		List<String> names = new ArrayList<String>(result.size());
		for (QUnitTestCase tc : result) {
			Description description = runner.describeChild(tc);
			names.add(description.getMethodName());
		}
		return names;
	}
}
