grammar Testrun;

options {
	superClass = TestrunANTLRParserBase;
	backtrack = true;
	memoize = true;
}

@lexer::header {
	package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;
}

@lexer::members {
	public java.util.List<org.antlr.runtime3_4_0.RecognitionException> lexerExceptions  = new java.util.ArrayList<org.antlr.runtime3_4_0.RecognitionException>();
	public java.util.List<Integer> lexerExceptionsPosition = new java.util.ArrayList<Integer>();
	
	public void reportError(org.antlr.runtime3_4_0.RecognitionException e) {
		lexerExceptions.add(e);
		lexerExceptionsPosition.add(((org.antlr.runtime3_4_0.ANTLRStringStream) input).index());
	}
}
@header{
	package org.qualitune.jouleunit.android.testrun.resource.testrun.mopp;
}

@members{
	private org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolverFactory tokenResolverFactory = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTokenResolverFactory();
	
	/**
	 * the index of the last token that was handled by collectHiddenTokens()
	 */
	private int lastPosition;
	
	/**
	 * A flag that indicates whether the parser should remember all expected elements.
	 * This flag is set to true when using the parse for code completion. Otherwise it
	 * is set to false.
	 */
	private boolean rememberExpectedElements = false;
	
	private Object parseToIndexTypeObject;
	private int lastTokenIndex = 0;
	
	/**
	 * A list of expected elements the were collected while parsing the input stream.
	 * This list is only filled if <code>rememberExpectedElements</code> is set to
	 * true.
	 */
	private java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal> expectedElements = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal>();
	
	private int mismatchedTokenRecoveryTries = 0;
	/**
	 * A helper list to allow a lexer to pass errors to its parser
	 */
	protected java.util.List<org.antlr.runtime3_4_0.RecognitionException> lexerExceptions = java.util.Collections.synchronizedList(new java.util.ArrayList<org.antlr.runtime3_4_0.RecognitionException>());
	
	/**
	 * Another helper list to allow a lexer to pass positions of errors to its parser
	 */
	protected java.util.List<Integer> lexerExceptionsPosition = java.util.Collections.synchronizedList(new java.util.ArrayList<Integer>());
	
	/**
	 * A stack for incomplete objects. This stack is used filled when the parser is
	 * used for code completion. Whenever the parser starts to read an object it is
	 * pushed on the stack. Once the element was parser completely it is popped from
	 * the stack.
	 */
	java.util.List<org.eclipse.emf.ecore.EObject> incompleteObjects = new java.util.ArrayList<org.eclipse.emf.ecore.EObject>();
	
	private int stopIncludingHiddenTokens;
	private int stopExcludingHiddenTokens;
	private int tokenIndexOfLastCompleteElement;
	
	private int expectedElementsIndexOfLastCompleteElement;
	
	/**
	 * The offset indicating the cursor position when the parser is used for code
	 * completion by calling parseToExpectedElements().
	 */
	private int cursorOffset;
	
	/**
	 * The offset of the first hidden token of the last expected element. This offset
	 * is used to discard expected elements, which are not needed for code completion.
	 */
	private int lastStartIncludingHidden;
	
	protected void addErrorToResource(final String errorMessage, final int column, final int line, final int startIndex, final int stopIndex) {
		postParseCommands.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>() {
			public boolean execute(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
				if (resource == null) {
					// the resource can be null if the parser is used for code completion
					return true;
				}
				resource.addProblem(new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunProblem() {
					public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity getSeverity() {
						return org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemSeverity.ERROR;
					}
					public org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType getType() {
						return org.qualitune.jouleunit.android.testrun.resource.testrun.TestrunEProblemType.SYNTAX_ERROR;
					}
					public String getMessage() {
						return errorMessage;
					}
					public java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunQuickFix> getQuickFixes() {
						return null;
					}
				}, column, line, startIndex, stopIndex);
				return true;
			}
		});
	}
	
	public void addExpectedElement(org.eclipse.emf.ecore.EClass eClass, int[] ids) {
		if (!this.rememberExpectedElements) {
			return;
		}
		int terminalID = ids[0];
		int followSetID = ids[1];
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement terminal = org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunFollowSetProvider.TERMINALS[terminalID];
		org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[] containmentFeatures = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[ids.length - 2];
		for (int i = 2; i < ids.length; i++) {
			containmentFeatures[i - 2] = org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunFollowSetProvider.LINKS[ids[i]];
		}
		org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunContainmentTrace containmentTrace = new org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunContainmentTrace(eClass, containmentFeatures);
		org.eclipse.emf.ecore.EObject container = getLastIncompleteElement();
		org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal expectedElement = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal(container, terminal, followSetID, containmentTrace);
		setPosition(expectedElement, input.index());
		int startIncludingHiddenTokens = expectedElement.getStartIncludingHiddenTokens();
		if (lastStartIncludingHidden >= 0 && lastStartIncludingHidden < startIncludingHiddenTokens && cursorOffset > startIncludingHiddenTokens) {
			// clear list of expected elements
			this.expectedElements.clear();
			this.expectedElementsIndexOfLastCompleteElement = 0;
		}
		lastStartIncludingHidden = startIncludingHiddenTokens;
		this.expectedElements.add(expectedElement);
	}
	
	protected void collectHiddenTokens(org.eclipse.emf.ecore.EObject element) {
	}
	
	protected void copyLocalizationInfos(final org.eclipse.emf.ecore.EObject source, final org.eclipse.emf.ecore.EObject target) {
		if (disableLocationMap) {
			return;
		}
		postParseCommands.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>() {
			public boolean execute(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				locationMap.setCharStart(target, locationMap.getCharStart(source));
				locationMap.setCharEnd(target, locationMap.getCharEnd(source));
				locationMap.setColumn(target, locationMap.getColumn(source));
				locationMap.setLine(target, locationMap.getLine(source));
				return true;
			}
		});
	}
	
	protected void copyLocalizationInfos(final org.antlr.runtime3_4_0.CommonToken source, final org.eclipse.emf.ecore.EObject target) {
		if (disableLocationMap) {
			return;
		}
		postParseCommands.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>() {
			public boolean execute(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				if (source == null) {
					return true;
				}
				locationMap.setCharStart(target, source.getStartIndex());
				locationMap.setCharEnd(target, source.getStopIndex());
				locationMap.setColumn(target, source.getCharPositionInLine());
				locationMap.setLine(target, source.getLine());
				return true;
			}
		});
	}
	
	/**
	 * Sets the end character index and the last line for the given object in the
	 * location map.
	 */
	protected void setLocalizationEnd(java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>> postParseCommands , final org.eclipse.emf.ecore.EObject object, final int endChar, final int endLine) {
		if (disableLocationMap) {
			return;
		}
		postParseCommands.add(new org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>() {
			public boolean execute(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource resource) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunLocationMap locationMap = resource.getLocationMap();
				if (locationMap == null) {
					// the locationMap can be null if the parser is used for code completion
					return true;
				}
				locationMap.setCharEnd(object, endChar);
				locationMap.setLine(object, endLine);
				return true;
			}
		});
	}
	
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextParser createInstance(java.io.InputStream actualInputStream, String encoding) {
		try {
			if (encoding == null) {
				return new TestrunParser(new org.antlr.runtime3_4_0.CommonTokenStream(new TestrunLexer(new org.antlr.runtime3_4_0.ANTLRInputStream(actualInputStream))));
			} else {
				return new TestrunParser(new org.antlr.runtime3_4_0.CommonTokenStream(new TestrunLexer(new org.antlr.runtime3_4_0.ANTLRInputStream(actualInputStream, encoding))));
			}
		} catch (java.io.IOException e) {
			new org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunRuntimeUtil().logError("Error while creating parser.", e);
			return null;
		}
	}
	
	/**
	 * This default constructor is only used to call createInstance() on it.
	 */
	public TestrunParser() {
		super(null);
	}
	
	protected org.eclipse.emf.ecore.EObject doParse() throws org.antlr.runtime3_4_0.RecognitionException {
		this.lastPosition = 0;
		// required because the lexer class can not be subclassed
		((TestrunLexer) getTokenStream().getTokenSource()).lexerExceptions = lexerExceptions;
		((TestrunLexer) getTokenStream().getTokenSource()).lexerExceptionsPosition = lexerExceptionsPosition;
		Object typeObject = getTypeObject();
		if (typeObject == null) {
			return start();
		} else if (typeObject instanceof org.eclipse.emf.ecore.EClass) {
			org.eclipse.emf.ecore.EClass type = (org.eclipse.emf.ecore.EClass) typeObject;
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.TestRun.class) {
				return parse_org_qualitune_jouleunit_android_testrun_TestRun();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.JunitTestCase.class) {
				return parse_org_qualitune_jouleunit_android_testrun_JunitTestCase();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.TestSuite.class) {
				return parse_org_qualitune_jouleunit_android_testrun_TestSuite();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.TestCase.class) {
				return parse_org_qualitune_jouleunit_android_testrun_TestCase();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.Block.class) {
				return parse_org_qualitune_jouleunit_android_testrun_Block();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.CursorStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_CursorStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.DisplayStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_DisplayStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.EnterStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_EnterStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.HomeButtonStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_HomeButtonStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.OpenSettingsStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.SendPortMessageStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.StartActivityStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_StartActivityStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.WaitStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_WaitStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.UnlockStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_UnlockStatement();
			}
			if (type.getInstanceClass() == org.qualitune.jouleunit.android.testrun.TestStatement.class) {
				return parse_org_qualitune_jouleunit_android_testrun_TestStatement();
			}
		}
		throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunUnexpectedContentTypeException(typeObject);
	}
	
	public int getMismatchedTokenRecoveryTries() {
		return mismatchedTokenRecoveryTries;
	}
	
	public Object getMissingSymbol(org.antlr.runtime3_4_0.IntStream arg0, org.antlr.runtime3_4_0.RecognitionException arg1, int arg2, org.antlr.runtime3_4_0.BitSet arg3) {
		mismatchedTokenRecoveryTries++;
		return super.getMissingSymbol(arg0, arg1, arg2, arg3);
	}
	
	public Object getParseToIndexTypeObject() {
		return parseToIndexTypeObject;
	}
	
	protected Object getTypeObject() {
		Object typeObject = getParseToIndexTypeObject();
		if (typeObject != null) {
			return typeObject;
		}
		java.util.Map<?,?> options = getOptions();
		if (options != null) {
			typeObject = options.get(org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunOptions.RESOURCE_CONTENT_TYPE);
		}
		return typeObject;
	}
	
	/**
	 * Implementation that calls {@link #doParse()} and handles the thrown
	 * RecognitionExceptions.
	 */
	public org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunParseResult parse() {
		terminateParsing = false;
		postParseCommands = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource>>();
		org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunParseResult parseResult = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunParseResult();
		try {
			org.eclipse.emf.ecore.EObject result =  doParse();
			if (lexerExceptions.isEmpty()) {
				parseResult.setRoot(result);
			}
		} catch (org.antlr.runtime3_4_0.RecognitionException re) {
			reportError(re);
		} catch (java.lang.IllegalArgumentException iae) {
			if ("The 'no null' constraint is violated".equals(iae.getMessage())) {
				// can be caused if a null is set on EMF models where not allowed. this will just
				// happen if other errors occurred before
			} else {
				iae.printStackTrace();
			}
		}
		for (org.antlr.runtime3_4_0.RecognitionException re : lexerExceptions) {
			reportLexicalError(re);
		}
		parseResult.getPostParseCommands().addAll(postParseCommands);
		return parseResult;
	}
	
	public java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal> parseToExpectedElements(org.eclipse.emf.ecore.EClass type, org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource dummyResource, int cursorOffset) {
		this.rememberExpectedElements = true;
		this.parseToIndexTypeObject = type;
		this.cursorOffset = cursorOffset;
		this.lastStartIncludingHidden = -1;
		final org.antlr.runtime3_4_0.CommonTokenStream tokenStream = (org.antlr.runtime3_4_0.CommonTokenStream) getTokenStream();
		org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunParseResult result = parse();
		for (org.eclipse.emf.ecore.EObject incompleteObject : incompleteObjects) {
			org.antlr.runtime3_4_0.Lexer lexer = (org.antlr.runtime3_4_0.Lexer) tokenStream.getTokenSource();
			int endChar = lexer.getCharIndex();
			int endLine = lexer.getLine();
			setLocalizationEnd(result.getPostParseCommands(), incompleteObject, endChar, endLine);
		}
		if (result != null) {
			org.eclipse.emf.ecore.EObject root = result.getRoot();
			if (root != null) {
				dummyResource.getContentsInternal().add(root);
			}
			for (org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunCommand<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTextResource> command : result.getPostParseCommands()) {
				command.execute(dummyResource);
			}
		}
		// remove all expected elements that were added after the last complete element
		expectedElements = expectedElements.subList(0, expectedElementsIndexOfLastCompleteElement + 1);
		int lastFollowSetID = expectedElements.get(expectedElementsIndexOfLastCompleteElement).getFollowSetID();
		java.util.Set<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal> currentFollowSet = new java.util.LinkedHashSet<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal>();
		java.util.List<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal> newFollowSet = new java.util.ArrayList<org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal>();
		for (int i = expectedElementsIndexOfLastCompleteElement; i >= 0; i--) {
			org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal expectedElementI = expectedElements.get(i);
			if (expectedElementI.getFollowSetID() == lastFollowSetID) {
				currentFollowSet.add(expectedElementI);
			} else {
				break;
			}
		}
		int followSetID = 95;
		int i;
		for (i = tokenIndexOfLastCompleteElement; i < tokenStream.size(); i++) {
			org.antlr.runtime3_4_0.CommonToken nextToken = (org.antlr.runtime3_4_0.CommonToken) tokenStream.get(i);
			if (nextToken.getType() < 0) {
				break;
			}
			if (nextToken.getChannel() == 99) {
				// hidden tokens do not reduce the follow set
			} else {
				// now that we have found the next visible token the position for that expected
				// terminals can be set
				for (org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal nextFollow : newFollowSet) {
					lastTokenIndex = 0;
					setPosition(nextFollow, i);
				}
				newFollowSet.clear();
				// normal tokens do reduce the follow set - only elements that match the token are
				// kept
				for (org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal nextFollow : currentFollowSet) {
					if (nextFollow.getTerminal().getTokenNames().contains(getTokenNames()[nextToken.getType()])) {
						// keep this one - it matches
						java.util.Collection<org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]>> newFollowers = nextFollow.getTerminal().getFollowers();
						for (org.qualitune.jouleunit.android.testrun.resource.testrun.util.TestrunPair<org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContainedFeature[]> newFollowerPair : newFollowers) {
							org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunExpectedElement newFollower = newFollowerPair.getLeft();
							org.eclipse.emf.ecore.EObject container = getLastIncompleteElement();
							org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunContainmentTrace containmentTrace = new org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunContainmentTrace(null, newFollowerPair.getRight());
							org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal newFollowTerminal = new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal(container, newFollower, followSetID, containmentTrace);
							newFollowSet.add(newFollowTerminal);
							expectedElements.add(newFollowTerminal);
						}
					}
				}
				currentFollowSet.clear();
				currentFollowSet.addAll(newFollowSet);
			}
			followSetID++;
		}
		// after the last token in the stream we must set the position for the elements
		// that were added during the last iteration of the loop
		for (org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal nextFollow : newFollowSet) {
			lastTokenIndex = 0;
			setPosition(nextFollow, i);
		}
		return this.expectedElements;
	}
	
	public void setPosition(org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectedTerminal expectedElement, int tokenIndex) {
		int currentIndex = Math.max(0, tokenIndex);
		for (int index = lastTokenIndex; index < currentIndex; index++) {
			if (index >= input.size()) {
				break;
			}
			org.antlr.runtime3_4_0.CommonToken tokenAtIndex = (org.antlr.runtime3_4_0.CommonToken) input.get(index);
			stopIncludingHiddenTokens = tokenAtIndex.getStopIndex() + 1;
			if (tokenAtIndex.getChannel() != 99 && !anonymousTokens.contains(tokenAtIndex)) {
				stopExcludingHiddenTokens = tokenAtIndex.getStopIndex() + 1;
			}
		}
		lastTokenIndex = Math.max(0, currentIndex);
		expectedElement.setPosition(stopExcludingHiddenTokens, stopIncludingHiddenTokens);
	}
	
	public Object recoverFromMismatchedToken(org.antlr.runtime3_4_0.IntStream input, int ttype, org.antlr.runtime3_4_0.BitSet follow) throws org.antlr.runtime3_4_0.RecognitionException {
		if (!rememberExpectedElements) {
			return super.recoverFromMismatchedToken(input, ttype, follow);
		} else {
			return null;
		}
	}
	
	/**
	 * Translates errors thrown by the parser into human readable messages.
	 */
	public void reportError(final org.antlr.runtime3_4_0.RecognitionException e)  {
		String message = e.getMessage();
		if (e instanceof org.antlr.runtime3_4_0.MismatchedTokenException) {
			org.antlr.runtime3_4_0.MismatchedTokenException mte = (org.antlr.runtime3_4_0.MismatchedTokenException) e;
			String expectedTokenName = formatTokenName(mte.expecting);
			String actualTokenName = formatTokenName(e.token.getType());
			message = "Syntax error on token \"" + e.token.getText() + " (" + actualTokenName + ")\", \"" + expectedTokenName + "\" expected";
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedTreeNodeException) {
			org.antlr.runtime3_4_0.MismatchedTreeNodeException mtne = (org.antlr.runtime3_4_0.MismatchedTreeNodeException) e;
			String expectedTokenName = formatTokenName(mtne.expecting);
			message = "mismatched tree node: " + "xxx" + "; tokenName " + expectedTokenName;
		} else if (e instanceof org.antlr.runtime3_4_0.NoViableAltException) {
			message = "Syntax error on token \"" + e.token.getText() + "\", check following tokens";
		} else if (e instanceof org.antlr.runtime3_4_0.EarlyExitException) {
			message = "Syntax error on token \"" + e.token.getText() + "\", delete this token";
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedSetException) {
			org.antlr.runtime3_4_0.MismatchedSetException mse = (org.antlr.runtime3_4_0.MismatchedSetException) e;
			message = "mismatched token: " + e.token + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedNotSetException) {
			org.antlr.runtime3_4_0.MismatchedNotSetException mse = (org.antlr.runtime3_4_0.MismatchedNotSetException) e;
			message = "mismatched token: " +  e.token + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_4_0.FailedPredicateException) {
			org.antlr.runtime3_4_0.FailedPredicateException fpe = (org.antlr.runtime3_4_0.FailedPredicateException) e;
			message = "rule " + fpe.ruleName + " failed predicate: {" +  fpe.predicateText + "}?";
		}
		// the resource may be null if the parser is used for code completion
		final String finalMessage = message;
		if (e.token instanceof org.antlr.runtime3_4_0.CommonToken) {
			final org.antlr.runtime3_4_0.CommonToken ct = (org.antlr.runtime3_4_0.CommonToken) e.token;
			addErrorToResource(finalMessage, ct.getCharPositionInLine(), ct.getLine(), ct.getStartIndex(), ct.getStopIndex());
		} else {
			addErrorToResource(finalMessage, e.token.getCharPositionInLine(), e.token.getLine(), 1, 5);
		}
	}
	
	/**
	 * Translates errors thrown by the lexer into human readable messages.
	 */
	public void reportLexicalError(final org.antlr.runtime3_4_0.RecognitionException e)  {
		String message = "";
		if (e instanceof org.antlr.runtime3_4_0.MismatchedTokenException) {
			org.antlr.runtime3_4_0.MismatchedTokenException mte = (org.antlr.runtime3_4_0.MismatchedTokenException) e;
			message = "Syntax error on token \"" + ((char) e.c) + "\", \"" + (char) mte.expecting + "\" expected";
		} else if (e instanceof org.antlr.runtime3_4_0.NoViableAltException) {
			message = "Syntax error on token \"" + ((char) e.c) + "\", delete this token";
		} else if (e instanceof org.antlr.runtime3_4_0.EarlyExitException) {
			org.antlr.runtime3_4_0.EarlyExitException eee = (org.antlr.runtime3_4_0.EarlyExitException) e;
			message = "required (...)+ loop (decision=" + eee.decisionNumber + ") did not match anything; on line " + e.line + ":" + e.charPositionInLine + " char=" + ((char) e.c) + "'";
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedSetException) {
			org.antlr.runtime3_4_0.MismatchedSetException mse = (org.antlr.runtime3_4_0.MismatchedSetException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedNotSetException) {
			org.antlr.runtime3_4_0.MismatchedNotSetException mse = (org.antlr.runtime3_4_0.MismatchedNotSetException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set " + mse.expecting;
		} else if (e instanceof org.antlr.runtime3_4_0.MismatchedRangeException) {
			org.antlr.runtime3_4_0.MismatchedRangeException mre = (org.antlr.runtime3_4_0.MismatchedRangeException) e;
			message = "mismatched char: '" + ((char) e.c) + "' on line " + e.line + ":" + e.charPositionInLine + "; expecting set '" + (char) mre.a + "'..'" + (char) mre.b + "'";
		} else if (e instanceof org.antlr.runtime3_4_0.FailedPredicateException) {
			org.antlr.runtime3_4_0.FailedPredicateException fpe = (org.antlr.runtime3_4_0.FailedPredicateException) e;
			message = "rule " + fpe.ruleName + " failed predicate: {" + fpe.predicateText + "}?";
		}
		addErrorToResource(message, e.charPositionInLine, e.line, lexerExceptionsPosition.get(lexerExceptions.indexOf(e)), lexerExceptionsPosition.get(lexerExceptions.indexOf(e)));
	}
	
	private void startIncompleteElement(Object object) {
		if (object instanceof org.eclipse.emf.ecore.EObject) {
			this.incompleteObjects.add((org.eclipse.emf.ecore.EObject) object);
		}
	}
	
	private void completedElement(Object object, boolean isContainment) {
		if (isContainment && !this.incompleteObjects.isEmpty()) {
			boolean exists = this.incompleteObjects.remove(object);
			if (!exists) {
			}
		}
		if (object instanceof org.eclipse.emf.ecore.EObject) {
			this.tokenIndexOfLastCompleteElement = getTokenStream().index();
			this.expectedElementsIndexOfLastCompleteElement = expectedElements.size() - 1;
		}
	}
	
	private org.eclipse.emf.ecore.EObject getLastIncompleteElement() {
		if (incompleteObjects.isEmpty()) {
			return null;
		}
		return incompleteObjects.get(incompleteObjects.size() - 1);
	}
	
}

start returns [ org.eclipse.emf.ecore.EObject element = null]
:
	{
		// follow set for start rule(s)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[0]);
		expectedElementsIndexOfLastCompleteElement = 0;
	}
	(
		c0 = parse_org_qualitune_jouleunit_android_testrun_TestRun{ element = c0; }
	)
	EOF	{
		retrieveLayoutInformation(element, null, null, false);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_TestRun returns [org.qualitune.jouleunit.android.testrun.TestRun element = null]
@init{
}
:
	a0 = 'TestRun' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[1]);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[2]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[3]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[4]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[5]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[6]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[7]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[8]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[9]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[10]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[11]);
	}
	
	(
		(
			a2 = 'applicationUnderTest' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_4_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a2, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[12]);
			}
			
			a3 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_4_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a3, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[13]);
			}
			
			(
				a4 = QUOTED_34_34				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a4 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.qualitune.jouleunit.android.testrun.ApkFile proxy = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createApkFile();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContextDependentURIFragmentFactory<org.qualitune.jouleunit.android.testrun.TestRun, org.qualitune.jouleunit.android.testrun.ApkFile>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTestRunAutReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__AUT), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_4_0_0_4, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a4, element);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a4, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[14]);
			}
			
			(
				a5 = PACKAGE				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a5 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("PACKAGE");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a5.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a5).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a5).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a5).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a5).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String) resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__PACKAGE_UNDER_TEST), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_4_0_0_6, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a5, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[15]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[16]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[17]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[18]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[19]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[20]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[21]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[22]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[23]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[24]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[25]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[26]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[27]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[28]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[29]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[30]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[31]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[32]);
	}
	
	(
		(
			a6 = 'unitTests' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_5_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a6, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[33]);
			}
			
			a7 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_5_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[34]);
			}
			
			(
				a8 = QUOTED_34_34				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a8 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a8.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a8).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a8).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a8).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a8).getStopIndex());
						}
						String resolved = (String) resolvedObject;
						org.qualitune.jouleunit.android.testrun.ApkFile proxy = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createApkFile();
						collectHiddenTokens(element);
						registerContextDependentProxy(new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunContextDependentURIFragmentFactory<org.qualitune.jouleunit.android.testrun.TestRun, org.qualitune.jouleunit.android.testrun.ApkFile>(getReferenceResolverSwitch() == null ? null : getReferenceResolverSwitch().getTestRunJunitApkReferenceResolver()), element, (org.eclipse.emf.ecore.EReference) element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK), resolved, proxy);
						if (proxy != null) {
							Object value = proxy;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_APK), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_5_0_0_4, proxy, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a8, element);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a8, proxy);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[35]);
			}
			
			(
				a9 = PACKAGE				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a9 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("PACKAGE");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a9.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_PACKAGE), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a9).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a9).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a9).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a9).getStopIndex());
						}
						java.lang.String resolved = (java.lang.String) resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__JUNIT_PACKAGE), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_5_0_0_6, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a9, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[36]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[37]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[38]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[39]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[40]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[41]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[42]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[43]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[44]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[45]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[46]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[47]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[48]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[49]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[50]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[51]);
	}
	
	(
		(
			a10 = 'numberOfRuns' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_6_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a10, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[52]);
			}
			
			a11 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_6_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a11, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[53]);
			}
			
			(
				a12 = NUMBER				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a12 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a12.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NO_OF_RUNS), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a12).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a12).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a12).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a12).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__NO_OF_RUNS), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_6_0_0_4, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a12, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[54]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[55]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[56]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[57]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[58]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[59]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[60]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[61]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[62]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[63]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[64]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[65]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[66]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[67]);
	}
	
	(
		(
			a13 = 'idleTime' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_7_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a13, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[68]);
			}
			
			a14 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_7_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a14, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[69]);
			}
			
			(
				a15 = NUMBER				
				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a15 != null) {
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
						tokenResolver.setOptions(getOptions());
						org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
						tokenResolver.resolve(a15.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__IDLE_TIME), result);
						Object resolvedObject = result.getResolvedToken();
						if (resolvedObject == null) {
							addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a15).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a15).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a15).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a15).getStopIndex());
						}
						java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
						if (resolved != null) {
							Object value = resolved;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__IDLE_TIME), value);
							completedElement(value, false);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_7_0_0_4, resolved, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a15, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[70]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[71]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[72]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[73]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[74]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[75]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[76]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[77]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[78]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[79]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[80]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[81]);
	}
	
	(
		(
			a16 = 'hardwareProfiling' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_8_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a16, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[82]);
			}
			
			a17 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_8_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a17, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[83]);
			}
			
			(
				(
					a18 = 'on' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_8_0_0_4, true, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a18, element);
						// set value of boolean attribute
						Object value = true;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON), value);
						completedElement(value, false);
					}
					|					a19 = 'off' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_8_0_0_4, false, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a19, element);
						// set value of boolean attribute
						Object value = false;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__HARDWARE_PROFILING_ON), value);
						completedElement(value, false);
					}
				)
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[84]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[85]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[86]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[87]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[88]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[89]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[90]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[91]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[92]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[93]);
	}
	
	(
		(
			a21 = 'run' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_9_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a21, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[94]);
			}
			
			a22 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_9_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a22, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[95]);
			}
			
			(
				(
					a23 = 'onServer' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_9_0_0_4, true, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a23, element);
						// set value of boolean attribute
						Object value = true;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__REMOTE_RUN), value);
						completedElement(value, false);
					}
					|					a24 = 'local' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_9_0_0_4, false, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a24, element);
						// set value of boolean attribute
						Object value = false;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__REMOTE_RUN), value);
						completedElement(value, false);
					}
				)
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[96]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[97]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[98]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[99]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[100]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[101]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[102]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[103]);
	}
	
	(
		(
			a26 = 'eachTestWithFullBattery' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_10_0_0_0, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a26, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[104]);
			}
			
			a27 = ':' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_10_0_0_2, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a27, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[105]);
			}
			
			(
				(
					a28 = 'on' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_10_0_0_4, true, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a28, element);
						// set value of boolean attribute
						Object value = true;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY), value);
						completedElement(value, false);
					}
					|					a29 = 'off' {
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
							startIncompleteElement(element);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_10_0_0_4, false, true);
						copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a29, element);
						// set value of boolean attribute
						Object value = false;
						element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__WAIT_FOR_FULL_BATTERY), value);
						completedElement(value, false);
					}
				)
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[106]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[107]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[108]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[109]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[110]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[111]);
	}
	
	(
		(
			(
				a31_0 = parse_org_qualitune_jouleunit_android_testrun_TestExecutable				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestRun();
						startIncompleteElement(element);
					}
					if (a31_0 != null) {
						if (a31_0 != null) {
							Object value = a31_0;
							addObjectToList(element, org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_RUN__EXECUTABLES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_0_0_0_11_0_0_0, a31_0, true);
						copyLocalizationInfos(a31_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[112]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[113]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[114]);
			}
			
		)
		
	)+	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[115]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[116]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[117]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_JunitTestCase returns [org.qualitune.jouleunit.android.testrun.JunitTestCase element = null]
@init{
}
:
	a0 = 'UnitTestCase' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createJunitTestCase();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_1_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[118]);
	}
	
	(
		a1 = TESTID		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createJunitTestCase();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TESTID");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.JUNIT_TEST_CASE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.JUNIT_TEST_CASE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_1_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[119]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[120]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[121]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[122]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_TestSuite returns [org.qualitune.jouleunit.android.testrun.TestSuite element = null]
@init{
}
:
	a0 = 'TestSuite' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[123]);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[124]);
	}
	
	a2 = '{' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[125]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[126]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[127]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[128]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[129]);
	}
	
	(
		(
			a3 = 'SetUp' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_7_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a3, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[130]);
			}
			
			a4 = '{' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_7_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a4, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[131]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[132]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[133]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[134]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[135]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[136]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[137]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[138]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[139]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[140]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[141]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[142]);
			}
			
			(
				a5_0 = parse_org_qualitune_jouleunit_android_testrun_TestBehavior				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
						startIncompleteElement(element);
					}
					if (a5_0 != null) {
						if (a5_0 != null) {
							Object value = a5_0;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__SET_UP), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_7_0_0_5, a5_0, true);
						copyLocalizationInfos(a5_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[143]);
			}
			
			a6 = '}' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_7_0_0_7, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a6, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[144]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[145]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[146]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[147]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[148]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[149]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[150]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[151]);
	}
	
	(
		(
			a7 = 'TearDown' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_8_0_0_1, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a7, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[152]);
			}
			
			a8 = '{' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_8_0_0_3, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a8, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[153]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[154]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[155]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[156]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[157]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[158]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[159]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[160]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[161]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[162]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[163]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[164]);
			}
			
			(
				a9_0 = parse_org_qualitune_jouleunit_android_testrun_TestBehavior				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
						startIncompleteElement(element);
					}
					if (a9_0 != null) {
						if (a9_0 != null) {
							Object value = a9_0;
							element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__TEAR_DOWN), value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_8_0_0_5, a9_0, true);
						copyLocalizationInfos(a9_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[165]);
			}
			
			a10 = '}' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_8_0_0_7, null, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a10, element);
			}
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[166]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[167]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[168]);
			}
			
		)
		
	)?	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[169]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[170]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[171]);
	}
	
	(
		(
			(
				a11_0 = parse_org_qualitune_jouleunit_android_testrun_TestExecutable				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
						startIncompleteElement(element);
					}
					if (a11_0 != null) {
						if (a11_0 != null) {
							Object value = a11_0;
							addObjectToList(element, org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_SUITE__EXECUTABLES, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_9_0_0_1, a11_0, true);
						copyLocalizationInfos(a11_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[172]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[173]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[174]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[175]);
			}
			
		)
		
	)+	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[176]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[177]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestSuite(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[178]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[179]);
	}
	
	a12 = '}' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestSuite();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_2_0_0_10, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a12, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[180]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[181]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[182]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[183]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_TestCase returns [org.qualitune.jouleunit.android.testrun.TestCase element = null]
