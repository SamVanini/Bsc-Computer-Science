// Generated from /home/samantha/IdeaProjects/Brainfuck/src/Brainfuck.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BrainfuckParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LT=1, GT=2, PLUS=3, MINUS=4, DOT=5, COMMA=6, LBRACK=7, RBRACK=8, EXTRA=9;
	public static final int
		RULE_main = 0, RULE_com = 1;
	private static String[] makeRuleNames() {
		return new String[] {
			"main", "com"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<'", "'>'", "'+'", "'-'", "'.'", "','", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LT", "GT", "PLUS", "MINUS", "DOT", "COMMA", "LBRACK", "RBRACK", 
			"EXTRA"
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

	@Override
	public String getGrammarFileName() { return "Brainfuck.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BrainfuckParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MainContext extends ParserRuleContext {
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode EOF() { return getToken(BrainfuckParser.EOF, 0); }
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_main);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(4);
			com();
			setState(5);
			match(EOF);
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

	public static class ComContext extends ParserRuleContext {
		public ComContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_com; }
	 
		public ComContext() { }
		public void copyFrom(ComContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NilContext extends ComContext {
		public NilContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitNil(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinusContext extends ComContext {
		public TerminalNode MINUS() { return getToken(BrainfuckParser.MINUS, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public MinusContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CommaContext extends ComContext {
		public TerminalNode COMMA() { return getToken(BrainfuckParser.COMMA, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public CommaContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitComma(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoopContext extends ComContext {
		public TerminalNode LBRACK() { return getToken(BrainfuckParser.LBRACK, 0); }
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public TerminalNode RBRACK() { return getToken(BrainfuckParser.RBRACK, 0); }
		public LoopContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LtContext extends ComContext {
		public TerminalNode LT() { return getToken(BrainfuckParser.LT, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public LtContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitLt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DotContext extends ComContext {
		public TerminalNode DOT() { return getToken(BrainfuckParser.DOT, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public DotContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitDot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GtContext extends ComContext {
		public TerminalNode GT() { return getToken(BrainfuckParser.GT, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public GtContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitGt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusContext extends ComContext {
		public TerminalNode PLUS() { return getToken(BrainfuckParser.PLUS, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public PlusContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BrainfuckVisitor ) return ((BrainfuckVisitor<? extends T>)visitor).visitPlus(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComContext com() throws RecognitionException {
		ComContext _localctx = new ComContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_com);
		try {
			setState(25);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				_localctx = new LtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(7);
				match(LT);
				setState(8);
				com();
				}
				break;
			case GT:
				_localctx = new GtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(9);
				match(GT);
				setState(10);
				com();
				}
				break;
			case PLUS:
				_localctx = new PlusContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(11);
				match(PLUS);
				setState(12);
				com();
				}
				break;
			case MINUS:
				_localctx = new MinusContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(13);
				match(MINUS);
				setState(14);
				com();
				}
				break;
			case DOT:
				_localctx = new DotContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(15);
				match(DOT);
				setState(16);
				com();
				}
				break;
			case COMMA:
				_localctx = new CommaContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(17);
				match(COMMA);
				setState(18);
				com();
				}
				break;
			case LBRACK:
				_localctx = new LoopContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(19);
				match(LBRACK);
				setState(20);
				com();
				setState(21);
				match(RBRACK);
				setState(22);
				com();
				}
				break;
			case EOF:
			case RBRACK:
				_localctx = new NilContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13\36\4\2\t\2\4\3"+
		"\t\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\5\3\34\n\3\3\3\2\2\4\2\4\2\2\2\"\2\6\3\2\2\2\4\33\3"+
		"\2\2\2\6\7\5\4\3\2\7\b\7\2\2\3\b\3\3\2\2\2\t\n\7\3\2\2\n\34\5\4\3\2\13"+
		"\f\7\4\2\2\f\34\5\4\3\2\r\16\7\5\2\2\16\34\5\4\3\2\17\20\7\6\2\2\20\34"+
		"\5\4\3\2\21\22\7\7\2\2\22\34\5\4\3\2\23\24\7\b\2\2\24\34\5\4\3\2\25\26"+
		"\7\t\2\2\26\27\5\4\3\2\27\30\7\n\2\2\30\31\5\4\3\2\31\34\3\2\2\2\32\34"+
		"\3\2\2\2\33\t\3\2\2\2\33\13\3\2\2\2\33\r\3\2\2\2\33\17\3\2\2\2\33\21\3"+
		"\2\2\2\33\23\3\2\2\2\33\25\3\2\2\2\33\32\3\2\2\2\34\5\3\2\2\2\3\33";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}