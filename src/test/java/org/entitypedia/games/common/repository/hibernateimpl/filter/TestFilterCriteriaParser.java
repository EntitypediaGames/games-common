package org.entitypedia.games.common.repository.hibernateimpl.filter;

import static org.junit.Assert.*;

import org.entitypedia.games.common.exceptions.FilterParsingException;
import org.entitypedia.games.common.model.Page;
import org.entitypedia.games.common.model.ResultsPage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RunWith(JUnit4.class)
public class TestFilterCriteriaParser {

    private Object getField(Object instance, String name) throws Exception {
        Class c = instance.getClass();
        Field f = c.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(instance);
    }

    @Test
    public void testSimpleEq() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel=21", result.toString());
    }

    @Test
    public void testSimpleEqP() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eqP nameName", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel=nameName", result.toString());
    }

    @Test
    public void testSimpleGe() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel ge 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel>=21", result.toString());
    }

    @Test
    public void testSimpleGeP() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel geP nameName", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel>=nameName", result.toString());
    }

    @Test
    public void testSimpleLike() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel like 'abc'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel like abc", result.toString());
    }

    @Test
    public void testLikePercent() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel like 'abc%'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel like abc%", result.toString());
    }

    @Test
    public void testLikeQuote() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel like 'dell\\'Angelo'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel like dell'Angelo", result.toString());
    }

    @Test
    public void testSizeEQ() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel sizeEq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel.size=21", result.toString());
    }

    @Test
    public void testSimpleAnd() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "columnCount eq 21 and rowCount eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("columnCount=21 and rowCount=21", result.toString());
    }

    @Test
    public void testSimpleOr() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "columnCount eq 21 or rowCount eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("columnCount=21 or rowCount=21", result.toString());
    }

    @Test
    public void testSimpleNot() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "not columnCount eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("not columnCount=21", result.toString());
    }

    @Test
    public void testPrecedence() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "colCount eq 21 and columnCount eq 21 or rowCount eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("colCount=21 and columnCount=21 or rowCount=21", result.toString());
    }

    @Test
    public void testPrecedenceBrackets() throws Exception {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "colCount eq 21 and (columnCount eq 21 or rowCount eq 21)", null);
        Criterion result = filterCriteriaParser.parse();
        assertTrue(result instanceof LogicalExpression);
        LogicalExpression and = (LogicalExpression) result;
        assertEquals("and", and.getOp());
        assertTrue(getField(and, "lhs") instanceof SimpleExpression);
        assertEquals("colCount=21", getField(and, "lhs").toString());
        assertTrue(getField(and, "rhs") instanceof LogicalExpression);
        LogicalExpression rhs = (LogicalExpression) getField(and, "rhs");
        assertEquals("columnCount=21 or rowCount=21", rhs.toString());
    }

    @Test
    public void testSimpleEqFloat() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq 2.1", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel=2.1", result.toString());
    }

    @Test
    public void testSimpleEqString() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq 'abc'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel=abc", result.toString());
    }

    @Test
    public void testSimpleEqBool() {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq true", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("nameCamel=true", result.toString());
    }

    @Test
    public void testSimpleEqDate() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq '20130101'", null);
        Criterion result = filterCriteriaParser.parse();
        Date d = (new SimpleDateFormat("yyyyMMdd")).parse("20130101");
        assertEquals("nameCamel=" + d.toString(), result.toString());
    }

    @Test
    public void testSimpleEqDateTime() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "nameCamel eq '20130101010100'", null);
        Criterion result = filterCriteriaParser.parse();
        Date d = (new SimpleDateFormat("yyyyMMddHHmmss")).parse("20130101010100");
        assertEquals("nameCamel=" + d.toString(), result.toString());
    }

    @Test
    public void testQualifiedOneLevel() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.answer eq 'bucomil'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a0.answer=bucomil", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
    }

    @Test
    public void testOrder() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "ArowCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(1, result.length);
        assertEquals("rowCount asc", result[0].toString());
    }

    @Test
    public void testOrderQualified() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "Alayout.rowCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(1, result.length);
        assertEquals("a0.rowCount asc", result[0].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(1, aliasMap.size());
        assertEquals("a0", aliasMap.get("layout"));
    }

    @Test
    public void testOrderQualifiedDouble() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "Alayout.rowCount-Alayout.columnCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(2, result.length);
        assertEquals("a0.rowCount asc", result[0].toString());
        assertEquals("a0.columnCount asc", result[1].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(1, aliasMap.size());
        assertEquals("a0", aliasMap.get("layout"));
    }

    @Test
    public void testOrderQualifiedDoubleDifferent() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "Alayout.rowCount-Acrossword.columnCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(2, result.length);
        assertEquals("a0.rowCount asc", result[0].toString());
        assertEquals("a1.columnCount asc", result[1].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(2, aliasMap.size());
        assertEquals("a0", aliasMap.get("layout"));
        assertEquals("a1", aliasMap.get("crossword"));
    }

    @Test
    public void testOrderQualifiedTriple() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "Acrossword.layout.rowCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(1, result.length);
        assertEquals("a1.rowCount asc", result[0].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(2, aliasMap.size());
        assertEquals("a0", aliasMap.get("crossword"));
        assertEquals("a1", aliasMap.get("a0.layout"));
    }

    @Test
    public void testOrderQualifiedTripleFilterOrder() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "crossword.layout.rowCount eq 21", "Acrossword.layout.rowCount");
        Criterion c = filterCriteriaParser.parse();
        assertEquals("a1.rowCount=21", c.toString());
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(1, result.length);
        assertEquals("a1.rowCount asc", result[0].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(2, aliasMap.size());
        assertEquals("a0", aliasMap.get("crossword"));
        assertEquals("a1", aliasMap.get("a0.layout"));
    }

    @Test
    public void testOrderQualifiedTripleFilterOrderDifferen() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "crossword.layout.rowCount eq 21", "Agame.crossword.layout.id");
        Criterion c = filterCriteriaParser.parse();
        assertEquals("a1.rowCount=21", c.toString());
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(1, result.length);
        assertEquals("a4.id asc", result[0].toString());

        Map<String, String> aliasMap = filterCriteriaParser.getAliasMap();
        assertEquals(5, aliasMap.size());
        assertEquals("a0", aliasMap.get("crossword"));
        assertEquals("a1", aliasMap.get("a0.layout"));
        assertEquals("a2", aliasMap.get("game"));
        assertEquals("a3", aliasMap.get("a2.crossword"));
        assertEquals("a4", aliasMap.get("a3.layout"));
    }

    @Test
    public void testOrderDouble() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, null, "ArowCount-AcolumnCount");
        Order[] result = filterCriteriaParser.parseOrder();
        assertEquals(2, result.length);
        assertEquals("rowCount asc", result[0].toString());
        assertEquals("columnCount asc", result[1].toString());
    }

    @Test
    public void testQualifiedTwoLevels() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.wordClue.answer eq 'bucomil'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a1.answer=bucomil", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
        assertTrue(aliases.containsKey("a0.wordClue"));
        assertEquals("a1", aliases.get("a0.wordClue"));
    }

    @Test
    public void testQualifiedThreeLevels() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.wordClue.answer.length eq 'bucomil'", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a2.length=bucomil", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
        assertTrue(aliases.containsKey("a0.wordClue"));
        assertEquals("a1", aliases.get("a0.wordClue"));
        assertTrue(aliases.containsKey("a1.answer"));
        assertEquals("a2", aliases.get("a1.answer"));
    }

    @Test
    public void testQualifiedTwoLevelsTwice() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.wordClue.answer eq 21 or clues.wordClue.answer eq 22", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a1.answer=21 or a1.answer=22", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
        assertTrue(aliases.containsKey("a0.wordClue"));
        assertEquals("a1", aliases.get("a0.wordClue"));
    }

    @Test
    public void testQualifiedOneLevelsTwiceDifferent() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.wordClue eq 21 or answers.length eq 22", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a0.wordClue=21 or a1.length=22", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
        assertTrue(aliases.containsKey("answers"));
        assertEquals("a1", aliases.get("answers"));
    }

    @Test
    public void testQualifiedTwoLevelsTwiceDifferent() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, "clues.wordClue.answer eq 21 or clues.wordCamel.answer eq 22", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a1.answer=21 or a2.answer=22", result.toString());
        Map<String, String> aliases = filterCriteriaParser.getAliasMap();
        assertTrue(aliases.containsKey("clues"));
        assertEquals("a0", aliases.get("clues"));
        assertTrue(aliases.containsKey("a0.wordClue"));
        assertEquals("a1", aliases.get("a0.wordClue"));
        assertTrue(aliases.containsKey("a0.wordCamel"));
        assertEquals("a2", aliases.get("a0.wordCamel"));
    }

    @Test
    public void testPageFilterPageNo() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(Page.class, "pageNo eq 21", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("pageNo=21", result.toString());
    }

    @Test
    public void testPageFilterPageSize() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(Page.class, "pageSize ge 1", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("pageSize>=1", result.toString());
    }

    @Test
    public void testResultsPageFilterOverallCount() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(ResultsPage.class, "overallCount eq 1", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("overallCount=1", result.toString());
    }

    @Test
    public void testLayoutUserId() throws ParseException {
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(TestLayout.class, "user.id eq 1", null);
        Criterion result = filterCriteriaParser.parse();
        assertEquals("a0.id=1", result.toString());
    }

    @Test(expected = FilterParsingException.class)
    public void testParsingLeftException() {
        String badSyntax = "published eq true and (creationTime lXXXt '20130111')";
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, badSyntax, null);
        filterCriteriaParser.parse();
    }

    @Test(expected = FilterParsingException.class)
    public void testParsingRigthException() {
        String badSyntax = "(creationTime lXXXt '20130111') and published eq true";
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, badSyntax, null);
        filterCriteriaParser.parse();
    }

    @Test(expected = FilterParsingException.class)
    public void testSimpleParsingException() {
        String badSyntax = "published eXXXq true";
        FilterCriteriaParser filterCriteriaParser = new FilterCriteriaParser(null, badSyntax, null);
        filterCriteriaParser.parse();
    }

    private final static class TestUser {
        private Integer id;
    }

    private final static class TestLayout {
        private TestUser user;
    }
}