@init{
}
:
	a0 = 'TestCase' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestCase();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_3_0_0_1, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[184]);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestCase();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_3_0_0_3, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[185]);
	}
	
	a2 = '{' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestCase();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_3_0_0_5, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[186]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[187]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[188]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[189]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[190]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[191]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[192]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[193]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[194]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[195]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestCase(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[196]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[197]);
	}
	
	(
		a3_0 = parse_org_qualitune_jouleunit_android_testrun_TestBehavior		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestCase();
				startIncompleteElement(element);
			}
			if (a3_0 != null) {
				if (a3_0 != null) {
					Object value = a3_0;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_CASE__BEHAVIOR), value);
					completedElement(value, true);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_3_0_0_7, a3_0, true);
				copyLocalizationInfos(a3_0, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[198]);
	}
	
	a4 = '}' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestCase();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_3_0_0_9, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a4, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[199]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[200]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestRun(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[201]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[202]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_Block returns [org.qualitune.jouleunit.android.testrun.Block element = null]
@init{
}
:
	(
		(
			(
				a0_0 = parse_org_qualitune_jouleunit_android_testrun_Statement				{
					if (terminateParsing) {
						throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
					}
					if (element == null) {
						element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createBlock();
						startIncompleteElement(element);
					}
					if (a0_0 != null) {
						if (a0_0 != null) {
							Object value = a0_0;
							addObjectToList(element, org.qualitune.jouleunit.android.testrun.TestrunPackage.BLOCK__STATEMENTS, value);
							completedElement(value, true);
						}
						collectHiddenTokens(element);
						retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_4_0_0_0_0_0_0, a0_0, true);
						copyLocalizationInfos(a0_0, element);
					}
				}
			)
			{
				// expected elements (follow set)
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[203]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[204]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[205]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[206]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[207]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[208]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[209]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[210]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[211]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[212]);
				addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[213]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[214]);
				addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[215]);
			}
			
		)
		
	)*	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[216]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[217]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[218]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[219]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[220]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[221]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[222]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[223]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[224]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[225]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[226]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[227]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[228]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement returns [org.qualitune.jouleunit.android.testrun.ClickOnScreenStatement element = null]
