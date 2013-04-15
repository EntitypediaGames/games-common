package org.entitypedia.games.common.service.filter;

import org.entitypedia.games.common.service.filter.parser.FilterBaseVisitor;
import org.entitypedia.games.common.service.filter.parser.FilterParser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterCriterionVisitor extends FilterBaseVisitor<Criterion> {

    private static final FilterLiteralVisitor literalVisitor = new FilterLiteralVisitor();
    private static final class AliasComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Integer o1cnt = StringUtils.countOccurrencesOf(o1, ".");
            Integer o2cnt = StringUtils.countOccurrencesOf(o2, ".");
            if (0 == o1cnt.compareTo(o2cnt)) {
                return o1.compareTo(o2);
            } else {
                return o1cnt.compareTo(o2cnt);
            }
        }
    }
    public static final AliasComparator aliasComparator = new AliasComparator();
    public SortedMap<String, String> aliases = new TreeMap<String, String>(aliasComparator);

    public String processQualifiedName(FilterParser.QualifiedNameContext ctx) {
        // names like crossword.wordClues.across require aliases to be added to Criteria
        // here the list of aliases is being collected
        String result = ctx.getText();
        if (1 < ctx.getChildCount()) {
            String curLevel = "";
            for (int i = 0; i < (ctx.Identifier().size() - 1); i++) {
                curLevel = curLevel + ctx.Identifier(i).getText();
                if (!aliases.containsKey(curLevel)) {
                    aliases.put(curLevel, "a" + Integer.toString(aliases.keySet().size()));
                }
                curLevel = curLevel + ".";
            }
            result = ctx.getText().replace(curLevel, aliases.get(curLevel.substring(0, curLevel.length() - 1)) + ".");
        }
        return result;
    }

    @Override
    public Criterion visitBkt(FilterParser.BktContext ctx) {
        return visit(ctx.expr());
    }

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
        return Restrictions.isNull(processQualifiedName(ctx.qualifiedName()));
    }

    @Override
    public Criterion visitIsNotNull(FilterParser.IsNotNullContext ctx) {
        return Restrictions.isNotNull(processQualifiedName(ctx.qualifiedName()));
    }

    @Override
    public Criterion visitGeP(FilterParser.GePContext ctx) {
        return Restrictions.geProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitSlt(FilterParser.SltContext ctx) {
        return Restrictions.sizeLt(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitSgt(FilterParser.SgtContext ctx) {
        return Restrictions.sizeGt(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitGe(FilterParser.GeContext ctx) {
        return Restrictions.ge(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitLt(FilterParser.LtContext ctx) {
        return Restrictions.lt(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitIsEmpty(FilterParser.IsEmptyContext ctx) {
        return Restrictions.isEmpty(processQualifiedName(ctx.qualifiedName()));
    }

    @Override
    public Criterion visitSne(FilterParser.SneContext ctx) {
        return Restrictions.sizeNe(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitSle(FilterParser.SleContext ctx) {
        return Restrictions.sizeLe(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitIsNotEmpty(FilterParser.IsNotEmptyContext ctx) {
        return Restrictions.isNotEmpty(processQualifiedName(ctx.qualifiedName()));
    }

    @Override
    public Criterion visitIlike(FilterParser.IlikeContext ctx) {
        return Restrictions.ilike(processQualifiedName(ctx.qualifiedName()), FilterLiteralVisitor.unescape(ctx.StringLiteral().getText()));
    }

    @Override
    public Criterion visitNeP(FilterParser.NePContext ctx) {
        return Restrictions.neProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitLtP(FilterParser.LtPContext ctx) {
        return Restrictions.ltProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitGtP(FilterParser.GtPContext ctx) {
        return Restrictions.gtProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitLeP(FilterParser.LePContext ctx) {
        return Restrictions.leProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitLike(FilterParser.LikeContext ctx) {
        return Restrictions.like(processQualifiedName(ctx.qualifiedName()), FilterLiteralVisitor.unescape(ctx.StringLiteral().getText()));
    }

    @Override
    public Criterion visitLe(FilterParser.LeContext ctx) {
        return Restrictions.le(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitNe(FilterParser.NeContext ctx) {
        return Restrictions.ne(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitSeq(FilterParser.SeqContext ctx) {
        return Restrictions.sizeEq(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitGt(FilterParser.GtContext ctx) {
        return Restrictions.gt(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }

    @Override
    public Criterion visitSge(FilterParser.SgeContext ctx) {
        return Restrictions.sizeGe(processQualifiedName(ctx.qualifiedName()), Integer.parseInt(ctx.DecimalLiteral().getText()));
    }

    @Override
    public Criterion visitEqP(FilterParser.EqPContext ctx) {
        return Restrictions.eqProperty(processQualifiedName(ctx.qualifiedName(0)), processQualifiedName(ctx.qualifiedName(1)));
    }

    @Override
    public Criterion visitEq(FilterParser.EqContext ctx) {
        return Restrictions.eq(processQualifiedName(ctx.qualifiedName()), literalVisitor.visit(ctx.literal()));
    }
}