package org.qualitune.jouleunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of {@link JouleProfiler} containing the functionality
 * to profile a {@link Method} multiple times.
 * 
 * @author Claas Wilke
 */
public abstract class AbstractProfilerNTimes implements JouleProfiler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#profileNTimes(java.lang.Object,
	 * java.lang.String, java.lang.Object[], int)
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, int iterations) throws ProfilingException {

		return profileNTimes(source, methodName, args, null, null, null, null,
				iterations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#profileNTimes(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.String,
	 * java.lang.Object[], java.lang.String, java.lang.Object[], int)
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, String setUpMethodName, Object[] setUpArgs,
			String tearDownMethodName, Object[] tearDownArgs, int iterations)
			throws ProfilingException {

		return profileNTimes(source, methodName, args, null, setUpMethodName,
				setUpArgs, null, tearDownMethodName, tearDownArgs, iterations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qualitune.jouleunit.JouleProfiler#profileNTimes(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object, java.lang.String,
	 * java.lang.Object[], java.lang.Object, java.lang.String,
	 * java.lang.Object[], int)
	 */
	public List<EnergyProfile> profileNTimes(Object source, String methodName,
			Object[] args, Object setUpSource, String setUpMethodName,
			Object[] setUpArgs, Object tearDownSource,
			String tearDownMethodName, Object[] tearDownArgs, int iterations)
			throws ProfilingException {

		if (source == null)
			throw new IllegalArgumentException(
					"Argument 'source' cannot be null.");
		else if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'methodName' cannot be null or empty.");
		else if (args == null)
			throw new IllegalArgumentException(
					"Argument 'args' cannot be null.");
		else if (setUpMethodName != null && setUpMethodName.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'setUpMethodName' cannot be empty.");
		else if (setUpMethodName != null && setUpArgs == null)
			throw new IllegalArgumentException(
					"Argument 'setUpArgs' cannot be null if 'setUpMethod' is given.");
		else if (tearDownMethodName != null && tearDownMethodName.length() == 0)
			throw new IllegalArgumentException(
					"Argument 'tearDownMethodName' cannot be empty.");
		else if (tearDownMethodName != null && tearDownArgs == null)
			throw new IllegalArgumentException(
					"Argument 'tearDownArgs' cannot be null if 'tearDownMethod' is given.");
		else if (iterations <= 0)
			throw new IllegalArgumentException(
					"Argument 'iterations' must be positive.");

		/* Retrieve the argument types. */
		Class<?>[] argTypes = new Class[args.length];

		for (int index = 0; index < args.length; index++)
			argTypes[index] = args[index].getClass();
		// end for.

		/* Try to find the method to invoke. */
		Method methodToProfile = findMethod(source, methodName, argTypes);

		Method setUpMethod = null;

		if (setUpMethodName != null) {
			Class<?>[] setUpArgTypes = new Class[setUpArgs.length];

			for (int index = 0; index < setUpArgs.length; index++)
				setUpArgTypes[index] = setUpArgs[index].getClass();
			// end for.

			if (setUpSource == null)
				setUpSource = source;
			// no else.

			setUpMethod = findMethod(setUpSource, setUpMethodName,
					setUpArgTypes);
		}
		// no else.

		Method tearDownMethod = null;

		if (tearDownMethodName != null) {
			Class<?>[] tearDownArgTypes = new Class[tearDownArgs.length];

			for (int index = 0; index < tearDownArgs.length; index++)
				tearDownArgTypes[index] = tearDownArgs[index].getClass();
			// end for.

			if (tearDownSource == null)
				tearDownSource = source;
			// no else.

			tearDownMethod = findMethod(tearDownSource, tearDownMethodName,
					tearDownArgTypes);
		}
		// no else.

		/* Probably initialize the EProfiler. */
		if (!isCalibrated())
			calibrate();
		// no else.

		List<EnergyProfile> result = new ArrayList<EnergyProfile>(iterations);

		/* Do the profiling. */
		for (int index = 0; index < iterations; index++) {
			try {
				if (setUpMethod != null) {
					setUpMethod.setAccessible(true);
					setUpMethod.invoke(setUpSource, setUpArgs);
				}
				// no else.

				startProfiling();

				methodToProfile.setAccessible(true);
				methodToProfile.invoke(source, args);

				result.add(endProfiling());

				if (tearDownMethod != null) {
					tearDownMethod.setAccessible(true);
					tearDownMethod.invoke(tearDownSource, tearDownArgs);
				}
				// no else.
			}

			catch (IllegalArgumentException e) {
				throw new ProfilingException(
						"Profiling failed during method invocation.", e);
			} catch (IllegalAccessException e) {
				throw new ProfilingException(
						"Profiling failed during method invocation.", e);
			} catch (InvocationTargetException e) {
				throw new ProfilingException(
						"Profiling failed during method invocation.", e);
			}
		}
		// end for.

		return result;
	}

	/**
	 * Helper method to find a {@link Method} using reflection.
	 * 
	 * @param source
	 *            The {@link Object} on which the {@link Method} shall be
	 *            invoked.
	 * @param methodName
	 *            The name of the {@link Method}.
	 * @param argTypes
	 *            The types of the arguments of the {@link Method}.
	 * @return The found {@link Method} if any.
	 * @throws ProfilingException
	 *             Thrown, if the {@link Method} cannot be found.
	 */
	protected static Method findMethod(Object source, String methodName,
			Class<?>[] argTypes) throws ProfilingException {
		Method result;

		try {
			Class<?> sourceClass;
			if (!(source instanceof Class<?>))
				sourceClass = source.getClass();
			else
				sourceClass = (Class<?>) source;

			result = sourceClass.getDeclaredMethod(methodName, argTypes);
		}

		catch (SecurityException e) {
			throw new ProfilingException(
					"Profiling failed during retrieval of method to invoke.", e);
		}

		catch (NoSuchMethodException e) {
			/* Try to find it manually (for probably existing primitive types. */
			result = findMethodManually(source, methodName, argTypes);
		}
		return result;
	}

	/**
	 * Helper method to find a {@link Method} if Java reflections do not work
	 * (e.g., when having arguments of primitive types).
	 * 
	 * @param source
	 *            The {@link Object} to which the {@link Method} to be resolved
	 *            belongs to.
	 * @param methodName
	 *            The name of the {@link Method}.
	 * @param argTypes
	 *            The types of the arguments as an array of {@link Class}es.
	 *            Primitive types may have the wrong {@link Class}, e.g.,
	 *            {@link Integer} instead of {@link int.class}.
	 * @throws ProfilingException
	 *             Thrown, if such a {@link Method} cannot be found.
	 */
	protected static Method findMethodManually(Object source,
			String methodName, Class<?>[] argTypes) throws ProfilingException {
		Method result = null;

		Class<?> sourceClass;
		if (!(source instanceof Class<?>))
			sourceClass = source.getClass();
		else
			sourceClass = (Class<?>) source;

		outer: for (Method method : sourceClass.getDeclaredMethods()) {

			if (!method.getName().equals(methodName))
				continue;
			// no else.

			if (method.getParameterTypes().length != argTypes.length)
				continue;
			// no else.

			Class<?> paramTypes[] = method.getParameterTypes();

			/* Compare parameter types. */
			for (int index = 0; index < argTypes.length; index++) {
				if (!argTypes[index].isAssignableFrom(paramTypes[index])) {
					/* Check for auto-boxing. */
					if (!(paramTypes[index].isPrimitive() && ((paramTypes[index]
							.isAssignableFrom(Boolean.TYPE) && argTypes[index]
							.isAssignableFrom(Boolean.class))
							|| (paramTypes[index]
									.isAssignableFrom(Character.TYPE) && argTypes[index]
									.isAssignableFrom(Character.class))
							|| (paramTypes[index].isAssignableFrom(Byte.TYPE) && argTypes[index]
									.isAssignableFrom(Byte.class))
							|| (paramTypes[index].isAssignableFrom(Short.TYPE) && argTypes[index]
									.isAssignableFrom(Short.class))
							|| (paramTypes[index]
									.isAssignableFrom(Integer.TYPE) && argTypes[index]
									.isAssignableFrom(Integer.class))
							|| (paramTypes[index].isAssignableFrom(Long.TYPE) && argTypes[index]
									.isAssignableFrom(Long.class))
							|| (paramTypes[index].isAssignableFrom(Float.TYPE) && argTypes[index]
									.isAssignableFrom(Float.class))
							|| (paramTypes[index].isAssignableFrom(Double.TYPE) && argTypes[index]
									.isAssignableFrom(Double.class)) || (paramTypes[index]
							.isAssignableFrom(Void.TYPE) && argTypes[index]
							.isAssignableFrom(Void.class))))) {
						continue outer;
					}
					// no else.
				}
				// no else.
			}
			// end for.

			result = method;
			break;
		}
		// end for.

		if (result == null) {
			StringBuffer msg = new StringBuffer();
			msg.append("Cannot find method '" + methodName
					+ "' with argument types [");
			for (int index = 0; index < argTypes.length; index++) {
				if (index > 0)
					msg.append(", ");
				// no else.
				msg.append(argTypes[index].getName());
			}
			// end for.
			msg.append("].");

			throw new ProfilingException(msg.toString());
		}

		return result;
	}

}