@init{
}
:
	a0 = 'CLICK' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createClickOnScreenStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_5_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[229]);
	}
	
	a1 = 'ON' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createClickOnScreenStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_5_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[230]);
	}
	
	a2 = 'SCREEN' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createClickOnScreenStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_5_0_0_4, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a2, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[231]);
	}
	
	(
		a3 = NUMBER		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createClickOnScreenStatement();
				startIncompleteElement(element);
			}
			if (a3 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__X), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStopIndex());
				}
				java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__X), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_5_0_0_6, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a3, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[232]);
	}
	
	(
		a4 = NUMBER		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createClickOnScreenStatement();
				startIncompleteElement(element);
			}
			if (a4 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__Y), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStopIndex());
				}
				java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CLICK_ON_SCREEN_STATEMENT__Y), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_5_0_0_8, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a4, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[233]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[234]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[235]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[236]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[237]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[238]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[239]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[240]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[241]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[242]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[243]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[244]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[245]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_CursorStatement returns [org.qualitune.jouleunit.android.testrun.CursorStatement element = null]
@init{
}
:
	a0 = 'CURSOR' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createCursorStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_6_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[246]);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createCursorStatement();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CURSOR_STATEMENT__DIRECTION), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				org.qualitune.jouleunit.android.testrun.CursorDirection resolved = (org.qualitune.jouleunit.android.testrun.CursorDirection) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.CURSOR_STATEMENT__DIRECTION), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_6_0_0_1, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[247]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[248]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[249]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[250]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[251]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[252]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[253]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[254]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[255]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[256]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[257]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[258]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[259]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_DisplayStatement returns [org.qualitune.jouleunit.android.testrun.DisplayStatement element = null]
