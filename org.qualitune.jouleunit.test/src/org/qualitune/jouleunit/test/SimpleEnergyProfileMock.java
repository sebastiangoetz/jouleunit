package org.qualitune.jouleunit.test;

import org.qualitune.jouleunit.EnergyProfile;
import org.qualitune.jouleunit.SimpleEnergyProfile;

/**
 * Mock implementation of {@link EnergyProfile} for testing.
 * 
 * @author Claas Wilke
 */
public class SimpleEnergyProfileMock extends SimpleEnergyProfile {

	/** ID for serialization. */
	private static final long serialVersionUID = -3784369529602448139L;

	/** The consumed energy of this {@link SimpleEnergyProfileMock}. */
	protected long consumedEnergy;

	/** The duration of this {@link SimpleEnergyProfileMock}. */
	protected long duration;

	/**
	 * Creates a new {@link SimpleEnergyProfileMock}.
	 * 
	 * @consumedEnergy The consumed energy of this {@link SimpleEnergyProfileMock}.
	 */
	public SimpleEnergyProfileMock(long consumedEnergy) {
		super();

		this.consumedEnergy = consumedEnergy;
	}

	/**
	 * Creates a new {@link SimpleEnergyProfileMock}.
	 * 
	 * @consumedEnergy The consumed energy of this {@link SimpleEnergyProfileMock}.
	 * @duration The duration of this {@link SimpleEnergyProfileMock}.
	 */
	public SimpleEnergyProfileMock(long consumedEnergy, long duration) {
		super();

		this.consumedEnergy = consumedEnergy;
		this.duration = duration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.eunit.EProfile#getConsumedEnergy()
	 */
	@Override
	public double getConsumedEnergy() {
		return this.consumedEnergy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.qualitune.jouleunit.EnergyProfile#getDuration()
	 */
	@Override
	public long getDuration() {
		return this.duration;
	}
}
