

# Introduction #
The JouleUnit workbench is Eclipse-based additional tooling for the JouleUnit framework, especially for the visualization of JouleUnit profiling and testing results.

This documentation explains how to install and use the JouleUnit workbench and the provided examples.

# Installation #
To run the JouleUnit Workbench, Eclipse is required.

  * If you do not have an Eclipse instance ready to use yet, download Eclipse from http://eclipse.org/

  * We recommend to use the Modeling Tools Edition, but feel free to use another Eclipse version instead.

  * If you want to use JouleUnit to energy profile and test Android applications, you will need to install the Android Development Tools (ADT, http://developer.android.com/sdk/installing/installing-adt.html) and The Android SDK (http://developer.android.com/sdk/) as well.

  * Additionally, for Android testing you will need to install the _EMFText Access_ and the _EMFText Shared ANTLR 3.4.0 Runtime_ plugins from the _EMFText_ core feature at http://www.emftext.org/update/

## Running the Joule Unit Workbench from the SVN ##
Currently, not JouleUnit Update site exist, yet. Therefore, you have to check out the JouleUnit plugins from the SVN and run them in a Runtime Eclipse instance or to build your own deployable plugin JARs for your Eclipse instance.

To check out the JouleUnit plugins you will require an SVN client, e.g., Subversive (http://www.eclipse.org/subversive/).

  * Once you have an SVN client running, check the Eclipse plugin projects located at https://jouleunit.googlecode.com/svn/trunk/workbench/plugins and import them into your Eclipse workspace.

  * Please note that the following plugins are only required when JouleUnit shall be used for the profiling of Android applications:
    * org.qualitune.jouleunit.android.testrun,
    * org.qualitune.jouleunit.android.testrun.resource, and
    * org.qualitune.jouleunit.android.testrun.resource.ui

  * Select one of the plugin projects in the workspace, open the context menu with a secondary mouse click and select _Run As -> Eclipse Application_

  * Alternatively you can export the plugin projects as JARs into the dropins folder of your Eclipse distribution and restart your Eclipse instance.

# Using the Joule Unit Workbench #
Your runtime Eclipse or restarted Eclipse instance should now contain a new perspective called JouleUnit.

  * You can open the JouleUnit perspective by the menu option _Window -> Open Perspective -> Other ... -> JouleUnit_

  * Your Eclispe instance should now look as shown in the screenshot below:

![http://jouleunit.googlecode.com/svn/trunk/images/perspective.png](http://jouleunit.googlecode.com/svn/trunk/images/perspective.png)

  * At the left hand side you have the project explorer, to manage your JouleUnit projects.

  * At the center, you have the _Test Run Details_ view, the _Test Run Results_ view, and the _Console_ view

  * The _Test Run Details_ view allows to graphically illustrate a single test of a test run or a sequence of tests within a test run.

  * The _Test Run Results_ view gives an overview over all test runs of the last executed test run.

  * The _Console_ view documents currently executed test runs and their progress.

# Joule Unit Examples #
Select one of the following examples to get further used to the JouleUnit Workbench:

  * AndroidExample (a hello world example showing how to energy profile Android applications).