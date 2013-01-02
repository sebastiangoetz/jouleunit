/**
 * 
 */
package org.qualitune.jouleunit.nao.test;

import java.io.File;

import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.JouleProfiler;
import org.qualitune.jouleunit.JouleUtil;
import org.qualitune.jouleunit.nao.NaoProfiler;
import org.qualitune.naoservice.client.Nao;
import org.qualitune.naoservice.client.Nao.ALMotion;
import org.qualitune.naoservice.client.NaoData;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * @author Robert Kranz - rkranzl@web.de
 *
 */
public class NaoProfiling {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NaoData nao = new NaoData("192.168.0.132", 8070);

		JouleProfiler profiler = new NaoProfiler(nao);
		profiler.setEstimationInterval(0);
		profiler.setProbeEffectConsiderationEnabled(true);
		profiler.setBaseConsumptionConsiderationEnabled(false);
		profiler.calibrate();

		System.out.println("Calibration done. Refresh rate: "
				+ profiler.getProfilingInterval());
		NaoUtil.standUp(nao);
		profiler.startProfiling();
		turnHeadLeft(nao);
		profiler.logEvent("Turned head left");
		turnHeadRight(nao);
		profiler.logEvent("Turned head right");
		EnergyProfile profile = profiler.endProfiling();
		NaoUtil.sitDown(nao);

		JouleUtil.exportProfile(profile, new File("profileWithEventsNew.csv"),
				"Nao Test");		
	}

	private static void turnHeadRight(NaoData nao) {
		ALMotion am = Nao.createALMotion(nao.getIP(),nao.getPort());
		am.setAngles("HeadYaw", "-1.5", 0.2f);
		
	}

	private static void turnHeadLeft(NaoData nao) {
		ALMotion am = Nao.createALMotion(nao.getIP(),nao.getPort());
		am.setAngles("HeadYaw", "1.5", 0.2f);
	}

}
