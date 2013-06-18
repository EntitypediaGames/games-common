package org.entitypedia.games.common.service.filter;

import org.entitypedia.games.common.service.filter.parser.FilterBaseVisitor;
import org.entitypedia.games.common.service.filter.parser.FilterParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterLiteralVisitor extends FilterBaseVisitor<Object> {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat formatterLong = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public Object visitDate(FilterParser.DateContext ctx) {
        try {
            String dateString = ctx.getText().substring(1, ctx.getText().length() - 1);
            Date result;
            if (8 == dateString.length()) {
                result = formatter.parse(dateString);
            } else {
                result = formatterLong.parse(dateString);
            }
            return result;
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
