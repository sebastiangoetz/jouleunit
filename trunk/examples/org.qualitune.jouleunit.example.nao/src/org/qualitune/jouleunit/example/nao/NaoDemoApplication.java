package org.qualitune.jouleunit.example.nao;

import org.qualitune.naoservice.client.Nao;
import org.qualitune.naoservice.client.NaoUtil;

/**
 * Example Nao application what can be optimized w.r.t. energy consumption using
 * JouleUnit.
 * 
 * @author Claas Wilke
 */
public class NaoDemoApplication {

	/** The port where the {@link Nao} web service is running. */
	protected static final int NAO_PORT = 8070;

	/** The IP address of the {@link Nao}. */
	protected static final String NAO_IP = "192.168.0.139";

	/**
	 * Main method to run the {@link NaoDemoApplication}.
	 * 
	 * This application is an example use case that can be optimized using
	 * JouleUnit. The main method executes a Nao presentation and afterwards
	 * waits a given number of seconds before executing the presentation again.
	 * 
	 * @param args
	 *            The first argument is the number of seconds to be waited
	 *            between to runs. If not given, it will be set to 300 (= 5
	 *            minutes). The second argument is the maximum number of
	 *            iterations before finishing the execution. If not given,
	 *            infinite execution will be performed.
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		/* Get arguments. */
		int waitInterval = 300;
		int maxNoOfIterations = -1;

		if (args != null && args.length > 0) {
			try {
				waitInterval = Integer.parseInt(args[0].trim());
			}

			catch (NumberFormatException e) {
				/* Do nothing, use default value instead. */
			}
		}
		// no else.

		if (args != null && args.length > 1) {
			try {
				maxNoOfIterations = Integer.parseInt(args[1].trim());
			}

			catch (NumberFormatException e) {
				/* Do nothing, use default value instead. */
			}
		}
		// no else.

		/* Create proxy to communicate with the nao. */
		Nao nao = new Nao(NAO_IP, NAO_PORT);
		int iterations = 0;

		/* Real main loop. */
		while (true) {
			/* Do the presentation. */
			presentationPart(nao);

			iterations++;

			/* Break condition. */
			if (maxNoOfIterations > 0 && iterations >= maxNoOfIterations)
				break;
			// no else.

			/* Wait before starting again. */
			waitNSeconds(waitInterval);
		}
		// end while.
	}

	/**
	 * Main method to run the {@link NaoDemoApplication}. This is the fixed
	 * version which consumes less energy as intended.
	 * 
	 * This application is an example use case that can be optimized using
	 * JouleUnit. The main method executes a Nao presentation and afterwards
	 * waits a given number of seconds before executing the presentation again.
	 * 
	 * @param args
	 *            The first argument is the number of seconds to be waited
	 *            between to runs. If not given, it will be set to 300 (= 5
	 *            minutes). The second argument is the maximum number of
	 *            iterations before finishing the execution. If not given,
	 *            infinite execution will be performed.
	 * 
	 * @throws InterruptedException
	 */
	public static void mainFixed(String[] args) throws InterruptedException {

		/* Get arguments. */
		int waitInterval = 300;
		int maxNoOfIterations = -1;

		if (args != null && args.length > 0) {
			try {
				waitInterval = Integer.parseInt(args[0].trim());
			}

			catch (NumberFormatException e) {
				/* Do nothing, use default value instead. */
			}
		}
		// no else.

		if (args != null && args.length > 1) {
			try {
				maxNoOfIterations = Integer.parseInt(args[1].trim());
			}

			catch (NumberFormatException e) {
				/* Do nothing, use default value instead. */
			}
		}
		// no else.

		/* Create proxy to communicate with the nao. */
		Nao nao = new Nao(NAO_IP, NAO_PORT);
		int iterations = 0;

		/* Real main loop. */
		while (true) {
			/* Do the presentation. */
			presentationPart(nao);

			iterations++;

			/* Break condition. */
			if (maxNoOfIterations > 0 && iterations >= maxNoOfIterations)
				break;
			// no else.

			/* Wait before starting again. */
			waitNSeconds(waitInterval);
		}
		// end while.
	}

	/** Contains the active, the presentation part of the application. */
	protected static void presentationPart(Nao nao) {
		standUp(nao);
		greetAudienceV01(nao);
		presentationV01(nao);
		sitDown(nao);
	}

	/**
	 * Contains the active, the presentation part of the application. This is
	 * the fixed version which consumes less energy as intended.
	 */
	protected static void presentationPartFixed(Nao nao) {
		standUp(nao);
		greetAudienceV01(nao);
		presentationV01(nao);
		sitDownFixed(nao);
	}

	/**
	 * Helper method letting a given {@link Nao} sitting down.
	 * 
	 * @param nao
	 *            The {@link Nao}.
	 */
	protected static void sitDown(Nao nao) {
		NaoUtil.sitDown(nao);
	}

	/**
	 * Helper method letting a given {@link Nao} sitting down. This is the fixed
	 * version which consumes less energy as intended.
	 * 
	 * @param nao
	 *            The {@link Nao}.
	 */
	protected static void sitDownFixed(Nao nao) {
		NaoUtil.sitDown(nao);
		NaoUtil.setStiffness(nao, 0f);
	}

	/**
	 * Helper method letting a given {@link Nao} standing up.
	 * 
	 * @param nao
	 *            The {@link Nao}.
	 */
	protected static void standUp(Nao nao) {
		NaoUtil.standUp(nao);
	}

	/**
	 * Helper method that let the {@link Thread} wait a given amount of seconds.
	 * 
	 * @param seconds
	 *            The amount of seconds to wait.
	 */
	protected static void waitNSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		}

		catch (InterruptedException e) {
			/* Do nothing. Proceed. */
		}
	}

	/** Method to greet the audience. */
	protected static void greetAudienceV01(Nao nao) {
		NaoUtil.say(nao, "Hello Everybody.");
		NaoUtil.waveHello(nao);
		NaoUtil.say(nao,
				"Thank you for coming. I will now give you a short presentation.");
	}

	/** Method for playing the first part of the presentation. First variant. */
	protected static void presentationV01(Nao nao) {
		StringBuffer talk = new StringBuffer();
		talk.append("First of all let me introduce myself. My name is Chuck.");
		talk.append("I am working at the software technology lab of the University of Dresden.");
		talk.append("I am very happy being part of a great team.");
		talk.append("Now my human colleage will continue and lead you through the rest of the presentation.");
		talk.append("Thank you.");
		NaoUtil.say(nao, talk.toString());
	}

	/**
	 * Method for playing the first part of the presentation. Second (more
	 * efficient) variant.
	 */
	protected static void presentationV02(Nao nao) {
		StringBuffer talk = new StringBuffer();
		talk.append("First of all let me introduce myself. My name is Chuck.");
		talk.append("I am working at the software technology lab of the University of Dresden.");
		talk.append("Foo. This is exhausting.");
		NaoUtil.say(nao, talk.toString());
		sitDown(nao);

		talk = new StringBuffer();
		talk.append("I am very happy being part of a great team.");
		talk.append("Now my human colleage will continue and lead you through the rest of the presentation.");
		talk.append("Thank you.");
		NaoUtil.say(nao, talk.toString());
	}
}
