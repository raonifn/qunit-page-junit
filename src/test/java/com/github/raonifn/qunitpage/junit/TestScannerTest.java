package com.github.raonifn.qunitpage.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TestScannerTest {

	@Test
	public void testFindTests() throws Exception {
		TestScanner testScanner = new TestScanner("src/test/webapp/test/page",
				"test/page");
		List<String> ret = testScanner.findTests();

		assertEquals(2, ret.size());
		assertTrue(ret.contains("test/page/test-login.js"));
		assertTrue(ret.contains("test/page/innerTest/test-login.js"));
	}
}
