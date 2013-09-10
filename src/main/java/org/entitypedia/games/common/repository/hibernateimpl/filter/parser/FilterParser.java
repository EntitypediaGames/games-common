// Generated from Filter.g4 by ANTLR 4.1

package org.entitypedia.games.common.repository.hibernateimpl.filter.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, LRB=4, RRB=5, EQ=6, EQP=7, GE=8, GEP=9, GT=10, 
		GTP=11, LIKE=12, ILIKE=13, LE=14, LEP=15, LT=16, LTP=17, NE=18, NEP=19, 
		SEQ=20, SGE=21, SGT=22, SLE=23, SLT=24, SNE=25, ISEMPTY=26, ISNOTEMPTY=27, 
		ISNOTNULL=28, ISNULL=29, AND=30, OR=31, NOT=32, Identifier=33, DateLiteral=34, 
		FloatingPointLiteral=35, DecimalLiteral=36, StringLiteral=37, WS=38;
	public static final String[] tokenNames = {
		"<INVALID>", "'.'", "'false'", "'true'", "'('", "')'", "'eq'", "'eqP'", 
		"'ge'", "'geP'", "'gt'", "'gtP'", "'like'", "'ilike'", "'le'", "'leP'", 
		"'lt'", "'ltP'", "'ne'", "'neP'", "'sizeEq'", "'sizeGe'", "'sizeGt'", 
		"'sizeLe'", "'sizeLt'", "'sizeNe'", "'isEmpty'", "'isNotEmpty'", "'isNotNull'", 
		"'isNull'", "'and'", "'or'", "'not'", "Identifier", "DateLiteral", "FloatingPointLiteral", 
		"DecimalLiteral", "StringLiteral", "WS"
	};
	public static final int
		RULE_init = 0, RULE_expr = 1, RULE_oper = 2, RULE_literal = 3, RULE_qualifiedName = 4, 
		RULE_booleanLiteral = 5;
	public static final String[] ruleNames = {
		"init", "expr", "oper", "literal", "qualifiedName", "booleanLiteral"
	};

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public FilterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class InitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FilterParser.EOF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12); expr(0);
			setState(13); match(EOF);
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

	public static class ExprContext extends ParserRuleContext {
		public int _p;
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class ConContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode AND() { return getToken(FilterParser.AND, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ConContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitCon(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(FilterParser.NOT, 0); }
		public NegContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNeg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BktContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RRB() { return getToken(FilterParser.RRB, 0); }
		public TerminalNode LRB() { return getToken(FilterParser.LRB, 0); }
		public BktContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitBkt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OpContext extends ExprContext {
		public OperContext oper() {
			return getRuleContext(OperContext.class,0);
		}
		public OpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitOp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DisContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OR() { return getToken(FilterParser.OR, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DisContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitDis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState, _p);
		ExprContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new NegContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(16); match(NOT);
				setState(17); expr(4);
				}
				break;
			case Identifier:
				{
				_localctx = new OpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(18); oper();
				}
				break;
			case LRB:
				{
				_localctx = new BktContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19); match(LRB);
				setState(20); expr(0);
				setState(21); match(RRB);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(33);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(31);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ConContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(25);
						if (!(3 >= _localctx._p)) throw new FailedPredicateException(this, "3 >= $_p");
						setState(26); match(AND);
						setState(27); expr(4);
						}
						break;

					case 2:
						{
						_localctx = new DisContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(28);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(29); match(OR);
						setState(30); expr(3);
						}
						break;
					}
					} 
				}
				setState(35);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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

	public static class OperContext extends ParserRuleContext {
		public OperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oper; }
	 
		public OperContext() { }
		public void copyFrom(OperContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsNotEmptyContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode ISNOTEMPTY() { return getToken(FilterParser.ISNOTEMPTY, 0); }
		public IsNotEmptyContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNullContext extends OperContext {
		public TerminalNode ISNULL() { return getToken(FilterParser.ISNULL, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public IsNullContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNotNullContext extends OperContext {
		public TerminalNode ISNOTNULL() { return getToken(FilterParser.ISNOTNULL, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public IsNotNullContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNotNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IlikeContext extends OperContext {
		public TerminalNode ILIKE() { return getToken(FilterParser.ILIKE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode StringLiteral() { return getToken(FilterParser.StringLiteral, 0); }
		public IlikeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIlike(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GePContext extends OperContext {
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public TerminalNode GEP() { return getToken(FilterParser.GEP, 0); }
		public GePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NePContext extends OperContext {
		public TerminalNode NEP() { return getToken(FilterParser.NEP, 0); }
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public NePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SltContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SLT() { return getToken(FilterParser.SLT, 0); }
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SltContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSlt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LtPContext extends OperContext {
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public TerminalNode LTP() { return getToken(FilterParser.LTP, 0); }
		public LtPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLtP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SgtContext extends OperContext {
		public TerminalNode SGT() { return getToken(FilterParser.SGT, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SgtContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSgt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GtPContext extends OperContext {
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public TerminalNode GTP() { return getToken(FilterParser.GTP, 0); }
		public GtPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGtP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GeContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode GE() { return getToken(FilterParser.GE, 0); }
		public GeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LtContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LT() { return getToken(FilterParser.LT, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LtContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LikeContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode StringLiteral() { return getToken(FilterParser.StringLiteral, 0); }
		public TerminalNode LIKE() { return getToken(FilterParser.LIKE, 0); }
		public LikeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLike(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LePContext extends OperContext {
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public TerminalNode LEP() { return getToken(FilterParser.LEP, 0); }
		public LePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsEmptyContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode ISEMPTY() { return getToken(FilterParser.ISEMPTY, 0); }
		public IsEmptyContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LeContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LE() { return getToken(FilterParser.LE, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NeContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode NE() { return getToken(FilterParser.NE, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public NeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SeqContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SEQ() { return getToken(FilterParser.SEQ, 0); }
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SeqContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSeq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GtContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode GT() { return getToken(FilterParser.GT, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public GtContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SgeContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SGE() { return getToken(FilterParser.SGE, 0); }
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SgeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSge(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SneContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SNE() { return getToken(FilterParser.SNE, 0); }
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SneContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSne(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqPContext extends OperContext {
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public TerminalNode EQP() { return getToken(FilterParser.EQP, 0); }
		public EqPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitEqP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SleContext extends OperContext {
		public TerminalNode SLE() { return getToken(FilterParser.SLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public SleContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSle(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqContext extends OperContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode EQ() { return getToken(FilterParser.EQ, 0); }
		public EqContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitEq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperContext oper() throws RecognitionException {
		OperContext _localctx = new OperContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_oper);
		try {
			setState(128);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new EqContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(36); qualifiedName();
				setState(37); match(EQ);
				setState(38); literal();
				}
				break;

			case 2:
				_localctx = new EqPContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(40); qualifiedName();
				setState(41); match(EQP);
				setState(42); qualifiedName();
				}
				break;

			case 3:
				_localctx = new GeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(44); qualifiedName();
				setState(45); match(GE);
				setState(46); literal();
				}
				break;

			case 4:
				_localctx = new GePContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(48); qualifiedName();
				setState(49); match(GEP);
				setState(50); qualifiedName();
				}
				break;

			case 5:
				_localctx = new GtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(52); qualifiedName();
				setState(53); match(GT);
				setState(54); literal();
				}
				break;

			case 6:
				_localctx = new GtPContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(56); qualifiedName();
				setState(57); match(GTP);
				setState(58); qualifiedName();
				}
				break;

			case 7:
				_localctx = new LikeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(60); qualifiedName();
				setState(61); match(LIKE);
				setState(62); match(StringLiteral);
				}
				break;

			case 8:
				_localctx = new IlikeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(64); qualifiedName();
				setState(65); match(ILIKE);
				setState(66); match(StringLiteral);
				}
				break;

			case 9:
				_localctx = new LeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(68); qualifiedName();
				setState(69); match(LE);
				setState(70); literal();
				}
				break;

			case 10:
				_localctx = new LePContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(72); qualifiedName();
				setState(73); match(LEP);
				setState(74); qualifiedName();
				}
				break;

			case 11:
				_localctx = new LtContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(76); qualifiedName();
				setState(77); match(LT);
				setState(78); literal();
				}
				break;

			case 12:
				_localctx = new LtPContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(80); qualifiedName();
				setState(81); match(LTP);
				setState(82); qualifiedName();
				}
				break;

			case 13:
				_localctx = new NeContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(84); qualifiedName();
				setState(85); match(NE);
				setState(86); literal();
				}
				break;

			case 14:
				_localctx = new NePContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(88); qualifiedName();
				setState(89); match(NEP);
				setState(90); qualifiedName();
				}
				break;

			case 15:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(92); qualifiedName();
				setState(93); match(SEQ);
				setState(94); match(DecimalLiteral);
				}
				break;

			case 16:
				_localctx = new SgeContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(96); qualifiedName();
				setState(97); match(SGE);
				setState(98); match(DecimalLiteral);
				}
				break;

			case 17:
				_localctx = new SgtContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(100); qualifiedName();
				setState(101); match(SGT);
				setState(102); match(DecimalLiteral);
				}
				break;

			case 18:
				_localctx = new SleContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(104); qualifiedName();
				setState(105); match(SLE);
				setState(106); match(DecimalLiteral);
				}
				break;

			case 19:
				_localctx = new SltContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(108); qualifiedName();
				setState(109); match(SLT);
				setState(110); match(DecimalLiteral);
				}
				break;

			case 20:
				_localctx = new SneContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(112); qualifiedName();
				setState(113); match(SNE);
				setState(114); match(DecimalLiteral);
				}
				break;

			case 21:
				_localctx = new IsEmptyContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(116); qualifiedName();
				setState(117); match(ISEMPTY);
				}
				break;

			case 22:
				_localctx = new IsNotEmptyContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(119); qualifiedName();
				setState(120); match(ISNOTEMPTY);
				}
				break;

			case 23:
				_localctx = new IsNotNullContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(122); qualifiedName();
				setState(123); match(ISNOTNULL);
				}
				break;

			case 24:
				_localctx = new IsNullContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(125); qualifiedName();
				setState(126); match(ISNULL);
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

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StringContext extends LiteralContext {
		public TerminalNode StringLiteral() { return getToken(FilterParser.StringLiteral, 0); }
		public StringContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanContext extends LiteralContext {
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public BooleanContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DateContext extends LiteralContext {
		public TerminalNode DateLiteral() { return getToken(FilterParser.DateLiteral, 0); }
		public DateContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecimalContext extends LiteralContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public DecimalContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FloatContext extends LiteralContext {
		public TerminalNode FloatingPointLiteral() { return getToken(FilterParser.FloatingPointLiteral, 0); }
		public FloatContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitFloat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_literal);
		try {
			setState(135);
			switch (_input.LA(1)) {
			case DecimalLiteral:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(130); match(DecimalLiteral);
				}
				break;
			case FloatingPointLiteral:
				_localctx = new FloatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(131); match(FloatingPointLiteral);
				}
				break;
			case StringLiteral:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(132); match(StringLiteral);
				}
				break;
			case 2:
			case 3:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(133); booleanLiteral();
				}
				break;
			case DateLiteral:
				_localctx = new DateContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(134); match(DateLiteral);
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

	public static class QualifiedNameContext extends ParserRuleContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(137); match(Identifier);
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(138); match(1);
					setState(139); match(Identifier);
					}
					} 
				}
				setState(144);
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
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_la = _input.LA(1);
			if ( !(_la==2 || _la==3) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 3 >= _localctx._p;

		case 1: return 2 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3(\u0096\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\5\3\32\n\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\"\n\3\f\3\16\3%\13"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\5\4\u0083\n\4\3\5\3\5\3\5\3\5\3\5\5\5\u008a\n\5\3"+
		"\6\3\6\3\6\7\6\u008f\n\6\f\6\16\6\u0092\13\6\3\7\3\7\3\7\2\b\2\4\6\b\n"+
		"\f\2\3\3\2\4\5\u00af\2\16\3\2\2\2\4\31\3\2\2\2\6\u0082\3\2\2\2\b\u0089"+
		"\3\2\2\2\n\u008b\3\2\2\2\f\u0093\3\2\2\2\16\17\5\4\3\2\17\20\7\2\2\3\20"+
		"\3\3\2\2\2\21\22\b\3\1\2\22\23\7\"\2\2\23\32\5\4\3\2\24\32\5\6\4\2\25"+
		"\26\7\6\2\2\26\27\5\4\3\2\27\30\7\7\2\2\30\32\3\2\2\2\31\21\3\2\2\2\31"+
		"\24\3\2\2\2\31\25\3\2\2\2\32#\3\2\2\2\33\34\6\3\2\3\34\35\7 \2\2\35\""+
		"\5\4\3\2\36\37\6\3\3\3\37 \7!\2\2 \"\5\4\3\2!\33\3\2\2\2!\36\3\2\2\2\""+
		"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\5\3\2\2\2%#\3\2\2\2&\'\5\n\6\2\'(\7\b\2"+
		"\2()\5\b\5\2)\u0083\3\2\2\2*+\5\n\6\2+,\7\t\2\2,-\5\n\6\2-\u0083\3\2\2"+
		"\2./\5\n\6\2/\60\7\n\2\2\60\61\5\b\5\2\61\u0083\3\2\2\2\62\63\5\n\6\2"+
		"\63\64\7\13\2\2\64\65\5\n\6\2\65\u0083\3\2\2\2\66\67\5\n\6\2\678\7\f\2"+
		"\289\5\b\5\29\u0083\3\2\2\2:;\5\n\6\2;<\7\r\2\2<=\5\n\6\2=\u0083\3\2\2"+
		"\2>?\5\n\6\2?@\7\16\2\2@A\7\'\2\2A\u0083\3\2\2\2BC\5\n\6\2CD\7\17\2\2"+
		"DE\7\'\2\2E\u0083\3\2\2\2FG\5\n\6\2GH\7\20\2\2HI\5\b\5\2I\u0083\3\2\2"+
		"\2JK\5\n\6\2KL\7\21\2\2LM\5\n\6\2M\u0083\3\2\2\2NO\5\n\6\2OP\7\22\2\2"+
		"PQ\5\b\5\2Q\u0083\3\2\2\2RS\5\n\6\2ST\7\23\2\2TU\5\n\6\2U\u0083\3\2\2"+
		"\2VW\5\n\6\2WX\7\24\2\2XY\5\b\5\2Y\u0083\3\2\2\2Z[\5\n\6\2[\\\7\25\2\2"+
		"\\]\5\n\6\2]\u0083\3\2\2\2^_\5\n\6\2_`\7\26\2\2`a\7&\2\2a\u0083\3\2\2"+
		"\2bc\5\n\6\2cd\7\27\2\2de\7&\2\2e\u0083\3\2\2\2fg\5\n\6\2gh\7\30\2\2h"+
		"i\7&\2\2i\u0083\3\2\2\2jk\5\n\6\2kl\7\31\2\2lm\7&\2\2m\u0083\3\2\2\2n"+
		"o\5\n\6\2op\7\32\2\2pq\7&\2\2q\u0083\3\2\2\2rs\5\n\6\2st\7\33\2\2tu\7"+
		"&\2\2u\u0083\3\2\2\2vw\5\n\6\2wx\7\34\2\2x\u0083\3\2\2\2yz\5\n\6\2z{\7"+
		"\35\2\2{\u0083\3\2\2\2|}\5\n\6\2}~\7\36\2\2~\u0083\3\2\2\2\177\u0080\5"+
		"\n\6\2\u0080\u0081\7\37\2\2\u0081\u0083\3\2\2\2\u0082&\3\2\2\2\u0082*"+
		"\3\2\2\2\u0082.\3\2\2\2\u0082\62\3\2\2\2\u0082\66\3\2\2\2\u0082:\3\2\2"+
		"\2\u0082>\3\2\2\2\u0082B\3\2\2\2\u0082F\3\2\2\2\u0082J\3\2\2\2\u0082N"+
		"\3\2\2\2\u0082R\3\2\2\2\u0082V\3\2\2\2\u0082Z\3\2\2\2\u0082^\3\2\2\2\u0082"+
		"b\3\2\2\2\u0082f\3\2\2\2\u0082j\3\2\2\2\u0082n\3\2\2\2\u0082r\3\2\2\2"+
		"\u0082v\3\2\2\2\u0082y\3\2\2\2\u0082|\3\2\2\2\u0082\177\3\2\2\2\u0083"+
		"\7\3\2\2\2\u0084\u008a\7&\2\2\u0085\u008a\7%\2\2\u0086\u008a\7\'\2\2\u0087"+
		"\u008a\5\f\7\2\u0088\u008a\7$\2\2\u0089\u0084\3\2\2\2\u0089\u0085\3\2"+
		"\2\2\u0089\u0086\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u0088\3\2\2\2\u008a"+
		"\t\3\2\2\2\u008b\u0090\7#\2\2\u008c\u008d\7\3\2\2\u008d\u008f\7#\2\2\u008e"+
		"\u008c\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2"+
		"\2\2\u0091\13\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0094\t\2\2\2\u0094\r"+
		"\3\2\2\2\b\31!#\u0082\u0089\u0090";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}