// Generated from Filter.g4 by ANTLR 4.4

package org.entitypedia.games.common.repository.hibernateimpl.filter.parser;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FilterParser}.
 *
 * @param <Result> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FilterVisitor<Result> extends ParseTreeVisitor<Result> {
	/**
	 * Visit a parse tree produced by the {@code con}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitCon(@NotNull FilterParser.ConContext ctx);

	/**
	 * Visit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNeg(@NotNull FilterParser.NegContext ctx);

	/**
	 * Visit a parse tree produced by the {@code isNotNull}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIsNotNull(@NotNull FilterParser.IsNotNullContext ctx);

	/**
	 * Visit a parse tree produced by the {@code isNull}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIsNull(@NotNull FilterParser.IsNullContext ctx);

	/**
	 * Visit a parse tree produced by the {@code geP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGeP(@NotNull FilterParser.GePContext ctx);

	/**
	 * Visit a parse tree produced by the {@code slt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSlt(@NotNull FilterParser.SltContext ctx);

	/**
	 * Visit a parse tree produced by the {@code sgt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSgt(@NotNull FilterParser.SgtContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitExpr(@NotNull FilterParser.ExprContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ge}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGe(@NotNull FilterParser.GeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code lt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLt(@NotNull FilterParser.LtContext ctx);

	/**
	 * Visit a parse tree produced by the {@code date}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDate(@NotNull FilterParser.DateContext ctx);

	/**
	 * Visit a parse tree produced by the {@code float}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitFloat(@NotNull FilterParser.FloatContext ctx);

	/**
	 * Visit a parse tree produced by the {@code isEmpty}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIsEmpty(@NotNull FilterParser.IsEmptyContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitOper(@NotNull FilterParser.OperContext ctx);

	/**
	 * Visit a parse tree produced by the {@code dis}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDis(@NotNull FilterParser.DisContext ctx);

	/**
	 * Visit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBoolean(@NotNull FilterParser.BooleanContext ctx);

	/**
	 * Visit a parse tree produced by the {@code sne}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSne(@NotNull FilterParser.SneContext ctx);

	/**
	 * Visit a parse tree produced by the {@code sle}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSle(@NotNull FilterParser.SleContext ctx);

	/**
	 * Visit a parse tree produced by the {@code isNotEmpty}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIsNotEmpty(@NotNull FilterParser.IsNotEmptyContext ctx);

	/**
	 * Visit a parse tree produced by the {@code op}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitOp(@NotNull FilterParser.OpContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ilike}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitIlike(@NotNull FilterParser.IlikeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitQualifiedName(@NotNull FilterParser.QualifiedNameContext ctx);

	/**
	 * Visit a parse tree produced by the {@code neP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNeP(@NotNull FilterParser.NePContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ltP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLtP(@NotNull FilterParser.LtPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitInit(@NotNull FilterParser.InitContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gtP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGtP(@NotNull FilterParser.GtPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBooleanLiteral(@NotNull FilterParser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code like}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLike(@NotNull FilterParser.LikeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code decimal}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitDecimal(@NotNull FilterParser.DecimalContext ctx);

	/**
	 * Visit a parse tree produced by the {@code leP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLeP(@NotNull FilterParser.LePContext ctx);

	/**
	 * Visit a parse tree produced by the {@code bkt}
	 * labeled alternative in {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitBkt(@NotNull FilterParser.BktContext ctx);

	/**
	 * Visit a parse tree produced by the {@code le}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLe(@NotNull FilterParser.LeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitString(@NotNull FilterParser.StringContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ne}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitNe(@NotNull FilterParser.NeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code seq}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSeq(@NotNull FilterParser.SeqContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gt}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitGt(@NotNull FilterParser.GtContext ctx);

	/**
	 * Visit a parse tree produced by the {@code sge}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitSge(@NotNull FilterParser.SgeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code eqP}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitEqP(@NotNull FilterParser.EqPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitLiteral(@NotNull FilterParser.LiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link FilterParser#oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	Result visitEq(@NotNull FilterParser.EqContext ctx);
}