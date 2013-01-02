package org.qualitune.jouleunit.nao.test.coordinator;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.qualitune.naoservice.client.NaoData;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * Test cases for Nao profiling.
 * 
 * @author Claas Wilke
 */
public class NaoTests {
	
	protected static NaoData nao;

	@BeforeClass
	public static void setUp() {
		nao = new NaoData("192.168.0.132", 8070);
	}

	@Test
	public void testStandUpAndSitDown() throws InterruptedException {
		assertTrue(NaoUtil.standUp(nao));
		assertTrue(NaoUtil.sitDown(nao));
		assertTrue(NaoUtil.setStiffness(nao, 0.0f));
	}
}
