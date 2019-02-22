package com.sorenpoulsen.serviceclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tempuri.TestPortType;
import org.tempuri.TestService;

public class ServiceTest {

	String mockEndpoint;

	@Before
	public void readMockPort() {
		Properties p = new Properties();
		try (InputStream is = ServiceTest.class.getResourceAsStream("/test.properties")) {
			p.load(is);
		} catch (IOException e) {
			Assert.fail();
		}
		String mockport = p.getProperty("mockport");
		Assert.assertNotNull(mockport);
		mockEndpoint = "http://localhost:" + mockport + "/testservice";
	}

	@Test
	public void testTriggeredResponse() {
		TestService testService = new TestService();
		TestPortType testPort = testService.getTestPort();
		BindingProvider binding = (BindingProvider) testPort;
		binding.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mockEndpoint);
		String response = testPort.sendMessage("hello");
		Assert.assertEquals("and hello to you", response);
	}


}
