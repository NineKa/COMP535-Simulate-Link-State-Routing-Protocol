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
		CMD_DEBUG=11, CMD_DEBUG_SELECT=12, NEWLINE=13, WHITESPACE=14;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"IP_ADDRESS", "NUMBER", "ESCAPED_QUOTE", "QUOTED_STRING", "CMD_DETECT", 
		"CMD_DISCONNECT", "CMD_QUIT", "CMD_ATTACH", "CMD_START", "CMD_CONNECT", 
		"CMD_NEIGHBORS", "CMD_DEBUG", "CMD_DEBUG_SELECT", "NEWLINE", "WHITESPACE"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\20\u00a7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\5\2$\n"+
		"\2\3\2\5\2\'\n\2\3\2\3\2\3\2\5\2,\n\2\3\2\5\2/\n\2\3\2\3\2\3\2\5\2\64"+
		"\n\2\3\2\5\2\67\n\2\3\2\3\2\3\2\5\2<\n\2\3\2\5\2?\n\2\3\3\3\3\3\3\7\3"+
		"D\n\3\f\3\16\3G\13\3\5\3I\n\3\3\4\3\4\3\4\3\5\3\5\3\5\7\5Q\n\5\f\5\16"+
		"\5T\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\5\16\u009b\n\16\3\17\3\17\3\17\3\17\3\20\6\20\u00a2"+
		"\n\20\r\20\16\20\u00a3\3\20\3\20\3R\2\21\3\3\5\4\7\2\t\5\13\6\r\7\17\b"+
		"\21\t\23\n\25\13\27\f\31\r\33\16\35\17\37\20\3\2\5\3\2\62;\4\2\f\f\17"+
		"\17\4\2\13\13\"\"\u00b3\2\3\3\2\2\2\2\5\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\3!\3\2\2"+
		"\2\5H\3\2\2\2\7J\3\2\2\2\tM\3\2\2\2\13W\3\2\2\2\r^\3\2\2\2\17i\3\2\2\2"+
		"\21n\3\2\2\2\23u\3\2\2\2\25{\3\2\2\2\27\u0083\3\2\2\2\31\u008d\3\2\2\2"+
		"\33\u009a\3\2\2\2\35\u009c\3\2\2\2\37\u00a1\3\2\2\2!#\t\2\2\2\"$\t\2\2"+
		"\2#\"\3\2\2\2#$\3\2\2\2$&\3\2\2\2%\'\t\2\2\2&%\3\2\2\2&\'\3\2\2\2\'(\3"+
		"\2\2\2()\7\60\2\2)+\t\2\2\2*,\t\2\2\2+*\3\2\2\2+,\3\2\2\2,.\3\2\2\2-/"+
		"\t\2\2\2.-\3\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61\7\60\2\2\61\63\t\2\2\2"+
		"\62\64\t\2\2\2\63\62\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\67\t\2\2\2"+
		"\66\65\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\7\60\2\29;\t\2\2\2:<\t\2\2"+
		"\2;:\3\2\2\2;<\3\2\2\2<>\3\2\2\2=?\t\2\2\2>=\3\2\2\2>?\3\2\2\2?\4\3\2"+
		"\2\2@I\7\62\2\2AE\t\2\2\2BD\t\2\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3"+
		"\2\2\2FI\3\2\2\2GE\3\2\2\2H@\3\2\2\2HA\3\2\2\2I\6\3\2\2\2JK\7^\2\2KL\7"+
		"$\2\2L\b\3\2\2\2MR\7$\2\2NQ\5\7\4\2OQ\n\3\2\2PN\3\2\2\2PO\3\2\2\2QT\3"+
		"\2\2\2RS\3\2\2\2RP\3\2\2\2SU\3\2\2\2TR\3\2\2\2UV\7$\2\2V\n\3\2\2\2WX\7"+
		"f\2\2XY\7g\2\2YZ\7v\2\2Z[\7g\2\2[\\\7e\2\2\\]\7v\2\2]\f\3\2\2\2^_\7f\2"+
		"\2_`\7k\2\2`a\7u\2\2ab\7e\2\2bc\7q\2\2cd\7p\2\2de\7p\2\2ef\7g\2\2fg\7"+
		"e\2\2gh\7v\2\2h\16\3\2\2\2ij\7s\2\2jk\7w\2\2kl\7k\2\2lm\7v\2\2m\20\3\2"+
		"\2\2no\7c\2\2op\7v\2\2pq\7v\2\2qr\7c\2\2rs\7e\2\2st\7j\2\2t\22\3\2\2\2"+
		"uv\7u\2\2vw\7v\2\2wx\7c\2\2xy\7t\2\2yz\7v\2\2z\24\3\2\2\2{|\7e\2\2|}\7"+
		"q\2\2}~\7p\2\2~\177\7p\2\2\177\u0080\7g\2\2\u0080\u0081\7e\2\2\u0081\u0082"+
		"\7v\2\2\u0082\26\3\2\2\2\u0083\u0084\7p\2\2\u0084\u0085\7g\2\2\u0085\u0086"+
		"\7k\2\2\u0086\u0087\7i\2\2\u0087\u0088\7j\2\2\u0088\u0089\7d\2\2\u0089"+
		"\u008a\7q\2\2\u008a\u008b\7t\2\2\u008b\u008c\7u\2\2\u008c\30\3\2\2\2\u008d"+
		"\u008e\7f\2\2\u008e\u008f\7g\2\2\u008f\u0090\7d\2\2\u0090\u0091\7w\2\2"+
		"\u0091\u0092\7i\2\2\u0092\32\3\2\2\2\u0093\u0094\7k\2\2\u0094\u0095\7"+
		"p\2\2\u0095\u0096\7h\2\2\u0096\u009b\7q\2\2\u0097\u0098\7n\2\2\u0098\u0099"+
		"\7u\2\2\u0099\u009b\7f\2\2\u009a\u0093\3\2\2\2\u009a\u0097\3\2\2\2\u009b"+
		"\34\3\2\2\2\u009c\u009d\t\3\2\2\u009d\u009e\3\2\2\2\u009e\u009f\b\17\2"+
		"\2\u009f\36\3\2\2\2\u00a0\u00a2\t\4\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3"+
		"\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5"+
		"\u00a6\b\20\2\2\u00a6 \3\2\2\2\21\2#&+.\63\66;>EHPR\u009a\u00a3\3\b\2"+
		"\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}