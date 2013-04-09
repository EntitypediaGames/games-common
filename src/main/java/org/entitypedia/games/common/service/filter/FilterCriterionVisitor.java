package org.entitypedia.games.common.service.filter;

import org.entitypedia.games.common.service.filter.parser.FilterBaseVisitor;
import org.entitypedia.games.common.service.filter.parser.FilterParser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterCriterionVisitor extends FilterBaseVisitor<Criterion> {

    private static final FilterLiteralVisitor literalVisitor = new FilterLiteralVisitor();

    @Override
    public Criterion visitCon(FilterParser.ConContext ctx) {
        return Restrictions.and(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Criterion visitDis(FilterParser.DisContext ctx) {
        return Restrictions.or(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Criterion visitNeg(FilterParser.NegContext ctx) {
        return Restrictions.not(visit(ctx.expr()));
    }

    @Override
    public Criterion visitIsNull(FilterParser.IsNullContext ctx) {
        return Restrictions.isNull(ctx.Identifier().getText());
    }

    @Override
    public Criterion visitIsNotNull(FilterParser.IsNotNullContext ctx) {
        return Restrictions.isNotNull(ctx.Identifier().getText());
    }

    @Override
    public Criterion visitGeP(FilterParser.GePContext ctx) {
        return Restrictions.geProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitSlt(FilterParser.SltContext ctx) {
        return Restrictions.sizeLt(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitSgt(FilterParser.SgtContext ctx) {
        return Restrictions.sizeGt(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitGe(FilterParser.GeContext ctx) {
        return Restrictions.ge(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitLt(FilterParser.LtContext ctx) {
        return Restrictions.lt(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitIsEmpty(FilterParser.IsEmptyContext ctx) {
        return Restrictions.isEmpty(ctx.Identifier().getText());
    }

    @Override
    public Criterion visitSne(FilterParser.SneContext ctx) {
        return Restrictions.sizeNe(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitSle(FilterParser.SleContext ctx) {
        return Restrictions.sizeLe(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitIsNotEmpty(FilterParser.IsNotEmptyContext ctx) {
        return Restrictions.isNotEmpty(ctx.Identifier().getText());
    }

    @Override
    public Criterion visitIlike(FilterParser.IlikeContext ctx) {
        return Restrictions.ilike(ctx.Identifier().getText(), FilterLiteralVisitor.unescape(ctx.StringLiteral().getText()));
    }

    @Override
    public Criterion visitNeP(FilterParser.NePContext ctx) {
        return Restrictions.neProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitLtP(FilterParser.LtPContext ctx) {
        return Restrictions.ltProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitGtP(FilterParser.GtPContext ctx) {
        return Restrictions.gtProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitLeP(FilterParser.LePContext ctx) {
        return Restrictions.leProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitLike(FilterParser.LikeContext ctx) {
        return Restrictions.like(ctx.Identifier().getText(), FilterLiteralVisitor.unescape(ctx.StringLiteral().getText()));
    }

    @Override
    public Criterion visitLe(FilterParser.LeContext ctx) {
        return Restrictions.le(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitNe(FilterParser.NeContext ctx) {
        return Restrictions.ne(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitSeq(FilterParser.SeqContext ctx) {
        return Restrictions.sizeEq(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitGt(FilterParser.GtContext ctx) {
        return Restrictions.gt(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitSge(FilterParser.SgeContext ctx) {
        return Restrictions.sizeGe(ctx.Identifier().getText(), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitEqP(FilterParser.EqPContext ctx) {
        return Restrictions.eqProperty(ctx.Identifier(0).getText(), ctx.Identifier(1).getText());
    }

    @Override
    public Criterion visitEq(FilterParser.EqContext ctx) {
        return Restrictions.eq(ctx.Identifier().getText(), literalVisitor.visit(ctx.literal()));
    }
}