SYNTAXDEF testrun
FOR <http://www.qualitune.org/jouleunit/android/testrun>
START TestRun

OPTIONS {
	reloadGeneratorModel = "true";
	overrideManifest = "false";
}

TOKENS {
	DEFINE SL_COMMENT $ '//'(~('\n'|'\r'|'\uffff'))* $;
	DEFINE ML_COMMENT $ '/*'.*'*/'$;
	DEFINE NUMBER $('0'..'9')+$;
	DEFINE PACKAGE TEXT + $('.'$ + TEXT + $)+$;
	DEFINE TESTID PACKAGE + $'#'$ + TEXT;
}

TOKENSTYLES {
	"ML_COMMENT" COLOR #008000, ITALIC;
	"SL_COMMENT" COLOR #008000, ITALIC;
}

RULES {
	TestRun ::=
		"TestRun" #1 name[TEXT] !0
		("applicationUnderTest" #1 ":" #1 aut['"','"'] #1 packageUnderTest[PACKAGE] !0)?
		("unitTests" #1 ":" #1 junitApk['"','"'] #1 junitPackage[PACKAGE] !0)?
		("numberOfRuns" #1 ":" #1 noOfRuns[NUMBER] !0)?
		("idleTime" #1 ":" #1 idleTime[NUMBER] !0)?
		("hardwareProfiling" #1 ":" #1 hardwareProfilingOn["on":"off"] !0)?
		("run" #1 ":" #1 remoteRun["onServer":"local"] !0)?
		("eachTestWithFullBattery" #1 ":" #1 waitForFullBattery["on":"off"] !0)?
		(executables)+;
		
	// Test Executables
	JunitTestCase ::=
		!0
		"UnitTestCase" #1 name[TESTID];

	TestSuite ::=
		!0
		"TestSuite" #1 name[TEXT] #1 "{" !0
		    (!1 "SetUp" #1 "{" !2
		    	setUp !1
		    "}" !0)?
		    (!1 "TearDown" #1 "{" !2
		    	tearDown !1
		    "}" !0)?
		    (!1	executables!0)+ 
		"}";

	TestCase ::=
		!0
		"TestCase" #1 name[TEXT] #1 "{" !1
			behavior !0
		"}";
		
	// Statements
	Block ::= 
		(statements !0)*;
	
	ClickOnScreenStatement ::=
		"CLICK" #1 "ON" #1 "SCREEN" #1 x[NUMBER] #1 y[NUMBER];

	CursorStatement ::=
		"CURSOR" direction[];
		
	DisplayStatement ::=
		"DISPLAY" #1 switchOn["ON":"OFF"];
		
	EnterStatement ::=
		"ENTER";
		
	HomeButtonStatement ::=
		"HOME" #1 "BUTTON";
		
	OpenSettingsStatement ::=
		"OPEN" #1 "SETTINGS";
		
	SendPortMessageStatement ::=
		"SEND" #1 "MESSAGE" #1 ip['"','"'] #1 port[NUMBER] #1 message['"','"'];
		
	StartActivityStatement ::=
		"START" #1 "ACTIVITY" #1 package['"','"'] #1 class['"','"'];
		
	WaitStatement ::= 
		"WAIT" #1 "FOR" #1 seconds[NUMBER];
		
	UnlockStatement ::=
		"UNLOCK";
		
	TestStatement ::=
		"Test" #1 name[TEXT] 
			(!1	statements !0
			| #1 "{"
			(!1 statements)+ !0
		"}");
}