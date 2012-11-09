This is the WT210 profiling part of the JouleUnit framework.

The WT210 is a power meter provided by Yokogawa (http://tmi.yokogawa.com/).
The implementation assumes that the power meter is connected to your test
PC via the GPIB bus either by using a GPIB-PCI adapter or a GPIB-USB adapter
provided by National Instruments (http://www.ni.com/).

Requires the JNI library for Java that is public available under
http://download.oracle.com/javase/6/docs/technotes/guides/jni/

Besides, the GPIB Java interface provided by Jean-Michel DRICOT is required
http://jpib.sourceforge.net/

To execute the GPIB-based profiler, make sure that the dlls in the lib
directory of this project are registered at your systems PATH configuration.


JouleUnit is an extension of the popular JUnit framework that allows 
testing Java applications w.r.t. their energy consumption.

JouleUnit is published under GPL. More details on the GPL can be found
in the LICENSE.txt

JouleUnit has been developed at the TU Dresden by Claas Wilke 
(claas.wilke@tu-dresden.de).

For further details visit http://www.jouleunit.org/

(c) 2011 by Claas Wilke.

Joule Unit has been funded within the project ZESSY/QualiTune #080951806 
by the European Social Fund (ESF) and Federal State of Saxony.