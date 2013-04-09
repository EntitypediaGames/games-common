// Generated from Filter.g4 by ANTLR 4.0

package org.entitypedia.games.common.service.filter.parser;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface FilterVisitor<T> extends ParseTreeVisitor<T> {
	T visitCon(FilterParser.ConContext ctx);

	T visitNeg(FilterParser.NegContext ctx);

	T visitIsNull(FilterParser.IsNullContext ctx);

	T visitIsNotNull(FilterParser.IsNotNullContext ctx);

	T visitGeP(FilterParser.GePContext ctx);

	T visitSlt(FilterParser.SltContext ctx);

	T visitSgt(FilterParser.SgtContext ctx);

	T visitGe(FilterParser.GeContext ctx);

	T visitLt(FilterParser.LtContext ctx);

	T visitDate(FilterParser.DateContext ctx);

	T visitFloat(FilterParser.FloatContext ctx);

	T visitIsEmpty(FilterParser.IsEmptyContext ctx);

	T visitDis(FilterParser.DisContext ctx);

	T visitBoolean(FilterParser.BooleanContext ctx);

	T visitSne(FilterParser.SneContext ctx);

	T visitSle(FilterParser.SleContext ctx);

	T visitIsNotEmpty(FilterParser.IsNotEmptyContext ctx);

	T visitOp(FilterParser.OpContext ctx);

	T visitIlike(FilterParser.IlikeContext ctx);

	T visitNeP(FilterParser.NePContext ctx);

	T visitLtP(FilterParser.LtPContext ctx);

	T visitGtP(FilterParser.GtPContext ctx);

	T visitInit(FilterParser.InitContext ctx);

	T visitBooleanLiteral(FilterParser.BooleanLiteralContext ctx);

	T visitLike(FilterParser.LikeContext ctx);

	T visitDecimal(FilterParser.DecimalContext ctx);

	T visitLeP(FilterParser.LePContext ctx);

	T visitBkt(FilterParser.BktContext ctx);

	T visitLe(FilterParser.LeContext ctx);

	T visitString(FilterParser.StringContext ctx);

	T visitNe(FilterParser.NeContext ctx);

	T visitSeq(FilterParser.SeqContext ctx);

	T visitGt(FilterParser.GtContext ctx);

	T visitSge(FilterParser.SgeContext ctx);

	T visitEqP(FilterParser.EqPContext ctx);

	T visitEq(FilterParser.EqContext ctx);
}