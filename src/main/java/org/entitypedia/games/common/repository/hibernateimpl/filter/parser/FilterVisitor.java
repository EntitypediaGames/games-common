// Generated from Filter.g4 by ANTLR 4.5

package org.entitypedia.games.common.repository.hibernateimpl.filter.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FilterParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FilterVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FilterParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit(FilterParser.InitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeg(FilterParser.NegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code op}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(FilterParser.OpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code con}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCon(FilterParser.ConContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bkt}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBkt(FilterParser.BktContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dis}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDis(FilterParser.DisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq(FilterParser.EqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqP(FilterParser.EqPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ge}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGe(FilterParser.GeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code geP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeP(FilterParser.GePContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGt(FilterParser.GtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gtP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtP(FilterParser.GtPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code like}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLike(FilterParser.LikeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ilike}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIlike(FilterParser.IlikeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code le}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLe(FilterParser.LeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code leP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeP(FilterParser.LePContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLt(FilterParser.LtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ltP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtP(FilterParser.LtPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ne}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNe(FilterParser.NeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code neP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeP(FilterParser.NePContext ctx);
	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeq(FilterParser.SeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sge}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSge(FilterParser.SgeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sgt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSgt(FilterParser.SgtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sle}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSle(FilterParser.SleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code slt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlt(FilterParser.SltContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sne}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSne(FilterParser.SneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isEmpty}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsEmpty(FilterParser.IsEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isNotEmpty}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotEmpty(FilterParser.IsNotEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isNotNull}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotNull(FilterParser.IsNotNullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isNull}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNull(FilterParser.IsNullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code decimal}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(FilterParser.DecimalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code float}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(FilterParser.FloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(FilterParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(FilterParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code date}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(FilterParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link FilterParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(FilterParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link FilterParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(FilterParser.BooleanLiteralContext ctx);
}