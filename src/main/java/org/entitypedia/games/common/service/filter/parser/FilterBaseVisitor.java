// Generated from Filter.g4 by ANTLR 4.0

package org.entitypedia.games.common.service.filter.parser;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.ParserRuleContext;

public class FilterBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements FilterVisitor<T> {
	@Override public T visitCon(FilterParser.ConContext ctx) { return visitChildren(ctx); }

	@Override public T visitNeg(FilterParser.NegContext ctx) { return visitChildren(ctx); }

	@Override public T visitIsNull(FilterParser.IsNullContext ctx) { return visitChildren(ctx); }

	@Override public T visitIsNotNull(FilterParser.IsNotNullContext ctx) { return visitChildren(ctx); }

	@Override public T visitGeP(FilterParser.GePContext ctx) { return visitChildren(ctx); }

	@Override public T visitSlt(FilterParser.SltContext ctx) { return visitChildren(ctx); }

	@Override public T visitSgt(FilterParser.SgtContext ctx) { return visitChildren(ctx); }

	@Override public T visitGe(FilterParser.GeContext ctx) { return visitChildren(ctx); }

	@Override public T visitLt(FilterParser.LtContext ctx) { return visitChildren(ctx); }

	@Override public T visitDate(FilterParser.DateContext ctx) { return visitChildren(ctx); }

	@Override public T visitFloat(FilterParser.FloatContext ctx) { return visitChildren(ctx); }

	@Override public T visitIsEmpty(FilterParser.IsEmptyContext ctx) { return visitChildren(ctx); }

	@Override public T visitDis(FilterParser.DisContext ctx) { return visitChildren(ctx); }

	@Override public T visitBoolean(FilterParser.BooleanContext ctx) { return visitChildren(ctx); }

	@Override public T visitSne(FilterParser.SneContext ctx) { return visitChildren(ctx); }

	@Override public T visitSle(FilterParser.SleContext ctx) { return visitChildren(ctx); }

	@Override public T visitIsNotEmpty(FilterParser.IsNotEmptyContext ctx) { return visitChildren(ctx); }

	@Override public T visitOp(FilterParser.OpContext ctx) { return visitChildren(ctx); }

	@Override public T visitIlike(FilterParser.IlikeContext ctx) { return visitChildren(ctx); }

	@Override public T visitNeP(FilterParser.NePContext ctx) { return visitChildren(ctx); }

	@Override public T visitLtP(FilterParser.LtPContext ctx) { return visitChildren(ctx); }

	@Override public T visitGtP(FilterParser.GtPContext ctx) { return visitChildren(ctx); }

	@Override public T visitInit(FilterParser.InitContext ctx) { return visitChildren(ctx); }

	@Override public T visitLike(FilterParser.LikeContext ctx) { return visitChildren(ctx); }

	@Override public T visitDecimal(FilterParser.DecimalContext ctx) { return visitChildren(ctx); }

	@Override public T visitLeP(FilterParser.LePContext ctx) { return visitChildren(ctx); }

	@Override public T visitBkt(FilterParser.BktContext ctx) { return visitChildren(ctx); }

	@Override public T visitLe(FilterParser.LeContext ctx) { return visitChildren(ctx); }

	@Override public T visitString(FilterParser.StringContext ctx) { return visitChildren(ctx); }

	@Override public T visitNe(FilterParser.NeContext ctx) { return visitChildren(ctx); }

	@Override public T visitSeq(FilterParser.SeqContext ctx) { return visitChildren(ctx); }

	@Override public T visitGt(FilterParser.GtContext ctx) { return visitChildren(ctx); }

	@Override public T visitSge(FilterParser.SgeContext ctx) { return visitChildren(ctx); }

	@Override public T visitEqP(FilterParser.EqPContext ctx) { return visitChildren(ctx); }

	@Override public T visitEq(FilterParser.EqContext ctx) { return visitChildren(ctx); }
}