package com.github.raonifn.qunitpage.junit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class QUnitRunnerTest {

	@Test
	public void testFindJS() throws Exception {
		List<String> ret = QUnitRunner.findJS("src/test/webapp/test/page",
				"test/page");

		assertEquals(1, ret.size());
		assertEquals("test/page/test-login.js", ret.get(0));
	}
}
