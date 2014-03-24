package org.entitypedia.games.common.repository.hibernateimpl.filter;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.entitypedia.games.common.exceptions.OrderParsingException;
import org.entitypedia.games.common.repository.hibernateimpl.filter.parser.FilterLexer;
import org.entitypedia.games.common.repository.hibernateimpl.filter.parser.FilterParser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Parses filter expressions for Hibernate Criteria.
 * Example:
 * <pre>
 * (columnCount eq 21 and rowCount eq 21) and not published isNull and (title like 'space-rug' or title like 'home \'rug\'')
 * </pre>
 * In URL instead of space dash is used. To use dash itself, double it.
 * <pre>
 * (columnCount-eq-21-and-rowCount-eq-21)-and-not-published-isNull-and-(title-like-'space--rug'-or-title-like-'home-\'rug\'')
 * </pre>
 * <p>
 * Supports integer, float, boolean, date and string constants. Date constants should follow the ('yyyyMMdd') or ('yyyyMMddHHmmss') pattern.
 * String constants should be enclosed in single quotes. Any single quotes inside the string should be escaped with \.
 * </p>
 * <p>
 * Supported boolean operators: not, and, or.
 * </p>
 * <p>
 * Supported operators: eq, eqP, ge, geP, gt, gtP, like, ilike, le, leP, lt, ltP, ne, neP, seq, sge, sgt, sle, slt, sne, isEmpty, isNotEmpty, isNotNull, isNull.
 * For details on the operators see {@link org.hibernate.criterion.Restrictions} class. xxProperty operators are abbreviated as xxP. sizexx operators are abbreviated as sxx
 * </p>
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class FilterCriteriaParser {

    private static final ANTLRErrorListener errorListener = new ThrowingErrorListener();
    private static final Order[] EMPTY_ORDER = new Order[0];

    private FilterCriterionVisitor filterCriterionVisitor = new FilterCriterionVisitor();
    private Map<String, String> aliasMap;

    public Criterion parse(String filter) {
        ANTLRInputStream input = new ANTLRInputStream(unescape(filter));
        FilterLexer lexer = new FilterLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FilterParser parser = new FilterParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree parseTree = parser.init();
        return filterCriterionVisitor.visit(parseTree);
    }

    public Map<String, String> getAliasMap() {
        // the list of collected aliases need to be transformed
        // from crossword -> a0, crossword.wordClue -> a1
        // to crossword -> a0, a0.wordClue -> a1

        if (0 == filterCriterionVisitor.aliases.keySet().size()) {
            return Collections.emptyMap();
        } else {
            if (null == aliasMap) {
                aliasMap = new HashMap<>();
                for (Map.Entry<String, String> entry : filterCriterionVisitor.aliases.entrySet()) {
                    if (0 == StringUtils.countOccurrencesOf(entry.getKey(), ".")) {
                        aliasMap.put(entry.getKey(), entry.getValue());
                    } else {
                        // replacements;
                        int curOccurrences = StringUtils.countOccurrencesOf(entry.getKey(), ".");
                        for (Map.Entry<String, String> prevEntry : filterCriterionVisitor.aliases.entrySet()) {
                            if ((curOccurrences - 1) == StringUtils.countOccurrencesOf(prevEntry.getKey(), ".")) {
                                if (entry.getKey().contains(prevEntry.getKey() + ".")) {
                                    aliasMap.put(entry.getKey().replace(prevEntry.getKey() + ".", prevEntry.getValue() + "."), entry.getValue());
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            return aliasMap;
        }
    }

    public Order[] parseOrder(String order) {
        if (null == order) {
            return EMPTY_ORDER;
        }
        String[] orders = order.split("-");
        if (0 == order.length()) {
            throw new OrderParsingException("Order expression is empty");
        }
        Order[] result = new Order[orders.length];
        for (int i = 0; i < orders.length; i++) {
            String o = orders[i];
            if (o.length() < 2) {
                throw new OrderParsingException("Order expression part too short: " + o);
            }
            if (o.charAt(0) == 'D' || o.charAt(0) == 'A') {
                String name = o.substring(1);
                if (name.contains(".")) {
                    List<String> qName = new ArrayList<>();
                    for (String part : name.split("\\.")) {
                        if (null != part && !"".equals(part)) {
                            qName.add(part);
                        }
                    }
                    String curLevel = filterCriterionVisitor.extractAliases(qName);
                    name = name.replace(curLevel, filterCriterionVisitor.aliases.get(curLevel.substring(0, curLevel.length() - 1)) + ".");
                }
                switch (o.charAt(0)) {
                    case 'D': {
                        result[i] = Order.desc(name);
                        break;
                    }
                    case 'A': {
                        result[i] = Order.asc(name);
                        break;
                    }
                    default: {
                        throw new OrderParsingException("Order expression parts should start with D or A: " + o);
                    }
                }
            } else {
                throw new OrderParsingException("Order expression parts should start with D or A: " + o);
            }
        }
        return result;
    }

    public FilterCriterionVisitor getFilterCriterionVisitor() {
        return filterCriterionVisitor;
    }

    public void setFilterCriterionVisitor(FilterCriterionVisitor filterCriterionVisitor) {
        this.filterCriterionVisitor = filterCriterionVisitor;
    }

    private static String unescape(String filter) {
        return filter.replaceAll("--", "\t").replace('-', ' ').replace('\t', '-');
    }
}
