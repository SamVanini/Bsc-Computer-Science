// Generated from /home/samantha/IdeaProjects/HaveFun/src/HaveFun.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HaveFunParser extends Parser {
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
	public static final int
		RULE_prog = 0, RULE_fun = 1, RULE_com = 2, RULE_exp = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "fun", "com", "exp"
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

	@Override
	public String getGrammarFileName() { return "HaveFun.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HaveFunParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode EOF() { return getToken(HaveFunParser.EOF, 0); }
		public List<FunContext> fun() {
			return getRuleContexts(FunContext.class);
		}
		public FunContext fun(int i) {
			return getRuleContext(FunContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(11);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUN) {
				{
				{
				setState(8);
				fun();
				}
				}
				setState(13);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(14);
			com(0);
			setState(15);
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

	public static class FunContext extends ParserRuleContext {
		public TerminalNode FUN() { return getToken(HaveFunParser.FUN, 0); }
		public List<TerminalNode> ID() { return getTokens(HaveFunParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(HaveFunParser.ID, i);
		}
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public TerminalNode LBRACE() { return getToken(HaveFunParser.LBRACE, 0); }
		public TerminalNode RET() { return getToken(HaveFunParser.RET, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(HaveFunParser.RBRACE, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(HaveFunParser.SEMICOLON, 0); }
		public List<TerminalNode> COLON() { return getTokens(HaveFunParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(HaveFunParser.COLON, i);
		}
		public FunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitFun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunContext fun() throws RecognitionException {
		FunContext _localctx = new FunContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fun);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			match(FUN);
			setState(18);
			match(ID);
			setState(19);
			match(LPAR);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(20);
				match(ID);
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COLON) {
					{
					{
					setState(21);
					match(COLON);
					setState(22);
					match(ID);
					}
					}
					setState(27);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(30);
			match(RPAR);
			setState(31);
			match(LBRACE);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GLOBAL) | (1L << IF) | (1L << WHILE) | (1L << SKIPP) | (1L << OUT) | (1L << LBRACE) | (1L << ID))) != 0)) {
				{
				setState(32);
				com(0);
				setState(33);
				match(SEMICOLON);
				}
			}

			setState(37);
			match(RET);
			setState(38);
			exp(0);
			setState(39);
			match(RBRACE);
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
	public static class GlobalDeclContext extends ComContext {
		public TerminalNode GLOBAL() { return getToken(HaveFunParser.GLOBAL, 0); }
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(HaveFunParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public GlobalDeclContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitGlobalDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GlobalAssignContext extends ComContext {
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public TerminalNode DOTG() { return getToken(HaveFunParser.DOTG, 0); }
		public TerminalNode ASSIGN() { return getToken(HaveFunParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public GlobalAssignContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitGlobalAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NdContext extends ComContext {
		public List<TerminalNode> LBRACE() { return getTokens(HaveFunParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(HaveFunParser.LBRACE, i);
		}
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(HaveFunParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(HaveFunParser.RBRACE, i);
		}
		public TerminalNode ND() { return getToken(HaveFunParser.ND, 0); }
		public NdContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitNd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SkipContext extends ComContext {
		public TerminalNode SKIPP() { return getToken(HaveFunParser.SKIPP, 0); }
		public SkipContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitSkip(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileContext extends ComContext {
		public TerminalNode WHILE() { return getToken(HaveFunParser.WHILE, 0); }
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public TerminalNode LBRACE() { return getToken(HaveFunParser.LBRACE, 0); }
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(HaveFunParser.RBRACE, 0); }
		public WhileContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfContext extends ComContext {
		public TerminalNode IF() { return getToken(HaveFunParser.IF, 0); }
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public TerminalNode THEN() { return getToken(HaveFunParser.THEN, 0); }
		public List<TerminalNode> LBRACE() { return getTokens(HaveFunParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(HaveFunParser.LBRACE, i);
		}
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(HaveFunParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(HaveFunParser.RBRACE, i);
		}
		public TerminalNode ELSE() { return getToken(HaveFunParser.ELSE, 0); }
		public IfContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SeqContext extends ComContext {
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(HaveFunParser.SEMICOLON, 0); }
		public SeqContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitSeq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignContext extends ComContext {
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(HaveFunParser.ASSIGN, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OutContext extends ComContext {
		public TerminalNode OUT() { return getToken(HaveFunParser.OUT, 0); }
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public OutContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitOut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComContext com() throws RecognitionException {
		return com(0);
	}

	private ComContext com(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ComContext _localctx = new ComContext(_ctx, _parentState);
		ComContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_com, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(42);
				match(IF);
				setState(43);
				match(LPAR);
				setState(44);
				exp(0);
				setState(45);
				match(RPAR);
				setState(46);
				match(THEN);
				setState(47);
				match(LBRACE);
				setState(48);
				com(0);
				setState(49);
				match(RBRACE);
				setState(50);
				match(ELSE);
				setState(51);
				match(LBRACE);
				setState(52);
				com(0);
				setState(53);
				match(RBRACE);
				}
				break;
			case 2:
				{
				_localctx = new GlobalDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
				match(GLOBAL);
				setState(56);
				match(ID);
				setState(57);
				match(ASSIGN);
				setState(58);
				exp(0);
				}
				break;
			case 3:
				{
				_localctx = new GlobalAssignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59);
				match(ID);
				setState(60);
				match(DOTG);
				setState(61);
				match(ASSIGN);
				setState(62);
				exp(0);
				}
				break;
			case 4:
				{
				_localctx = new AssignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(63);
				match(ID);
				setState(64);
				match(ASSIGN);
				setState(65);
				exp(0);
				}
				break;
			case 5:
				{
				_localctx = new SkipContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(66);
				match(SKIPP);
				}
				break;
			case 6:
				{
				_localctx = new WhileContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(67);
				match(WHILE);
				setState(68);
				match(LPAR);
				setState(69);
				exp(0);
				setState(70);
				match(RPAR);
				setState(71);
				match(LBRACE);
				setState(72);
				com(0);
				setState(73);
				match(RBRACE);
				}
				break;
			case 7:
				{
				_localctx = new OutContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(75);
				match(OUT);
				setState(76);
				match(LPAR);
				setState(77);
				exp(0);
				setState(78);
				match(RPAR);
				}
				break;
			case 8:
				{
				_localctx = new NdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(80);
				match(LBRACE);
				setState(81);
				com(0);
				setState(82);
				match(RBRACE);
				setState(83);
				match(ND);
				setState(84);
				match(LBRACE);
				setState(85);
				com(0);
				setState(86);
				match(RBRACE);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(95);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SeqContext(new ComContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_com);
					setState(90);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(91);
					match(SEMICOLON);
					setState(92);
					com(5);
					}
					} 
				}
				setState(97);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NatContext extends ExpContext {
		public TerminalNode NAT() { return getToken(HaveFunParser.NAT, 0); }
		public NatContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitNat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncallContext extends ExpContext {
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(HaveFunParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(HaveFunParser.COLON, i);
		}
		public FuncallContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitFuncall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusMinusContext extends ExpContext {
		public Token op;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(HaveFunParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(HaveFunParser.MINUS, 0); }
		public PlusMinusContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitPlusMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolContext extends ExpContext {
		public TerminalNode BOOL() { return getToken(HaveFunParser.BOOL, 0); }
		public BoolContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GlobalIDContext extends ExpContext {
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public TerminalNode DOTG() { return getToken(HaveFunParser.DOTG, 0); }
		public GlobalIDContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitGlobalID(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivMulModContext extends ExpContext {
		public Token op;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode DIV() { return getToken(HaveFunParser.DIV, 0); }
		public TerminalNode MUL() { return getToken(HaveFunParser.MUL, 0); }
		public TerminalNode MOD() { return getToken(HaveFunParser.MOD, 0); }
		public DivMulModContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitDivMulMod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotContext extends ExpContext {
		public TerminalNode NOT() { return getToken(HaveFunParser.NOT, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public NotContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqExpContext extends ExpContext {
		public Token op;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode EQQ() { return getToken(HaveFunParser.EQQ, 0); }
		public TerminalNode NEQ() { return getToken(HaveFunParser.NEQ, 0); }
		public EqExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitEqExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CmpExpContext extends ExpContext {
		public Token op;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode LT() { return getToken(HaveFunParser.LT, 0); }
		public TerminalNode LEQ() { return getToken(HaveFunParser.LEQ, 0); }
		public TerminalNode GEQ() { return getToken(HaveFunParser.GEQ, 0); }
		public TerminalNode GT() { return getToken(HaveFunParser.GT, 0); }
		public CmpExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitCmpExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicExpContext extends ExpContext {
		public Token op;
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode AND() { return getToken(HaveFunParser.AND, 0); }
		public TerminalNode OR() { return getToken(HaveFunParser.OR, 0); }
		public LogicExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitLogicExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParExpContext extends ExpContext {
		public TerminalNode LPAR() { return getToken(HaveFunParser.LPAR, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(HaveFunParser.RPAR, 0); }
		public ParExpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitParExp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode POW() { return getToken(HaveFunParser.POW, 0); }
		public PowContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitPow(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdContext extends ExpContext {
		public TerminalNode ID() { return getToken(HaveFunParser.ID, 0); }
		public IdContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof HaveFunVisitor ) return ((HaveFunVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new NatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(99);
				match(NAT);
				}
				break;
			case 2:
				{
				_localctx = new BoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(100);
				match(BOOL);
				}
				break;
			case 3:
				{
				_localctx = new ParExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101);
				match(LPAR);
				setState(102);
				exp(0);
				setState(103);
				match(RPAR);
				}
				break;
			case 4:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(105);
				match(NOT);
				setState(106);
				exp(9);
				}
				break;
			case 5:
				{
				_localctx = new FuncallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(107);
				match(ID);
				setState(108);
				match(LPAR);
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NAT) | (1L << BOOL) | (1L << NOT) | (1L << LPAR) | (1L << ID))) != 0)) {
					{
					setState(109);
					exp(0);
					setState(114);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COLON) {
						{
						{
						setState(110);
						match(COLON);
						setState(111);
						exp(0);
						}
						}
						setState(116);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(119);
				match(RPAR);
				}
				break;
			case 6:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(120);
				match(ID);
				}
				break;
			case 7:
				{
				_localctx = new GlobalIDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(121);
				match(ID);
				setState(122);
				match(DOTG);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(145);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(143);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new PowContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(125);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(126);
						match(POW);
						setState(127);
						exp(10);
						}
						break;
					case 2:
						{
						_localctx = new DivMulModContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(128);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(129);
						((DivMulModContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((DivMulModContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(130);
						exp(9);
						}
						break;
					case 3:
						{
						_localctx = new PlusMinusContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(131);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(132);
						((PlusMinusContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((PlusMinusContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(133);
						exp(8);
						}
						break;
					case 4:
						{
						_localctx = new CmpExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(134);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(135);
						((CmpExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LEQ) | (1L << GEQ) | (1L << LT) | (1L << GT))) != 0)) ) {
							((CmpExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(136);
						exp(7);
						}
						break;
					case 5:
						{
						_localctx = new EqExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(137);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(138);
						((EqExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQQ || _la==NEQ) ) {
							((EqExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(139);
						exp(6);
						}
						break;
					case 6:
						{
						_localctx = new LogicExpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(140);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(141);
						((LogicExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((LogicExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(142);
						exp(5);
						}
						break;
					}
					} 
				}
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return com_sempred((ComContext)_localctx, predIndex);
		case 3:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean com_sempred(ComContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\u0097\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\3\2\7\2\f\n\2\f\2\16\2\17\13\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\7\3\32\n\3\f\3\16\3\35\13\3\5\3\37\n\3\3\3\3\3\3"+
		"\3\3\3\3\3\5\3&\n\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\5\4[\n\4\3\4\3\4\3\4\7\4`\n\4\f\4\16\4c\13\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5s\n\5\f\5\16\5v\13\5"+
		"\5\5x\n\5\3\5\3\5\3\5\3\5\5\5~\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u0092\n\5\f\5\16\5\u0095\13\5"+
		"\3\5\2\4\6\b\6\2\4\6\b\2\7\3\2\13\r\3\2\t\n\3\2\23\26\3\2\21\22\3\2\17"+
		"\20\2\u00ac\2\r\3\2\2\2\4\23\3\2\2\2\6Z\3\2\2\2\b}\3\2\2\2\n\f\5\4\3\2"+
		"\13\n\3\2\2\2\f\17\3\2\2\2\r\13\3\2\2\2\r\16\3\2\2\2\16\20\3\2\2\2\17"+
		"\r\3\2\2\2\20\21\5\6\4\2\21\22\7\2\2\3\22\3\3\2\2\2\23\24\7\3\2\2\24\25"+
		"\7&\2\2\25\36\7 \2\2\26\33\7&\2\2\27\30\7%\2\2\30\32\7&\2\2\31\27\3\2"+
		"\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\37\3\2\2\2\35\33\3\2"+
		"\2\2\36\26\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 !\7!\2\2!%\7\"\2\2\"#\5\6"+
		"\4\2#$\7$\2\2$&\3\2\2\2%\"\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\7\4\2\2()\5"+
		"\b\5\2)*\7#\2\2*\5\3\2\2\2+,\b\4\1\2,-\7\30\2\2-.\7 \2\2./\5\b\5\2/\60"+
		"\7!\2\2\60\61\7\31\2\2\61\62\7\"\2\2\62\63\5\6\4\2\63\64\7#\2\2\64\65"+
		"\7\32\2\2\65\66\7\"\2\2\66\67\5\6\4\2\678\7#\2\28[\3\2\2\29:\7\6\2\2:"+
		";\7&\2\2;<\7\35\2\2<[\5\b\5\2=>\7&\2\2>?\7\5\2\2?@\7\35\2\2@[\5\b\5\2"+
		"AB\7&\2\2BC\7\35\2\2C[\5\b\5\2D[\7\34\2\2EF\7\33\2\2FG\7 \2\2GH\5\b\5"+
		"\2HI\7!\2\2IJ\7\"\2\2JK\5\6\4\2KL\7#\2\2L[\3\2\2\2MN\7\36\2\2NO\7 \2\2"+
		"OP\5\b\5\2PQ\7!\2\2Q[\3\2\2\2RS\7\"\2\2ST\5\6\4\2TU\7#\2\2UV\7\37\2\2"+
		"VW\7\"\2\2WX\5\6\4\2XY\7#\2\2Y[\3\2\2\2Z+\3\2\2\2Z9\3\2\2\2Z=\3\2\2\2"+
		"ZA\3\2\2\2ZD\3\2\2\2ZE\3\2\2\2ZM\3\2\2\2ZR\3\2\2\2[a\3\2\2\2\\]\f\6\2"+
		"\2]^\7$\2\2^`\5\6\4\7_\\\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\7\3\2"+
		"\2\2ca\3\2\2\2de\b\5\1\2e~\7\7\2\2f~\7\b\2\2gh\7 \2\2hi\5\b\5\2ij\7!\2"+
		"\2j~\3\2\2\2kl\7\27\2\2l~\5\b\5\13mn\7&\2\2nw\7 \2\2ot\5\b\5\2pq\7%\2"+
		"\2qs\5\b\5\2rp\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2ux\3\2\2\2vt\3\2\2"+
		"\2wo\3\2\2\2wx\3\2\2\2xy\3\2\2\2y~\7!\2\2z~\7&\2\2{|\7&\2\2|~\7\5\2\2"+
		"}d\3\2\2\2}f\3\2\2\2}g\3\2\2\2}k\3\2\2\2}m\3\2\2\2}z\3\2\2\2}{\3\2\2\2"+
		"~\u0093\3\2\2\2\177\u0080\f\f\2\2\u0080\u0081\7\16\2\2\u0081\u0092\5\b"+
		"\5\f\u0082\u0083\f\n\2\2\u0083\u0084\t\2\2\2\u0084\u0092\5\b\5\13\u0085"+
		"\u0086\f\t\2\2\u0086\u0087\t\3\2\2\u0087\u0092\5\b\5\n\u0088\u0089\f\b"+
		"\2\2\u0089\u008a\t\4\2\2\u008a\u0092\5\b\5\t\u008b\u008c\f\7\2\2\u008c"+
		"\u008d\t\5\2\2\u008d\u0092\5\b\5\b\u008e\u008f\f\6\2\2\u008f\u0090\t\6"+
		"\2\2\u0090\u0092\5\b\5\7\u0091\177\3\2\2\2\u0091\u0082\3\2\2\2\u0091\u0085"+
		"\3\2\2\2\u0091\u0088\3\2\2\2\u0091\u008b\3\2\2\2\u0091\u008e\3\2\2\2\u0092"+
		"\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\t\3\2\2\2"+
		"\u0095\u0093\3\2\2\2\r\r\33\36%Zatw}\u0091\u0093";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}