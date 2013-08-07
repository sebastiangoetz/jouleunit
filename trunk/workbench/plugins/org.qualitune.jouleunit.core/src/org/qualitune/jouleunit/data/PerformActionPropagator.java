package org.qualitune.jouleunit.data;

import java.util.ArrayList;
import java.util.Iterator;

public class PerformActionPropagator {

	private static PerformActionPropagator instance = null;

	private ArrayList<PerformActionListener> myListeners = new ArrayList<PerformActionListener>();

	public static PerformActionPropagator getInstance() {
		if (instance == null) {
			return instance = new PerformActionPropagator();
		}
		return instance;
	}

	private PerformActionPropagator() {

	}

	public void registerEventListener(PerformActionListener listener) {
		if (!myListeners.contains(listener))
			myListeners.add(listener);
	}

	public void unregisterEventListener(PerformActionListener listener) {
		myListeners.remove(listener);
	}

	public void propagateEvent(String id, int action) {
		propagateEvent(id, action, null);
	}

	public void propagateEvent(String id, int action, Object o) {

		for (Iterator<PerformActionListener> iter = myListeners.iterator(); iter.hasNext();) {
			PerformActionListener element = (PerformActionListener) iter.next();
			element.handleEvent(id, action, o);
		}
	}
}