@init{
}
:
	a0 = 'DISPLAY' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createDisplayStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_7_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[260]);
	}
	
	(
		(
			a1 = 'ON' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createDisplayStatement();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_7_0_0_2, true, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
				// set value of boolean attribute
				Object value = true;
				element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON), value);
				completedElement(value, false);
			}
			|			a2 = 'OFF' {
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createDisplayStatement();
					startIncompleteElement(element);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_7_0_0_2, false, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a2, element);
				// set value of boolean attribute
				Object value = false;
				element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.DISPLAY_STATEMENT__SWITCH_ON), value);
				completedElement(value, false);
			}
		)
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[261]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[262]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[263]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[264]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[265]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[266]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[267]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[268]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[269]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[270]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[271]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[272]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[273]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_EnterStatement returns [org.qualitune.jouleunit.android.testrun.EnterStatement element = null]
@init{
}
:
	a0 = 'ENTER' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createEnterStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_8_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[274]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[275]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[276]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[277]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[278]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[279]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[280]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[281]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[282]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[283]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[284]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[285]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[286]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_HomeButtonStatement returns [org.qualitune.jouleunit.android.testrun.HomeButtonStatement element = null]
@init{
}
:
	a0 = 'HOME' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createHomeButtonStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_9_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[287]);
	}
	
	a1 = 'BUTTON' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createHomeButtonStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_9_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[288]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[289]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[290]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[291]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[292]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[293]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[294]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[295]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[296]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[297]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[298]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[299]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[300]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement returns [org.qualitune.jouleunit.android.testrun.OpenSettingsStatement element = null]
