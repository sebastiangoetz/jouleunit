package org.qualitune.jouleunit.ui;

import java.net.URL;
import java.util.Date;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class HistoryTestResultObject {

	private String id = "";
	private long modifiedOn = 0;
	private String location = "";
	private boolean failed = false;
	private long startTime = 0;
	private long endTime = 0;
	private long duration = 0;
	private String avgPowerRate = "";
	private String consumption = "";
	private boolean fileSavedLocally = false;
	private int number = -1;

	private int avgEnergyConsumptionComparison = -2; //used to calc comparation between two testSuites and is used to get a certain IMG (with the vlaue it calcs)
	private int avgPowerRateComparison = -2;

	private double previousHTROAvgEnergyConsumption = 0;
	private double previousHTROAvgPowerRate = 0;

	public static final int AVG_POWER_RATE = 0;
	public static final int AVG_ENERGY_CONSUMPTION = 1;

	//the current history-object compared to current test
	private static final Image IMG_INCREASED = getImage("up_c.gif"); //if compare-method returns 1
	private static final Image IMG_DECREASED = getImage("down_c.gif");
	private static final Image IMG_SAME = getImage("equal2.gif");
	private static final Image IMG_NOT_AVAILABLE = getImage("dont_know.gif");

	public HistoryTestResultObject() {

	}

	public HistoryTestResultObject(int number, String id, long modifiedOn, String location, boolean failed, long startTime, long endTime, long duration, String avgPowerRate, String consumption, boolean fileSavedLocally) {
		super();
		this.number = number;
		this.id = id;
		this.modifiedOn = modifiedOn;
		this.location = location;
		this.failed = failed;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.avgPowerRate = avgPowerRate;
		this.consumption = consumption;
		this.fileSavedLocally = fileSavedLocally;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getAvgPowerRate() {
		return avgPowerRate;
	}

	public void setAvgPowerRate(String avgPowerRate) {
		this.avgPowerRate = avgPowerRate;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public boolean isFileSavedLocally() {
		return fileSavedLocally;
	}

	public void setFileSavedLocally(boolean fileSavedLocally) {
		this.fileSavedLocally = fileSavedLocally;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getCreatedOn() {
		return new Date(startTime);
	}

	public String getLocation() {
		return location;
	}

	// Helper Method to load the images
	private static Image getImage(String file) {
		//return UIPlugin.imageDescriptorFromPlugin(JouleUnitUiPlugIn.PLUGIN_ID, "/icons/" + file).createImage();
		Bundle bundle = FrameworkUtil.getBundle(HistoryTestResultObject.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();
	}

	public Image interpretValueAsImage(int compareTo) {
		switch (compareTo) {
		case 0: {
			switch (avgPowerRateComparison) {
			case 0:
				return IMG_SAME;
			case -1:
				return IMG_DECREASED;
			case 1:
				return IMG_INCREASED;
			default:
				return IMG_NOT_AVAILABLE;
			}
		}
		case 1: {
			switch (avgEnergyConsumptionComparison) {
			case 0:
				return IMG_SAME;
			case -1:
				return IMG_DECREASED;
			case 1:
				return IMG_INCREASED;
			default:
				return IMG_NOT_AVAILABLE;
			}
		}
		}
		return null;
	}

	public void setAvgEnergyConsumptionComparison(double value) {
		this.avgEnergyConsumptionComparison = Double.compare(value, Double.parseDouble(consumption));
	}

	public void setAvgPowerRateComparison(double value) {
		this.avgPowerRateComparison = Double.compare(value, Double.parseDouble(avgPowerRate));
	}

	public void setPreviousHTROAvgEnergyConsumption(double previousHTROAvgEnergyConsumption) {
		this.previousHTROAvgEnergyConsumption = previousHTROAvgEnergyConsumption;
		this.avgEnergyConsumptionComparison = Double.compare(previousHTROAvgEnergyConsumption, Double.parseDouble(consumption));
	}

	public void setPreviousHTROAvgPowerRate(double previousHTROAvgPowerRate) {
		this.previousHTROAvgPowerRate = previousHTROAvgPowerRate;
		this.avgPowerRateComparison = Double.compare(previousHTROAvgPowerRate, Double.parseDouble(avgPowerRate));
	}

	public double getAvgEnergyConsumptionDiff() {
		return previousHTROAvgEnergyConsumption - Double.parseDouble(consumption);
	}

	public double getAvgPowerRateDiff() {
		return previousHTROAvgPowerRate - Double.parseDouble(avgPowerRate);
	}

	public Date getModifiedOn() {
		return new Date(modifiedOn);
	}

	public void setModifiedOn(long modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "HTRO: No. " + this.number
		+ " ID " + this.id
		+ " Mod " +	this.modifiedOn
		+ " Loc " +	this.location
		+ " failed " + this.failed
		+ " start " + this.startTime
		+ " end " + this.endTime
		+ " duration " + this.duration
		+ " APR" + this.avgPowerRate
		+ " CON " + this.consumption
		+ " saved " + this.fileSavedLocally + "\n";
	}
}
