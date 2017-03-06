// Generated from socs/network/util/parser/RouterCommand.g4 by ANTLR 4.6
package socs.network.util.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RouterCommandParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IP_ADDRESS=1, NUMBER=2, QUOTED_STRING=3, CMD_DETECT=4, CMD_DISCONNECT=5, 
		CMD_QUIT=6, CMD_ATTACH=7, CMD_START=8, CMD_CONNECT=9, CMD_NEIGHBORS=10, 
		CMD_DEBUG=11, CMD_DEBUG_SELECT=12, NEWLINE=13, WHITESPACE=14;
	public static final int
		RULE_command = 0, RULE_cmdDetect = 1, RULE_cmdAttach = 2, RULE_cmdAttachFile = 3, 
		RULE_cmdStart = 4, RULE_cmdNeighbors = 5, RULE_cmdQuit = 6, RULE_cmdDebug = 7;
	public static final String[] ruleNames = {
		"command", "cmdDetect", "cmdAttach", "cmdAttachFile", "cmdStart", "cmdNeighbors", 
		"cmdQuit", "cmdDebug"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'detect'", "'disconnect'", "'quit'", "'attach'", 
		"'start'", "'connect'", "'neighbors'", "'debug'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IP_ADDRESS", "NUMBER", "QUOTED_STRING", "CMD_DETECT", "CMD_DISCONNECT", 
		"CMD_QUIT", "CMD_ATTACH", "CMD_START", "CMD_CONNECT", "CMD_NEIGHBORS", 
		"CMD_DEBUG", "CMD_DEBUG_SELECT", "NEWLINE", "WHITESPACE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RouterCommand.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RouterCommandParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CommandContext extends ParserRuleContext {
		public CmdDetectContext cmdDetect() {
			return getRuleContext(CmdDetectContext.class,0);
		}
		public CmdAttachContext cmdAttach() {
			return getRuleContext(CmdAttachContext.class,0);
		}
		public CmdAttachFileContext cmdAttachFile() {
			return getRuleContext(CmdAttachFileContext.class,0);
		}
		public CmdStartContext cmdStart() {
			return getRuleContext(CmdStartContext.class,0);
		}
		public CmdNeighborsContext cmdNeighbors() {
			return getRuleContext(CmdNeighborsContext.class,0);
		}
		public CmdQuitContext cmdQuit() {
			return getRuleContext(CmdQuitContext.class,0);
		}
		public CmdDebugContext cmdDebug() {
			return getRuleContext(CmdDebugContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_command);
		try {
			setState(23);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				cmdDetect();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(17);
				cmdAttach();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(18);
				cmdAttachFile();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(19);
				cmdStart();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(20);
				cmdNeighbors();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(21);
				cmdQuit();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(22);
				cmdDebug();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdDetectContext extends ParserRuleContext {
		public Token simulateIP;
		public TerminalNode CMD_DETECT() { return getToken(RouterCommandParser.CMD_DETECT, 0); }
		public TerminalNode IP_ADDRESS() { return getToken(RouterCommandParser.IP_ADDRESS, 0); }
		public CmdDetectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdDetect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdDetect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdDetect(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdDetect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdDetectContext cmdDetect() throws RecognitionException {
		CmdDetectContext _localctx = new CmdDetectContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_cmdDetect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(CMD_DETECT);
			setState(26);
			((CmdDetectContext)_localctx).simulateIP = match(IP_ADDRESS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdAttachContext extends ParserRuleContext {
		public Token processIP;
		public Token processPort;
		public Token simulateIP;
		public Token weight;
		public TerminalNode CMD_ATTACH() { return getToken(RouterCommandParser.CMD_ATTACH, 0); }
		public List<TerminalNode> IP_ADDRESS() { return getTokens(RouterCommandParser.IP_ADDRESS); }
		public TerminalNode IP_ADDRESS(int i) {
			return getToken(RouterCommandParser.IP_ADDRESS, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(RouterCommandParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(RouterCommandParser.NUMBER, i);
		}
		public CmdAttachContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdAttach; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdAttach(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdAttach(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdAttach(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdAttachContext cmdAttach() throws RecognitionException {
		CmdAttachContext _localctx = new CmdAttachContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_cmdAttach);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(CMD_ATTACH);
			setState(29);
			((CmdAttachContext)_localctx).processIP = match(IP_ADDRESS);
			setState(30);
			((CmdAttachContext)_localctx).processPort = match(NUMBER);
			setState(31);
			((CmdAttachContext)_localctx).simulateIP = match(IP_ADDRESS);
			setState(32);
			((CmdAttachContext)_localctx).weight = match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdAttachFileContext extends ParserRuleContext {
		public Token path;
		public Token weight;
		public TerminalNode CMD_ATTACH() { return getToken(RouterCommandParser.CMD_ATTACH, 0); }
		public TerminalNode QUOTED_STRING() { return getToken(RouterCommandParser.QUOTED_STRING, 0); }
		public TerminalNode NUMBER() { return getToken(RouterCommandParser.NUMBER, 0); }
		public CmdAttachFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdAttachFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdAttachFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdAttachFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdAttachFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdAttachFileContext cmdAttachFile() throws RecognitionException {
		CmdAttachFileContext _localctx = new CmdAttachFileContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_cmdAttachFile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(CMD_ATTACH);
			setState(35);
			((CmdAttachFileContext)_localctx).path = match(QUOTED_STRING);
			setState(36);
			((CmdAttachFileContext)_localctx).weight = match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdStartContext extends ParserRuleContext {
		public TerminalNode CMD_START() { return getToken(RouterCommandParser.CMD_START, 0); }
		public CmdStartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdStart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdStartContext cmdStart() throws RecognitionException {
		CmdStartContext _localctx = new CmdStartContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_cmdStart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(CMD_START);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdNeighborsContext extends ParserRuleContext {
		public TerminalNode CMD_NEIGHBORS() { return getToken(RouterCommandParser.CMD_NEIGHBORS, 0); }
		public CmdNeighborsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdNeighbors; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdNeighbors(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdNeighbors(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdNeighbors(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdNeighborsContext cmdNeighbors() throws RecognitionException {
		CmdNeighborsContext _localctx = new CmdNeighborsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cmdNeighbors);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(CMD_NEIGHBORS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdQuitContext extends ParserRuleContext {
		public TerminalNode CMD_QUIT() { return getToken(RouterCommandParser.CMD_QUIT, 0); }
		public CmdQuitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdQuit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdQuit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdQuit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdQuit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdQuitContext cmdQuit() throws RecognitionException {
		CmdQuitContext _localctx = new CmdQuitContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_cmdQuit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(CMD_QUIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdDebugContext extends ParserRuleContext {
		public Token select;
		public TerminalNode CMD_DEBUG() { return getToken(RouterCommandParser.CMD_DEBUG, 0); }
		public TerminalNode CMD_DEBUG_SELECT() { return getToken(RouterCommandParser.CMD_DEBUG_SELECT, 0); }
		public CmdDebugContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdDebug; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).enterCmdDebug(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RouterCommandListener ) ((RouterCommandListener)listener).exitCmdDebug(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RouterCommandVisitor ) return ((RouterCommandVisitor<? extends T>)visitor).visitCmdDebug(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdDebugContext cmdDebug() throws RecognitionException {
		CmdDebugContext _localctx = new CmdDebugContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cmdDebug);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(CMD_DEBUG);
			setState(45);
			((CmdDebugContext)_localctx).select = match(CMD_DEBUG_SELECT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\20\62\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\5\2\32\n\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2"+
		"\2/\2\31\3\2\2\2\4\33\3\2\2\2\6\36\3\2\2\2\b$\3\2\2\2\n(\3\2\2\2\f*\3"+
		"\2\2\2\16,\3\2\2\2\20.\3\2\2\2\22\32\5\4\3\2\23\32\5\6\4\2\24\32\5\b\5"+
		"\2\25\32\5\n\6\2\26\32\5\f\7\2\27\32\5\16\b\2\30\32\5\20\t\2\31\22\3\2"+
		"\2\2\31\23\3\2\2\2\31\24\3\2\2\2\31\25\3\2\2\2\31\26\3\2\2\2\31\27\3\2"+
		"\2\2\31\30\3\2\2\2\32\3\3\2\2\2\33\34\7\6\2\2\34\35\7\3\2\2\35\5\3\2\2"+
		"\2\36\37\7\t\2\2\37 \7\3\2\2 !\7\4\2\2!\"\7\3\2\2\"#\7\4\2\2#\7\3\2\2"+
		"\2$%\7\t\2\2%&\7\5\2\2&\'\7\4\2\2\'\t\3\2\2\2()\7\n\2\2)\13\3\2\2\2*+"+
		"\7\f\2\2+\r\3\2\2\2,-\7\b\2\2-\17\3\2\2\2./\7\r\2\2/\60\7\16\2\2\60\21"+
		"\3\2\2\2\3\31";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}