@init{
}
:
	a0 = 'OPEN' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createOpenSettingsStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_10_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[301]);
	}
	
	a1 = 'SETTINGS' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createOpenSettingsStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_10_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[302]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[303]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[304]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[305]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[306]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[307]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[308]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[309]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[310]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[311]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[312]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[313]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[314]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement returns [org.qualitune.jouleunit.android.testrun.SendPortMessageStatement element = null]
@init{
}
:
	a0 = 'SEND' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createSendPortMessageStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_11_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[315]);
	}
	
	a1 = 'MESSAGE' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createSendPortMessageStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_11_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[316]);
	}
	
	(
		a2 = QUOTED_34_34		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createSendPortMessageStatement();
				startIncompleteElement(element);
			}
			if (a2 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__IP), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__IP), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_11_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[317]);
	}
	
	(
		a3 = NUMBER		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createSendPortMessageStatement();
				startIncompleteElement(element);
			}
			if (a3 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__PORT), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStopIndex());
				}
				java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__PORT), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_11_0_0_6, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a3, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[318]);
	}
	
	(
		a4 = QUOTED_34_34		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createSendPortMessageStatement();
				startIncompleteElement(element);
			}
			if (a4 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a4.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__MESSAGE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a4).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a4).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.SEND_PORT_MESSAGE_STATEMENT__MESSAGE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_11_0_0_8, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a4, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[319]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[320]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[321]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[322]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[323]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[324]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[325]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[326]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[327]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[328]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[329]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[330]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[331]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_StartActivityStatement returns [org.qualitune.jouleunit.android.testrun.StartActivityStatement element = null]
