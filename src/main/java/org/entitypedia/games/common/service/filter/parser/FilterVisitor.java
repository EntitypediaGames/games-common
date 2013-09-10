// Generated from Filter.g4 by ANTLR 4.1

package org.entitypedia.games.common.service.filter.parser;

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
	 * Visit a parse tree produced by {@link FilterParser#con}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCon(@NotNull FilterParser.ConContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#neg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeg(@NotNull FilterParser.NegContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#isNull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNull(@NotNull FilterParser.IsNullContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#isNotNull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotNull(@NotNull FilterParser.IsNotNullContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#geP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeP(@NotNull FilterParser.GePContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#slt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlt(@NotNull FilterParser.SltContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#sgt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSgt(@NotNull FilterParser.SgtContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#ge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGe(@NotNull FilterParser.GeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#lt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLt(@NotNull FilterParser.LtContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(@NotNull FilterParser.DateContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#float}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(@NotNull FilterParser.FloatContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#isEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsEmpty(@NotNull FilterParser.IsEmptyContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#dis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDis(@NotNull FilterParser.DisContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#boolean}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(@NotNull FilterParser.BooleanContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#sne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSne(@NotNull FilterParser.SneContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#sle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSle(@NotNull FilterParser.SleContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#isNotEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNotEmpty(@NotNull FilterParser.IsNotEmptyContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(@NotNull FilterParser.OpContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#ilike}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIlike(@NotNull FilterParser.IlikeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(@NotNull FilterParser.QualifiedNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#neP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeP(@NotNull FilterParser.NePContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#ltP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtP(@NotNull FilterParser.LtPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#gtP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtP(@NotNull FilterParser.GtPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit(@NotNull FilterParser.InitContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull FilterParser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#like}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLike(@NotNull FilterParser.LikeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#decimal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(@NotNull FilterParser.DecimalContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#leP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeP(@NotNull FilterParser.LePContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#bkt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBkt(@NotNull FilterParser.BktContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#le}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLe(@NotNull FilterParser.LeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(@NotNull FilterParser.StringContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#ne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNe(@NotNull FilterParser.NeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#seq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeq(@NotNull FilterParser.SeqContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#gt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGt(@NotNull FilterParser.GtContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#sge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSge(@NotNull FilterParser.SgeContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#eqP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqP(@NotNull FilterParser.EqPContext ctx);

	/**
	 * Visit a parse tree produced by {@link FilterParser#eq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq(@NotNull FilterParser.EqContext ctx);
}