package org.entitypedia.games.common.service.filter;

import org.entitypedia.games.common.service.filter.parser.FilterBaseVisitor;
import org.entitypedia.games.common.service.filter.parser.FilterParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterLiteralVisitor extends FilterBaseVisitor<Object> {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    @Override
    public Object visitDate(FilterParser.DateContext ctx) {
        try {
            return formatter.parse(ctx.getText().substring(1, ctx.getText().length() - 1));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object visitFloat(FilterParser.FloatContext ctx) {
        // all float fields in the models should be Double
        return Double.parseDouble(ctx.getText());
    }

    @Override
    public Object visitBoolean(FilterParser.BooleanContext ctx) {
        return Boolean.parseBoolean(ctx.getText());
    }

    @Override
    public Object visitDecimal(FilterParser.DecimalContext ctx) {
        // all integer fields in the models should be Long
        return Long.parseLong(ctx.getText());
    }

    @Override
    public Object visitString(FilterParser.StringContext ctx) {
        return unescape(ctx.getText());
    }

    public static String unescape(String string) {
        // remove surrounding ' and unescape \' -> ' and \\ -> \
        return string.substring(1, string.length() - 1).replace("\\'", "'").replace("\\\\", "\\");
    }
}