@init{
}
:
	a0 = 'START' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createStartActivityStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_12_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[332]);
	}
	
	a1 = 'ACTIVITY' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createStartActivityStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_12_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[333]);
	}
	
	(
		a2 = QUOTED_34_34		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createStartActivityStatement();
				startIncompleteElement(element);
			}
			if (a2 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__PACKAGE), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__PACKAGE), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_12_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[334]);
	}
	
	(
		a3 = QUOTED_34_34		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createStartActivityStatement();
				startIncompleteElement(element);
			}
			if (a3 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("QUOTED_34_34");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a3.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__CLASS), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a3).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a3).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.START_ACTIVITY_STATEMENT__CLASS), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_12_0_0_6, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a3, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[335]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[336]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[337]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[338]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[339]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[340]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[341]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[342]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[343]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[344]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[345]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[346]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[347]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_WaitStatement returns [org.qualitune.jouleunit.android.testrun.WaitStatement element = null]
@init{
}
:
	a0 = 'WAIT' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createWaitStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_13_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[348]);
	}
	
	a1 = 'FOR' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createWaitStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_13_0_0_2, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a1, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[349]);
	}
	
	(
		a2 = NUMBER		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createWaitStatement();
				startIncompleteElement(element);
			}
			if (a2 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("NUMBER");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a2.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.WAIT_STATEMENT__SECONDS), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a2).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a2).getStopIndex());
				}
				java.lang.Integer resolved = (java.lang.Integer) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.WAIT_STATEMENT__SECONDS), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_13_0_0_4, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a2, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[350]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[351]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[352]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[353]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[354]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[355]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[356]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[357]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[358]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[359]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[360]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[361]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[362]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_UnlockStatement returns [org.qualitune.jouleunit.android.testrun.UnlockStatement element = null]
