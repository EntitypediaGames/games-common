package org.entitypedia.games.common.repository.hibernateimpl.filter;

import org.entitypedia.games.common.repository.hibernateimpl.filter.parser.FilterBaseVisitor;
import org.entitypedia.games.common.repository.hibernateimpl.filter.parser.FilterParser;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterLiteralVisitor extends FilterBaseVisitor<Object> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat formatterLong = new SimpleDateFormat("yyyyMMddHHmmss");
    // the class filter is written for
    private final Class targetType;

    public FilterLiteralVisitor(Class targetType) {
        this.targetType = targetType;
    }

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
        // get data type from the first qualified name in the parent
        if (null != targetType && null != ctx.getParent()) {
            FilterParser.QualifiedNameContext qName = ctx.getParent().getRuleContext(FilterParser.QualifiedNameContext.class, 0);
            Field f = getFieldFromClass(targetType, qName);
            if (null != f) {
                if (Float.class.equals(f.getType())) {
                    return Float.parseFloat(ctx.getText());
                } else if (Double.class.equals(f.getType())) {
                    return Double.parseDouble(ctx.getText());
                }
            }
        }
        return Double.parseDouble(ctx.getText());
    }

    @Override
    public Object visitBoolean(FilterParser.BooleanContext ctx) {
        return Boolean.parseBoolean(ctx.getText());
    }

    @Override
    public Object visitDecimal(FilterParser.DecimalContext ctx) {
        // get data type from the first qualified name in the parent
        if (null != targetType && null != ctx.getParent()) {
            FilterParser.QualifiedNameContext qName = ctx.getParent().getRuleContext(FilterParser.QualifiedNameContext.class, 0);
            Field f = getFieldFromClass(targetType, qName);
            if (null != f) {
                if (Integer.class.equals(f.getType())) {
                    return Integer.parseInt(ctx.getText());
                } else if (Long.class.equals(f.getType())) {
                    return Long.parseLong(ctx.getText());
                } else if (Short.class.equals(f.getType())) {
                    return Short.parseShort(ctx.getText());
                } else if (Byte.class.equals(f.getType())) {
                    return Byte.parseByte(ctx.getText());
                }
            }
        }
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

    private static Field getFieldFromClass(final Class type, final FilterParser.QualifiedNameContext ctx) {
        Class currentClass = type;
        Field result = null;
        int i = 0;
        while (i < ctx.Identifier().size() && null != currentClass) {
            List<Field> currentFields = new ArrayList<>();
            collectAllFields(currentFields, currentClass);
            result = findField(currentFields, ctx.Identifier(i).getText());
            if (null != result) {
                currentClass = result.getType();
            }
            i++;
        }

        return result;
    }

    private static void collectAllFields(List<Field> fields, Class<?> type) {
        Collections.addAll(fields, type.getDeclaredFields());

        if (null != type.getSuperclass()) {
            collectAllFields(fields, type.getSuperclass());
        }
    }

    private static Field findField(List<Field> fields, String name) {
        Field result = null;
        for (Field f : fields) {
            if (name.equals(f.getName())) {
                result = f;
                break;
            }
        }
        return result;
    }
}
