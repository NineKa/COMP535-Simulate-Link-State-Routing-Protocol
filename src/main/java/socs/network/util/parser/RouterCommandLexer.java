// Generated from socs/network/util/parser/RouterCommand.g4 by ANTLR 4.6
package socs.network.util.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RouterCommandLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IP_ADDRESS=1, NUMBER=2, QUOTED_STRING=3, CMD_DETECT=4, CMD_DISCONNECT=5, 
		CMD_QUIT=6, CMD_ATTACH=7, CMD_START=8, CMD_CONNECT=9, CMD_NEIGHBORS=10, 
		CMD_EXIT=11, NEWLINE=12, WHITESPACE=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IP_ADDRESS", "NUMBER", "ESCAPED_QUOTE", "QUOTED_STRING", "CMD_DETECT", 
		"CMD_DISCONNECT", "CMD_QUIT", "CMD_ATTACH", "CMD_START", "CMD_CONNECT", 
		"CMD_NEIGHBORS", "CMD_EXIT", "NEWLINE", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'detect'", "'disconnect'", "'quit'", "'attach'", 
		"'start'", "'connect'", "'neighbors'", "'exit'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "IP_ADDRESS", "NUMBER", "QUOTED_STRING", "CMD_DETECT", "CMD_DISCONNECT", 
		"CMD_QUIT", "CMD_ATTACH", "CMD_START", "CMD_CONNECT", "CMD_NEIGHBORS", 
		"CMD_EXIT", "NEWLINE", "WHITESPACE"
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


	public RouterCommandLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RouterCommand.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17\u009b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\5\2\"\n\2\3\2\5\2"+
		"%\n\2\3\2\3\2\3\2\5\2*\n\2\3\2\5\2-\n\2\3\2\3\2\3\2\5\2\62\n\2\3\2\5\2"+
		"\65\n\2\3\2\3\2\3\2\5\2:\n\2\3\2\5\2=\n\2\3\3\3\3\3\3\7\3B\n\3\f\3\16"+
		"\3E\13\3\5\3G\n\3\3\4\3\4\3\4\3\5\3\5\3\5\7\5O\n\5\f\5\16\5R\13\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\6\17\u0096"+
		"\n\17\r\17\16\17\u0097\3\17\3\17\3P\2\20\3\3\5\4\7\2\t\5\13\6\r\7\17\b"+
		"\21\t\23\n\25\13\27\f\31\r\33\16\35\17\3\2\5\3\2\62;\4\2\f\f\17\17\4\2"+
		"\13\13\"\"\u00a6\2\3\3\2\2\2\2\5\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r"+
		"\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2"+
		"\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5F\3\2\2\2\7"+
		"H\3\2\2\2\tK\3\2\2\2\13U\3\2\2\2\r\\\3\2\2\2\17g\3\2\2\2\21l\3\2\2\2\23"+
		"s\3\2\2\2\25y\3\2\2\2\27\u0081\3\2\2\2\31\u008b\3\2\2\2\33\u0090\3\2\2"+
		"\2\35\u0095\3\2\2\2\37!\t\2\2\2 \"\t\2\2\2! \3\2\2\2!\"\3\2\2\2\"$\3\2"+
		"\2\2#%\t\2\2\2$#\3\2\2\2$%\3\2\2\2%&\3\2\2\2&\'\7\60\2\2\')\t\2\2\2(*"+
		"\t\2\2\2)(\3\2\2\2)*\3\2\2\2*,\3\2\2\2+-\t\2\2\2,+\3\2\2\2,-\3\2\2\2-"+
		".\3\2\2\2./\7\60\2\2/\61\t\2\2\2\60\62\t\2\2\2\61\60\3\2\2\2\61\62\3\2"+
		"\2\2\62\64\3\2\2\2\63\65\t\2\2\2\64\63\3\2\2\2\64\65\3\2\2\2\65\66\3\2"+
		"\2\2\66\67\7\60\2\2\679\t\2\2\28:\t\2\2\298\3\2\2\29:\3\2\2\2:<\3\2\2"+
		"\2;=\t\2\2\2<;\3\2\2\2<=\3\2\2\2=\4\3\2\2\2>G\7\62\2\2?C\t\2\2\2@B\t\2"+
		"\2\2A@\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2DG\3\2\2\2EC\3\2\2\2F>\3\2"+
		"\2\2F?\3\2\2\2G\6\3\2\2\2HI\7^\2\2IJ\7$\2\2J\b\3\2\2\2KP\7$\2\2LO\5\7"+
		"\4\2MO\n\3\2\2NL\3\2\2\2NM\3\2\2\2OR\3\2\2\2PQ\3\2\2\2PN\3\2\2\2QS\3\2"+
		"\2\2RP\3\2\2\2ST\7$\2\2T\n\3\2\2\2UV\7f\2\2VW\7g\2\2WX\7v\2\2XY\7g\2\2"+
		"YZ\7e\2\2Z[\7v\2\2[\f\3\2\2\2\\]\7f\2\2]^\7k\2\2^_\7u\2\2_`\7e\2\2`a\7"+
		"q\2\2ab\7p\2\2bc\7p\2\2cd\7g\2\2de\7e\2\2ef\7v\2\2f\16\3\2\2\2gh\7s\2"+
		"\2hi\7w\2\2ij\7k\2\2jk\7v\2\2k\20\3\2\2\2lm\7c\2\2mn\7v\2\2no\7v\2\2o"+
		"p\7c\2\2pq\7e\2\2qr\7j\2\2r\22\3\2\2\2st\7u\2\2tu\7v\2\2uv\7c\2\2vw\7"+
		"t\2\2wx\7v\2\2x\24\3\2\2\2yz\7e\2\2z{\7q\2\2{|\7p\2\2|}\7p\2\2}~\7g\2"+
		"\2~\177\7e\2\2\177\u0080\7v\2\2\u0080\26\3\2\2\2\u0081\u0082\7p\2\2\u0082"+
		"\u0083\7g\2\2\u0083\u0084\7k\2\2\u0084\u0085\7i\2\2\u0085\u0086\7j\2\2"+
		"\u0086\u0087\7d\2\2\u0087\u0088\7q\2\2\u0088\u0089\7t\2\2\u0089\u008a"+
		"\7u\2\2\u008a\30\3\2\2\2\u008b\u008c\7g\2\2\u008c\u008d\7z\2\2\u008d\u008e"+
		"\7k\2\2\u008e\u008f\7v\2\2\u008f\32\3\2\2\2\u0090\u0091\t\3\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0093\b\16\2\2\u0093\34\3\2\2\2\u0094\u0096\t\4\2"+
		"\2\u0095\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098"+
		"\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\b\17\2\2\u009a\36\3\2\2\2\20"+
		"\2!$),\61\649<CFNP\u0097\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}