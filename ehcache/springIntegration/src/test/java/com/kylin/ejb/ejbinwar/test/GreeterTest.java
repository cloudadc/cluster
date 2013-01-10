package com.kylin.ejb.ejbinwar.test;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.kylin.ejb.ejbinwar.controller.Greeter;
import com.kylin.ejb.ejbinwar.ejb.GreeterService;

/**
 * A simple test case using Arquillian to test a CDI bean.
 *
 * @author david@davidsalter.co.uk
 */
@RunWith(Arquillian.class)
public class GreeterTest {
	
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
		.addClasses(Greeter.class, GreeterService.class)
		.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	}

	@Inject
	Greeter greeter;

	@Test
	public void testGetMessage() throws Exception {
		String name="World!";
		greeter.setName(name);

		assertEquals("Hello " + name, greeter.getMessage());
	}
}
