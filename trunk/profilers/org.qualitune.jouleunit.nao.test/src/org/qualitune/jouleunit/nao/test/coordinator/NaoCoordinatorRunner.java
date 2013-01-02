package org.qualitune.jouleunit.nao.test.coordinator;

import org.qualitune.jouleunit.nao.NaoJouleUnitCoordinator;

public class NaoCoordinatorRunner {

	/**
	 * Main method to trigger the {@link NaoJouleUnitCoordinator}.
	 */
	public static void main(String[] args) {
		NaoJouleUnitCoordinator coordinator = new NaoJouleUnitCoordinator(
				"192.168.0.132", 8070, NaoTests.class);
		coordinator.runTestRun(1, 0, false);
	}
}
