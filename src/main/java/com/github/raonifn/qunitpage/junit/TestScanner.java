package com.github.raonifn.qunitpage.junit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestScanner {

	private String dirName;

	private String root;

	public TestScanner(String dirName, String root) {
		this.dirName = dirName;
		this.root = root;
	}

	public List<String> findTests() {
		File file = new File(dirName);
		return findJS(file);
	}

	protected List<String> findJS(File dir) {
		List<String> ret = new ArrayList<String>();

		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				ret.addAll(findJS(f));
			} else {
				String fileName = f.getPath().substring(dirName.length());
				String finalName = (root + fileName).replaceAll("//", "/");
				ret.add(finalName);
			}
		}

		return ret;
	}

}
