// Generated from /home/samantha/IdeaProjects/HaveFun/src/HaveFun.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HaveFunLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FUN=1, RET=2, DOTG=3, GLOBAL=4, NAT=5, BOOL=6, PLUS=7, MINUS=8, MUL=9, 
		DIV=10, MOD=11, POW=12, AND=13, OR=14, EQQ=15, NEQ=16, LEQ=17, GEQ=18, 
		LT=19, GT=20, NOT=21, IF=22, THEN=23, ELSE=24, WHILE=25, SKIPP=26, ASSIGN=27, 
		OUT=28, ND=29, LPAR=30, RPAR=31, LBRACE=32, RBRACE=33, SEMICOLON=34, COLON=35, 
		ID=36, WS=37;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"FUN", "RET", "DOTG", "GLOBAL", "NAT", "BOOL", "PLUS", "MINUS", "MUL", 
			"DIV", "MOD", "POW", "AND", "OR", "EQQ", "NEQ", "LEQ", "GEQ", "LT", "GT", 
			"NOT", "IF", "THEN", "ELSE", "WHILE", "SKIPP", "ASSIGN", "OUT", "ND", 
			"LPAR", "RPAR", "LBRACE", "RBRACE", "SEMICOLON", "COLON", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'fun'", "'return'", "'.g'", "'global'", null, null, "'+'", "'-'", 
			"'*'", "'/'", "'mod'", "'^'", "'&'", "'|'", "'=='", "'!='", "'<='", "'>='", 
			"'<'", "'>'", "'!'", "'if'", "'then'", "'else'", "'while'", "'skip'", 
			"'='", "'out'", "'nd'", "'('", "')'", "'{'", "'}'", "';'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "FUN", "RET", "DOTG", "GLOBAL", "NAT", "BOOL", "PLUS", "MINUS", 
			"MUL", "DIV", "MOD", "POW", "AND", "OR", "EQQ", "NEQ", "LEQ", "GEQ", 
			"LT", "GT", "NOT", "IF", "THEN", "ELSE", "WHILE", "SKIPP", "ASSIGN", 
			"OUT", "ND", "LPAR", "RPAR", "LBRACE", "RBRACE", "SEMICOLON", "COLON", 
			"ID", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public HaveFunLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "HaveFun.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\'\u00d4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\7\6f\n"+
		"\6\f\6\16\6i\13\6\5\6k\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7v\n"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3"+
		" \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\6%\u00ca\n%\r%\16%\u00cb\3&\6&\u00cf\n"+
		"&\r&\16&\u00d0\3&\3&\2\2\'\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'\3\2\6\3\2\63;\3\2\62;\3\2"+
		"c|\5\2\13\f\17\17\"\"\2\u00d8\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5Q\3"+
		"\2\2\2\7X\3\2\2\2\t[\3\2\2\2\13j\3\2\2\2\ru\3\2\2\2\17w\3\2\2\2\21y\3"+
		"\2\2\2\23{\3\2\2\2\25}\3\2\2\2\27\177\3\2\2\2\31\u0083\3\2\2\2\33\u0085"+
		"\3\2\2\2\35\u0087\3\2\2\2\37\u0089\3\2\2\2!\u008c\3\2\2\2#\u008f\3\2\2"+
		"\2%\u0092\3\2\2\2\'\u0095\3\2\2\2)\u0097\3\2\2\2+\u0099\3\2\2\2-\u009b"+
		"\3\2\2\2/\u009e\3\2\2\2\61\u00a3\3\2\2\2\63\u00a8\3\2\2\2\65\u00ae\3\2"+
		"\2\2\67\u00b3\3\2\2\29\u00b5\3\2\2\2;\u00b9\3\2\2\2=\u00bc\3\2\2\2?\u00be"+
		"\3\2\2\2A\u00c0\3\2\2\2C\u00c2\3\2\2\2E\u00c4\3\2\2\2G\u00c6\3\2\2\2I"+
		"\u00c9\3\2\2\2K\u00ce\3\2\2\2MN\7h\2\2NO\7w\2\2OP\7p\2\2P\4\3\2\2\2QR"+
		"\7t\2\2RS\7g\2\2ST\7v\2\2TU\7w\2\2UV\7t\2\2VW\7p\2\2W\6\3\2\2\2XY\7\60"+
		"\2\2YZ\7i\2\2Z\b\3\2\2\2[\\\7i\2\2\\]\7n\2\2]^\7q\2\2^_\7d\2\2_`\7c\2"+
		"\2`a\7n\2\2a\n\3\2\2\2bk\7\62\2\2cg\t\2\2\2df\t\3\2\2ed\3\2\2\2fi\3\2"+
		"\2\2ge\3\2\2\2gh\3\2\2\2hk\3\2\2\2ig\3\2\2\2jb\3\2\2\2jc\3\2\2\2k\f\3"+
		"\2\2\2lm\7v\2\2mn\7t\2\2no\7w\2\2ov\7g\2\2pq\7h\2\2qr\7c\2\2rs\7n\2\2"+
		"st\7u\2\2tv\7g\2\2ul\3\2\2\2up\3\2\2\2v\16\3\2\2\2wx\7-\2\2x\20\3\2\2"+
		"\2yz\7/\2\2z\22\3\2\2\2{|\7,\2\2|\24\3\2\2\2}~\7\61\2\2~\26\3\2\2\2\177"+
		"\u0080\7o\2\2\u0080\u0081\7q\2\2\u0081\u0082\7f\2\2\u0082\30\3\2\2\2\u0083"+
		"\u0084\7`\2\2\u0084\32\3\2\2\2\u0085\u0086\7(\2\2\u0086\34\3\2\2\2\u0087"+
		"\u0088\7~\2\2\u0088\36\3\2\2\2\u0089\u008a\7?\2\2\u008a\u008b\7?\2\2\u008b"+
		" \3\2\2\2\u008c\u008d\7#\2\2\u008d\u008e\7?\2\2\u008e\"\3\2\2\2\u008f"+
		"\u0090\7>\2\2\u0090\u0091\7?\2\2\u0091$\3\2\2\2\u0092\u0093\7@\2\2\u0093"+
		"\u0094\7?\2\2\u0094&\3\2\2\2\u0095\u0096\7>\2\2\u0096(\3\2\2\2\u0097\u0098"+
		"\7@\2\2\u0098*\3\2\2\2\u0099\u009a\7#\2\2\u009a,\3\2\2\2\u009b\u009c\7"+
		"k\2\2\u009c\u009d\7h\2\2\u009d.\3\2\2\2\u009e\u009f\7v\2\2\u009f\u00a0"+
		"\7j\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2\7p\2\2\u00a2\60\3\2\2\2\u00a3\u00a4"+
		"\7g\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7u\2\2\u00a6\u00a7\7g\2\2\u00a7"+
		"\62\3\2\2\2\u00a8\u00a9\7y\2\2\u00a9\u00aa\7j\2\2\u00aa\u00ab\7k\2\2\u00ab"+
		"\u00ac\7n\2\2\u00ac\u00ad\7g\2\2\u00ad\64\3\2\2\2\u00ae\u00af\7u\2\2\u00af"+
		"\u00b0\7m\2\2\u00b0\u00b1\7k\2\2\u00b1\u00b2\7r\2\2\u00b2\66\3\2\2\2\u00b3"+
		"\u00b4\7?\2\2\u00b48\3\2\2\2\u00b5\u00b6\7q\2\2\u00b6\u00b7\7w\2\2\u00b7"+
		"\u00b8\7v\2\2\u00b8:\3\2\2\2\u00b9\u00ba\7p\2\2\u00ba\u00bb\7f\2\2\u00bb"+
		"<\3\2\2\2\u00bc\u00bd\7*\2\2\u00bd>\3\2\2\2\u00be\u00bf\7+\2\2\u00bf@"+
		"\3\2\2\2\u00c0\u00c1\7}\2\2\u00c1B\3\2\2\2\u00c2\u00c3\7\177\2\2\u00c3"+
		"D\3\2\2\2\u00c4\u00c5\7=\2\2\u00c5F\3\2\2\2\u00c6\u00c7\7.\2\2\u00c7H"+
		"\3\2\2\2\u00c8\u00ca\t\4\2\2\u00c9\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb"+
		"\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00ccJ\3\2\2\2\u00cd\u00cf\t\5\2\2"+
		"\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1"+
		"\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\b&\2\2\u00d3L\3\2\2\2\b\2gju"+
		"\u00cb\u00d0\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}