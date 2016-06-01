package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
public class LogbackApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();//捕获日志信息

	@Test
	public void testLoadedCustomLogbackConfig() throws Exception {
		DemoApplication.main(new String[0]); 
		this.outputCapture.expect(containsString("Sample Debug Message"));
		this.outputCapture.expect(not(containsString("Sample Trace Message")));
	}

	@Test
	public void testProfile() throws Exception {
		DemoApplication
				.main(new String[] { "--spring.profiles.active=staging" });
		this.outputCapture.expect(containsString("Sample Debug Message"));
		this.outputCapture.expect(containsString("Sample Trace Message"));
	}

}