@init{
}
:
	a0 = 'UNLOCK' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createUnlockStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_14_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[363]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[364]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[365]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[366]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[367]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[368]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[369]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[370]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[371]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[372]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[373]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[374]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[375]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_TestStatement returns [org.qualitune.jouleunit.android.testrun.TestStatement element = null]
@init{
}
:
	a0 = 'Test' {
		if (element == null) {
			element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
			startIncompleteElement(element);
		}
		collectHiddenTokens(element);
		retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_0, null, true);
		copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a0, element);
	}
	{
		// expected elements (follow set)
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[376]);
	}
	
	(
		a1 = TEXT		
		{
			if (terminateParsing) {
				throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
			}
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
				startIncompleteElement(element);
			}
			if (a1 != null) {
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolver tokenResolver = tokenResolverFactory.createTokenResolver("TEXT");
				tokenResolver.setOptions(getOptions());
				org.qualitune.jouleunit.android.testrun.resource.testrun.ITestrunTokenResolveResult result = getFreshTokenResolveResult();
				tokenResolver.resolve(a1.getText(), element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__NAME), result);
				Object resolvedObject = result.getResolvedToken();
				if (resolvedObject == null) {
					addErrorToResource(result.getErrorMessage(), ((org.antlr.runtime3_4_0.CommonToken) a1).getLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getCharPositionInLine(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStartIndex(), ((org.antlr.runtime3_4_0.CommonToken) a1).getStopIndex());
				}
				java.lang.String resolved = (java.lang.String) resolvedObject;
				if (resolved != null) {
					Object value = resolved;
					element.eSet(element.eClass().getEStructuralFeature(org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__NAME), value);
					completedElement(value, false);
				}
				collectHiddenTokens(element);
				retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_2, resolved, true);
				copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken) a1, element);
			}
		}
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[377]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[378]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[379]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[380]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[381]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[382]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[383]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[384]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[385]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[386]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[387]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[388]);
	}
	
	(
		(
			a2_0 = parse_org_qualitune_jouleunit_android_testrun_Statement			{
				if (terminateParsing) {
					throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
				}
				if (element == null) {
					element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
					startIncompleteElement(element);
				}
				if (a2_0 != null) {
					if (a2_0 != null) {
						Object value = a2_0;
						addObjectToList(element, org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__STATEMENTS, value);
						completedElement(value, true);
					}
					collectHiddenTokens(element);
					retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_3_0_0_1, a2_0, true);
					copyLocalizationInfos(a2_0, element);
				}
			}
		)
		{
			// expected elements (follow set)
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[389]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[390]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[391]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[392]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[393]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[394]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[395]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[396]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[397]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[398]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[399]);
			addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[400]);
			addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[401]);
		}
		
		
		|		a3 = '{' {
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
				startIncompleteElement(element);
			}
			collectHiddenTokens(element);
			retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_3_0_1_1, null, true);
			copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a3, element);
		}
		{
			// expected elements (follow set)
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[402]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[403]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[404]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[405]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[406]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[407]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[408]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[409]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[410]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[411]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[412]);
		}
		
		(
			(
				(
					a4_0 = parse_org_qualitune_jouleunit_android_testrun_Statement					{
						if (terminateParsing) {
							throw new org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunTerminateParsingException();
						}
						if (element == null) {
							element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
							startIncompleteElement(element);
						}
						if (a4_0 != null) {
							if (a4_0 != null) {
								Object value = a4_0;
								addObjectToList(element, org.qualitune.jouleunit.android.testrun.TestrunPackage.TEST_STATEMENT__STATEMENTS, value);
								completedElement(value, true);
							}
							collectHiddenTokens(element);
							retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_3_0_1_2_0_0_1, a4_0, true);
							copyLocalizationInfos(a4_0, element);
						}
					}
				)
				{
					// expected elements (follow set)
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[413]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[414]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[415]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[416]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[417]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[418]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[419]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[420]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[421]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[422]);
					addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[423]);
					addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[424]);
				}
				
			)
			
		)+		{
			// expected elements (follow set)
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[425]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[426]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[427]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[428]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[429]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[430]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[431]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[432]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[433]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[434]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getTestStatement(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[435]);
			addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[436]);
		}
		
		a5 = '}' {
			if (element == null) {
				element = org.qualitune.jouleunit.android.testrun.TestrunFactory.eINSTANCE.createTestStatement();
				startIncompleteElement(element);
			}
			collectHiddenTokens(element);
			retrieveLayoutInformation(element, org.qualitune.jouleunit.android.testrun.resource.testrun.grammar.TestrunGrammarInformationProvider.TESTRUN_15_0_0_3_0_1_4, null, true);
			copyLocalizationInfos((org.antlr.runtime3_4_0.CommonToken)a5, element);
		}
		{
			// expected elements (follow set)
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[437]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[438]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[439]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[440]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[441]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[442]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[443]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[444]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[445]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[446]);
			addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[447]);
			addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[448]);
			addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[449]);
		}
		
	)
	{
		// expected elements (follow set)
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[450]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[451]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[452]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[453]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[454]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[455]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[456]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[457]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[458]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[459]);
		addExpectedElement(org.qualitune.jouleunit.android.testrun.TestrunPackage.eINSTANCE.getBlock(), org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[460]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[461]);
		addExpectedElement(null, org.qualitune.jouleunit.android.testrun.resource.testrun.mopp.TestrunExpectationConstants.EXPECTATIONS[462]);
	}
	
