// Generated from Filter.g4 by ANTLR 4.0

package org.entitypedia.games.common.service.filter.parser;

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
		LRB=1, RRB=2, EQ=3, EQP=4, GE=5, GEP=6, GT=7, GTP=8, LIKE=9, ILIKE=10, 
		LE=11, LEP=12, LT=13, LTP=14, NE=15, NEP=16, SEQ=17, SGE=18, SGT=19, SLE=20, 
		SLT=21, SNE=22, ISEMPTY=23, ISNOTEMPTY=24, ISNOTNNULL=25, ISNULL=26, AND=27, 
		OR=28, NOT=29, Identifier=30, DateLiteral=31, BooleanLiteral=32, FloatingPointLiteral=33, 
		DecimalLiteral=34, StringLiteral=35, WS=36;
	public static final String[] tokenNames = {
		"<INVALID>", "'('", "')'", "'eq'", "'eqP'", "'ge'", "'geP'", "'gt'", "'gtP'", 
		"'like'", "'ilike'", "'le'", "'leP'", "'lt'", "'ltP'", "'ne'", "'neP'", 
		"'sizeEq'", "'sizeGe'", "'sizeGt'", "'sizeLe'", "'sizeLt'", "'sizeNe'", 
		"'isEmpty'", "'isNotEmpty'", "'isNotNull'", "'isNull'", "'and'", "'or'", 
		"'not'", "Identifier", "DateLiteral", "BooleanLiteral", "FloatingPointLiteral", 
		"DecimalLiteral", "StringLiteral", "WS"
	};
	public static final int
		RULE_init = 0, RULE_expr = 1, RULE_oper = 2, RULE_literal = 3;
	public static final String[] ruleNames = {
		"init", "expr", "oper", "literal"
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
			setState(8); expr(0);
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
		public TerminalNode AND() { return getToken(FilterParser.AND, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ConContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitCon(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegContext extends ExprContext {
		public TerminalNode NOT() { return getToken(FilterParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NegContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNeg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BktContext extends ExprContext {
		public TerminalNode LRB() { return getToken(FilterParser.LRB, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RRB() { return getToken(FilterParser.RRB, 0); }
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
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OR() { return getToken(FilterParser.OR, 0); }
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
			setState(18);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new NegContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(11); match(NOT);
				setState(12); expr(4);
				}
				break;
			case Identifier:
				{
				_localctx = new OpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(13); oper();
				}
				break;
			case LRB:
				{
				_localctx = new BktContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(14); match(LRB);
				setState(15); expr(0);
				setState(16); match(RRB);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(28);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(26);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ConContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(20);
						if (!(3 >= _localctx._p)) throw new FailedPredicateException(this, "3 >= $_p");
						setState(21); match(AND);
						setState(22); expr(4);
						}
						break;

					case 2:
						{
						_localctx = new DisContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(23);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(24); match(OR);
						setState(25); expr(3);
						}
						break;
					}
					} 
				}
				setState(30);
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
		public TerminalNode ISNOTEMPTY() { return getToken(FilterParser.ISNOTEMPTY, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public IsNotEmptyContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNotEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNullContext extends OperContext {
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public TerminalNode ISNULL() { return getToken(FilterParser.ISNULL, 0); }
		public IsNullContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsNotNullContext extends OperContext {
		public TerminalNode ISNOTNNULL() { return getToken(FilterParser.ISNOTNNULL, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public IsNotNullContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsNotNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IlikeContext extends OperContext {
		public TerminalNode StringLiteral() { return getToken(FilterParser.StringLiteral, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public TerminalNode ILIKE() { return getToken(FilterParser.ILIKE, 0); }
		public IlikeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIlike(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GePContext extends OperContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public TerminalNode GEP() { return getToken(FilterParser.GEP, 0); }
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public GePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NePContext extends OperContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public TerminalNode NEP() { return getToken(FilterParser.NEP, 0); }
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public NePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SltContext extends OperContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public TerminalNode SLT() { return getToken(FilterParser.SLT, 0); }
		public SltContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSlt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LtPContext extends OperContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public TerminalNode LTP() { return getToken(FilterParser.LTP, 0); }
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public LtPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLtP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SgtContext extends OperContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode SGT() { return getToken(FilterParser.SGT, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public SgtContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSgt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GtPContext extends OperContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public TerminalNode GTP() { return getToken(FilterParser.GTP, 0); }
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public GtPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGtP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GeContext extends OperContext {
		public TerminalNode GE() { return getToken(FilterParser.GE, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public GeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitGe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LtContext extends OperContext {
		public TerminalNode LT() { return getToken(FilterParser.LT, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
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
		public TerminalNode StringLiteral() { return getToken(FilterParser.StringLiteral, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public TerminalNode LIKE() { return getToken(FilterParser.LIKE, 0); }
		public LikeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLike(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LePContext extends OperContext {
		public TerminalNode LEP() { return getToken(FilterParser.LEP, 0); }
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public LePContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitLeP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IsEmptyContext extends OperContext {
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public TerminalNode ISEMPTY() { return getToken(FilterParser.ISEMPTY, 0); }
		public IsEmptyContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitIsEmpty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LeContext extends OperContext {
		public TerminalNode LE() { return getToken(FilterParser.LE, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
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
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode NE() { return getToken(FilterParser.NE, 0); }
		public NeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitNe(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SeqContext extends OperContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode SEQ() { return getToken(FilterParser.SEQ, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public SeqContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSeq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GtContext extends OperContext {
		public TerminalNode GT() { return getToken(FilterParser.GT, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
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
		public TerminalNode SGE() { return getToken(FilterParser.SGE, 0); }
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public SgeContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSge(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SneContext extends OperContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode SNE() { return getToken(FilterParser.SNE, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public SneContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSne(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqPContext extends OperContext {
		public TerminalNode Identifier(int i) {
			return getToken(FilterParser.Identifier, i);
		}
		public List<TerminalNode> Identifier() { return getTokens(FilterParser.Identifier); }
		public TerminalNode EQP() { return getToken(FilterParser.EQP, 0); }
		public EqPContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitEqP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SleContext extends OperContext {
		public TerminalNode DecimalLiteral() { return getToken(FilterParser.DecimalLiteral, 0); }
		public TerminalNode SLE() { return getToken(FilterParser.SLE, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public SleContext(OperContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FilterVisitor ) return ((FilterVisitor<? extends T>)visitor).visitSle(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqContext extends OperContext {
		public TerminalNode EQ() { return getToken(FilterParser.EQ, 0); }
		public TerminalNode Identifier() { return getToken(FilterParser.Identifier, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
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
			setState(99);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new EqContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(31); match(Identifier);
				setState(32); match(EQ);
				setState(33); literal();
				}
				break;

			case 2:
				_localctx = new EqPContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(34); match(Identifier);
				setState(35); match(EQP);
				setState(36); match(Identifier);
				}
				break;

			case 3:
				_localctx = new GeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(37); match(Identifier);
				setState(38); match(GE);
				setState(39); literal();
				}
				break;

			case 4:
				_localctx = new GePContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(40); match(Identifier);
				setState(41); match(GEP);
				setState(42); match(Identifier);
				}
				break;

			case 5:
				_localctx = new GtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(43); match(Identifier);
				setState(44); match(GT);
				setState(45); literal();
				}
				break;

			case 6:
				_localctx = new GtPContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(46); match(Identifier);
				setState(47); match(GTP);
				setState(48); match(Identifier);
				}
				break;

			case 7:
				_localctx = new LikeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(49); match(Identifier);
				setState(50); match(LIKE);
				setState(51); match(StringLiteral);
				}
				break;

			case 8:
				_localctx = new IlikeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(52); match(Identifier);
				setState(53); match(ILIKE);
				setState(54); match(StringLiteral);
				}
				break;

			case 9:
				_localctx = new LeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(55); match(Identifier);
				setState(56); match(LE);
				setState(57); literal();
				}
				break;

			case 10:
				_localctx = new LePContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(58); match(Identifier);
				setState(59); match(LEP);
				setState(60); match(Identifier);
				}
				break;

			case 11:
				_localctx = new LtContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(61); match(Identifier);
				setState(62); match(LT);
				setState(63); literal();
				}
				break;

			case 12:
				_localctx = new LtPContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(64); match(Identifier);
				setState(65); match(LTP);
				setState(66); match(Identifier);
				}
				break;

			case 13:
				_localctx = new NeContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(67); match(Identifier);
				setState(68); match(NE);
				setState(69); literal();
				}
				break;

			case 14:
				_localctx = new NePContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(70); match(Identifier);
				setState(71); match(NEP);
				setState(72); match(Identifier);
				}
				break;

			case 15:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(73); match(Identifier);
				setState(74); match(SEQ);
				setState(75); match(DecimalLiteral);
				}
				break;

			case 16:
				_localctx = new SgeContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(76); match(Identifier);
				setState(77); match(SGE);
				setState(78); match(DecimalLiteral);
				}
				break;

			case 17:
				_localctx = new SgtContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(79); match(Identifier);
				setState(80); match(SGT);
				setState(81); match(DecimalLiteral);
				}
				break;

			case 18:
				_localctx = new SleContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(82); match(Identifier);
				setState(83); match(SLE);
				setState(84); match(DecimalLiteral);
				}
				break;

			case 19:
				_localctx = new SltContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(85); match(Identifier);
				setState(86); match(SLT);
				setState(87); match(DecimalLiteral);
				}
				break;

			case 20:
				_localctx = new SneContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(88); match(Identifier);
				setState(89); match(SNE);
				setState(90); match(DecimalLiteral);
				}
				break;

			case 21:
				_localctx = new IsEmptyContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(91); match(Identifier);
				setState(92); match(ISEMPTY);
				}
				break;

			case 22:
				_localctx = new IsNotEmptyContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(93); match(Identifier);
				setState(94); match(ISNOTEMPTY);
				}
				break;

			case 23:
				_localctx = new IsNotNullContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(95); match(Identifier);
				setState(96); match(ISNOTNNULL);
				}
				break;

			case 24:
				_localctx = new IsNullContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(97); match(Identifier);
				setState(98); match(ISNULL);
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
		public TerminalNode BooleanLiteral() { return getToken(FilterParser.BooleanLiteral, 0); }
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
			setState(106);
			switch (_input.LA(1)) {
			case DecimalLiteral:
				_localctx = new DecimalContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(101); match(DecimalLiteral);
				}
				break;
			case FloatingPointLiteral:
				_localctx = new FloatContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(102); match(FloatingPointLiteral);
				}
				break;
			case StringLiteral:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(103); match(StringLiteral);
				}
				break;
			case BooleanLiteral:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(104); match(BooleanLiteral);
				}
				break;
			case DateLiteral:
				_localctx = new DateContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(105); match(DateLiteral);
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
		"\2\3&o\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\5\3\25\n\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\35\n\3\f\3\16\3 \13\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4f\n"+
		"\4\3\5\3\5\3\5\3\5\3\5\5\5m\n\5\3\5\2\6\2\4\6\b\2\2\u0089\2\n\3\2\2\2"+
		"\4\24\3\2\2\2\6e\3\2\2\2\bl\3\2\2\2\n\13\5\4\3\2\13\3\3\2\2\2\f\r\b\3"+
		"\1\2\r\16\7\37\2\2\16\25\5\4\3\2\17\25\5\6\4\2\20\21\7\3\2\2\21\22\5\4"+
		"\3\2\22\23\7\4\2\2\23\25\3\2\2\2\24\f\3\2\2\2\24\17\3\2\2\2\24\20\3\2"+
		"\2\2\25\36\3\2\2\2\26\27\6\3\2\3\27\30\7\35\2\2\30\35\5\4\3\2\31\32\6"+
		"\3\3\3\32\33\7\36\2\2\33\35\5\4\3\2\34\26\3\2\2\2\34\31\3\2\2\2\35 \3"+
		"\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37\5\3\2\2\2 \36\3\2\2\2!\"\7 \2\2"+
		"\"#\7\5\2\2#f\5\b\5\2$%\7 \2\2%&\7\6\2\2&f\7 \2\2\'(\7 \2\2()\7\7\2\2"+
		")f\5\b\5\2*+\7 \2\2+,\7\b\2\2,f\7 \2\2-.\7 \2\2./\7\t\2\2/f\5\b\5\2\60"+
		"\61\7 \2\2\61\62\7\n\2\2\62f\7 \2\2\63\64\7 \2\2\64\65\7\13\2\2\65f\7"+
		"%\2\2\66\67\7 \2\2\678\7\f\2\28f\7%\2\29:\7 \2\2:;\7\r\2\2;f\5\b\5\2<"+
		"=\7 \2\2=>\7\16\2\2>f\7 \2\2?@\7 \2\2@A\7\17\2\2Af\5\b\5\2BC\7 \2\2CD"+
		"\7\20\2\2Df\7 \2\2EF\7 \2\2FG\7\21\2\2Gf\5\b\5\2HI\7 \2\2IJ\7\22\2\2J"+
		"f\7 \2\2KL\7 \2\2LM\7\23\2\2Mf\7$\2\2NO\7 \2\2OP\7\24\2\2Pf\7$\2\2QR\7"+
		" \2\2RS\7\25\2\2Sf\7$\2\2TU\7 \2\2UV\7\26\2\2Vf\7$\2\2WX\7 \2\2XY\7\27"+
		"\2\2Yf\7$\2\2Z[\7 \2\2[\\\7\30\2\2\\f\7$\2\2]^\7 \2\2^f\7\31\2\2_`\7 "+
		"\2\2`f\7\32\2\2ab\7 \2\2bf\7\33\2\2cd\7 \2\2df\7\34\2\2e!\3\2\2\2e$\3"+
		"\2\2\2e\'\3\2\2\2e*\3\2\2\2e-\3\2\2\2e\60\3\2\2\2e\63\3\2\2\2e\66\3\2"+
		"\2\2e9\3\2\2\2e<\3\2\2\2e?\3\2\2\2eB\3\2\2\2eE\3\2\2\2eH\3\2\2\2eK\3\2"+
		"\2\2eN\3\2\2\2eQ\3\2\2\2eT\3\2\2\2eW\3\2\2\2eZ\3\2\2\2e]\3\2\2\2e_\3\2"+
		"\2\2ea\3\2\2\2ec\3\2\2\2f\7\3\2\2\2gm\7$\2\2hm\7#\2\2im\7%\2\2jm\7\"\2"+
		"\2km\7!\2\2lg\3\2\2\2lh\3\2\2\2li\3\2\2\2lj\3\2\2\2lk\3\2\2\2m\t\3\2\2"+
		"\2\7\24\34\36el";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}