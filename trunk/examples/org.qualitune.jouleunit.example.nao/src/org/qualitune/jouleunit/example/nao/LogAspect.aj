package org.qualitune.jouleunit.example.nao;

import java.util.HashMap;
import java.util.Map;

import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.PowerRate;

/**
 * This log aspect is used to log the call of each method call within the
 * {@link NaoDemoApplication} during profiling. After each method call the
 * profiler will log an additional {@link PowerRate} value in the current
 * {@link EnergyProfile}.
 * 
 * @author Claas Wilke
 */
public aspect LogAspect {

	protected static Map<String, Integer> methodCounters = new HashMap<String, Integer>();

	/** Log all method calls within {@link NaoDemoApplication}. */
	after(): call(void NaoDemoApplication.*(*)) {

		/* Get the called method. */
		String calledMethod = thisJoinPoint.getSignature().getName();

		/* Ignore the main method. */
		if (calledMethod != "main"
				&& TestNaoDemoApplication.profiler.isProfiling()) {
			/* Increase its counter. */
			int methodCounter = 0;
			if (methodCounters.containsKey(calledMethod))
				methodCounter = methodCounters.get(calledMethod);
			// no else.

			methodCounter++;
			methodCounters.put(calledMethod, methodCounter);

			/* Log the event. */
			if (TestNaoDemoApplication.profiler != null)
				TestNaoDemoApplication.profiler.logEvent("executed "
						+ calledMethod + " #" + methodCounter);
			// no else.
		}
		// no else.
	}
}
