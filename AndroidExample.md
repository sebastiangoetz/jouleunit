

# Introduction #

This example shows how to energy profile an Android hello world application by using JouleUnit and the JouleUnitWorkbench. We assume that you installed the JouleUnitWorkbench already. Otherwise check the respective [wiki page](http://code.google.com/p/jouleunit/wiki/JouleUnitWorkbench) for further details.

  * Please also note that you installed all plugins necessary for the Android extensions of the JouleUnit Workbench as mentioned on the respective [wiki page](http://code.google.com/p/jouleunit/wiki/JouleUnitWorkbench).

## Example Sources ##
This example consists of two Eclispe projects, located within the JouleUnit SVN. Please load the following projects from the SVN (https://jouleunit.googlecode.com/svn/trunk/workbench/examples/android) and import them into your Eclipse workspace:

  * helloworld
  * helloworldtest

Afterwards, the _Project Explorer_ should look as follows:

![http://jouleunit.googlecode.com/svn/trunk/images/projectexplorer.png](http://jouleunit.googlecode.com/svn/trunk/images/projectexplorer.png)

The first project, _helloworld_ contains an example Android application simply displaying a _Hello World_ message once started on an Android device. If you are new to Android development, you can run the tutorial available at http://developer.android.com/training/basics/firstapp/creating-project.html to get further information about Android applications and how to run them on mobile devices.

The second project _helloworldtest_ contains a simple JUnit test suite consisting of one test case. The JUnit test suite is contained in the _src_ directory and called _org.jouleunit.helloworld.test.HelloWorldTest_. If you want to learn more about JUnit testing of Android, please have a look at http://developer.android.com/tools/testing/testing_eclipse.html

If you open the test suite, it should look as follows:

![http://jouleunit.googlecode.com/svn/trunk/images/helloworldtest.png](http://jouleunit.googlecode.com/svn/trunk/images/helloworldtest.png)

Once deployed and started on an Android device, the test case _testIdle()_ will open the hello world application and will simply wait for ten seconds.

  * To run the test suite as a normal JUnit test, select _Run As -> Android JUnit Test_ in the context menu. Afterwards, the test suite should be deployed on your Android device and being executed.

The exection should result with a green testing bar within the JUnit result view in Eclipse. If so, you can now apply the same JouleUnit test as a JouleUnit test runner.

## Running JouleUnit Tests ##
In theory, JouleUnit tests for Android are standard Android JUnit tests, meaning, each JUnit test can be executed and its energy consumption being profiled by JouleUnit.

In contrast to the run dialog to trigger JUnit, we use a simple script language that tells the JouleUnit runner, which Android application shall be tested with which JUnit test cases.

Thus, within the _helloworldtest_ project, the _helloworld.testrun_ file exist that can be used to trigger a JouleUnit profiling run. A screenshot of the example _helloworld.testrun_ file is shown below:

![http://jouleunit.googlecode.com/svn/trunk/images/testrun.png](http://jouleunit.googlecode.com/svn/trunk/images/testrun.png)

  * First, a name of the test run (here _ExampleWallpaper_) is given

  * Following, the relative path to the compiled application (apk) file that shall be profiled is given, together with the package id of the respective Android application (here _org.jouleunit.helloworld_, the package id is configured within the manifest file of the respective Android project).

  * Similarly, next, the relative path to the compiled JUnit test suite and its package id are given.

  * The parameter _hardwareProfiling_ configures whether or not to profile further hardware information together with energy consumption (currently, the profiling of CPU utilization, WiFi traffic and display brightness are supported).

  * The parameter _run_ configures whether to run the test cases on a local device or on a remote tesing server (not yet being publicly available).

  * Afterwards, the test cases being part of the test run are configured, which, in this case is only the _testIdle_ test case, identified by the canonical class and method name.

### Running the TestRun ###
  * To trigger the test run, select the file in the project explorer and select the context menu option _Run As -> testrun application_

  * On the Android console within the _Console_ view you should see the progress of the test run.

  * Hint, if the Android console shows the message _Error: DebugBridge is not initialized. TestRun cancelled._, simply try to rerun the testrun.

The following screenshot shows the result of the profiling run:

![http://jouleunit.googlecode.com/svn/trunk/images/helloworldresult.png](http://jouleunit.googlecode.com/svn/trunk/images/helloworldresult.png)

  * As can be seen, the _Test Run Results_ view contains only one test case, which can be visualized in the _Test Run Details_ view when double clicking on the entry in the view.

  * In our example, the test run resulted in a negative measured power rate, as the device we used for testing was currently connected via USB to a desktop PC and thus, charged. To avoid these effects you have to use either WiFi based communication with the test device or to profile the power provided to your test device via the USB connection as well.

# Summary #
This tutorial shortly explained how to used the JouleUnitWorkbench for the profiling of Android applications. As this tutorial is rather new it may be incomplete or missing required information. Thus, if you have trouble to understand all the stuff, do not hesitate to contact us and help us to improve the tutorial.