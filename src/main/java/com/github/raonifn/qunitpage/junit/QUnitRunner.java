package com.github.raonifn.qunitpage.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

public class QUnitRunner extends ParentRunner<QUnitTestCase> {

	private final Class<?> clazz;

	private JUnitMethodCaller jUnitMethodCaller;

	public QUnitRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		this.clazz = clazz;

		this.jUnitMethodCaller = new JUnitMethodCaller(clazz);
	}

	@Override
	protected List<QUnitTestCase> getChildren() {
		TestScanner testScanner = new TestScanner(jUnitMethodCaller.getDir(),
				jUnitMethodCaller.getRoot());

		List<String> urls = testScanner.findTests();
		List<QUnitTestCase> ret = new ArrayList<QUnitTestCase>(urls.size());

		for (String url : urls) {
			QUnitTestCase testCase = new QUnitTestCase();
			testCase.setUrl(clazz, url);
			ret.add(testCase);
		}

		return ret;
	}

	@Override
	protected Description describeChild(QUnitTestCase child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(QUnitTestCase child, RunNotifier notifier) {
		jUnitMethodCaller.instanciate();
		child.run(notifier, jUnitMethodCaller);
	}

}