;

parse_org_qualitune_jouleunit_android_testrun_TestExecutable returns [org.qualitune.jouleunit.android.testrun.TestExecutable element = null]
:
	c0 = parse_org_qualitune_jouleunit_android_testrun_JunitTestCase{ element = c0; /* this is a subclass or primitive expression choice */ }
	|	c1 = parse_org_qualitune_jouleunit_android_testrun_TestSuite{ element = c1; /* this is a subclass or primitive expression choice */ }
	|	c2 = parse_org_qualitune_jouleunit_android_testrun_TestCase{ element = c2; /* this is a subclass or primitive expression choice */ }
	
;

parse_org_qualitune_jouleunit_android_testrun_TestBehavior returns [org.qualitune.jouleunit.android.testrun.TestBehavior element = null]
:
	c0 = parse_org_qualitune_jouleunit_android_testrun_Block{ element = c0; /* this is a subclass or primitive expression choice */ }
	
;

parse_org_qualitune_jouleunit_android_testrun_Statement returns [org.qualitune.jouleunit.android.testrun.Statement element = null]
:
	c0 = parse_org_qualitune_jouleunit_android_testrun_ClickOnScreenStatement{ element = c0; /* this is a subclass or primitive expression choice */ }
	|	c1 = parse_org_qualitune_jouleunit_android_testrun_CursorStatement{ element = c1; /* this is a subclass or primitive expression choice */ }
	|	c2 = parse_org_qualitune_jouleunit_android_testrun_DisplayStatement{ element = c2; /* this is a subclass or primitive expression choice */ }
	|	c3 = parse_org_qualitune_jouleunit_android_testrun_EnterStatement{ element = c3; /* this is a subclass or primitive expression choice */ }
	|	c4 = parse_org_qualitune_jouleunit_android_testrun_HomeButtonStatement{ element = c4; /* this is a subclass or primitive expression choice */ }
	|	c5 = parse_org_qualitune_jouleunit_android_testrun_OpenSettingsStatement{ element = c5; /* this is a subclass or primitive expression choice */ }
	|	c6 = parse_org_qualitune_jouleunit_android_testrun_SendPortMessageStatement{ element = c6; /* this is a subclass or primitive expression choice */ }
	|	c7 = parse_org_qualitune_jouleunit_android_testrun_StartActivityStatement{ element = c7; /* this is a subclass or primitive expression choice */ }
	|	c8 = parse_org_qualitune_jouleunit_android_testrun_WaitStatement{ element = c8; /* this is a subclass or primitive expression choice */ }
	|	c9 = parse_org_qualitune_jouleunit_android_testrun_UnlockStatement{ element = c9; /* this is a subclass or primitive expression choice */ }
	|	c10 = parse_org_qualitune_jouleunit_android_testrun_TestStatement{ element = c10; /* this is a subclass or primitive expression choice */ }
	
;

SL_COMMENT:
	( '//'(~('\n'|'\r'|'\uffff'))* )
	{ _channel = 99; }
;
ML_COMMENT:
	( '/*'.*'*/')
	{ _channel = 99; }
;
NUMBER:
	(('0'..'9')+)
;
PACKAGE:
	(('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+('.'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)+)
;
TESTID:
	(('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+('.'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)+'#'('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)
;
TEXT:
	(('A'..'Z' | 'a'..'z' | '0'..'9' | '_' | '-' )+)
;
WHITESPACE:
	((' ' | '\t' | '\f'))
	{ _channel = 99; }
;
LINEBREAK:
	(('\r\n' | '\r' | '\n'))
	{ _channel = 99; }
;
QUOTED_34_34:
	(('"')(~('"'))*('"'))
;

