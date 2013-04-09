// Generated from Filter.g4 by ANTLR 4.0

package org.entitypedia.games.common.service.filter.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FilterLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, LRB=3, RRB=4, EQ=5, EQP=6, GE=7, GEP=8, GT=9, GTP=10, 
		LIKE=11, ILIKE=12, LE=13, LEP=14, LT=15, LTP=16, NE=17, NEP=18, SEQ=19, 
		SGE=20, SGT=21, SLE=22, SLT=23, SNE=24, ISEMPTY=25, ISNOTEMPTY=26, ISNOTNNULL=27, 
		ISNULL=28, AND=29, OR=30, NOT=31, Identifier=32, DateLiteral=33, FloatingPointLiteral=34, 
		DecimalLiteral=35, StringLiteral=36, WS=37;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'false'", "'true'", "'('", "')'", "'eq'", "'eqP'", "'ge'", "'geP'", "'gt'", 
		"'gtP'", "'like'", "'ilike'", "'le'", "'leP'", "'lt'", "'ltP'", "'ne'", 
		"'neP'", "'sizeEq'", "'sizeGe'", "'sizeGt'", "'sizeLe'", "'sizeLt'", "'sizeNe'", 
		"'isEmpty'", "'isNotEmpty'", "'isNotNull'", "'isNull'", "'and'", "'or'", 
		"'not'", "Identifier", "DateLiteral", "FloatingPointLiteral", "DecimalLiteral", 
		"StringLiteral", "WS"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "LRB", "RRB", "EQ", "EQP", "GE", "GEP", "GT", "GTP", "LIKE", 
		"ILIKE", "LE", "LEP", "LT", "LTP", "NE", "NEP", "SEQ", "SGE", "SGT", "SLE", 
		"SLT", "SNE", "ISEMPTY", "ISNOTEMPTY", "ISNOTNNULL", "ISNULL", "AND", 
		"OR", "NOT", "Identifier", "DateLiteral", "FloatingPointLiteral", "DecimalLiteral", 
		"StringLiteral", "EscapeSequence", "WS"
	};


	public FilterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Filter.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 37: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\'\u0134\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36"+
		"\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\7!\u00ef\n!\f!\16!\u00f2\13!\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u00fd\n\"\3\"\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\5\"\u0107\n\"\3\"\3\"\3#\6#\u010c\n#\r#\16#\u010d\3#\3#\7#"+
		"\u0112\n#\f#\16#\u0115\13#\3$\3$\3$\7$\u011a\n$\f$\16$\u011d\13$\5$\u011f"+
		"\n$\3%\3%\3%\7%\u0124\n%\f%\16%\u0127\13%\3%\3%\3&\3&\3&\3\'\6\'\u012f"+
		"\n\'\r\'\16\'\u0130\3\'\3\'\2(\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17"+
		"\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22"+
		"\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1"+
		"\67\35\19\36\1;\37\1= \1?!\1A\"\1C#\1E$\1G%\1I&\1K\2\1M\'\2\3\2\7\4C\\"+
		"c|\5\62;C\\c|\4))^^\4))^^\3\"\"\u013e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2M\3\2\2\2\3O\3\2\2"+
		"\2\5U\3\2\2\2\7Z\3\2\2\2\t\\\3\2\2\2\13^\3\2\2\2\ra\3\2\2\2\17e\3\2\2"+
		"\2\21h\3\2\2\2\23l\3\2\2\2\25o\3\2\2\2\27s\3\2\2\2\31x\3\2\2\2\33~\3\2"+
		"\2\2\35\u0081\3\2\2\2\37\u0085\3\2\2\2!\u0088\3\2\2\2#\u008c\3\2\2\2%"+
		"\u008f\3\2\2\2\'\u0093\3\2\2\2)\u009a\3\2\2\2+\u00a1\3\2\2\2-\u00a8\3"+
		"\2\2\2/\u00af\3\2\2\2\61\u00b6\3\2\2\2\63\u00bd\3\2\2\2\65\u00c5\3\2\2"+
		"\2\67\u00d0\3\2\2\29\u00da\3\2\2\2;\u00e1\3\2\2\2=\u00e5\3\2\2\2?\u00e8"+
		"\3\2\2\2A\u00ec\3\2\2\2C\u00f3\3\2\2\2E\u010b\3\2\2\2G\u011e\3\2\2\2I"+
		"\u0120\3\2\2\2K\u012a\3\2\2\2M\u012e\3\2\2\2OP\7h\2\2PQ\7c\2\2QR\7n\2"+
		"\2RS\7u\2\2ST\7g\2\2T\4\3\2\2\2UV\7v\2\2VW\7t\2\2WX\7w\2\2XY\7g\2\2Y\6"+
		"\3\2\2\2Z[\7*\2\2[\b\3\2\2\2\\]\7+\2\2]\n\3\2\2\2^_\7g\2\2_`\7s\2\2`\f"+
		"\3\2\2\2ab\7g\2\2bc\7s\2\2cd\7R\2\2d\16\3\2\2\2ef\7i\2\2fg\7g\2\2g\20"+
		"\3\2\2\2hi\7i\2\2ij\7g\2\2jk\7R\2\2k\22\3\2\2\2lm\7i\2\2mn\7v\2\2n\24"+
		"\3\2\2\2op\7i\2\2pq\7v\2\2qr\7R\2\2r\26\3\2\2\2st\7n\2\2tu\7k\2\2uv\7"+
		"m\2\2vw\7g\2\2w\30\3\2\2\2xy\7k\2\2yz\7n\2\2z{\7k\2\2{|\7m\2\2|}\7g\2"+
		"\2}\32\3\2\2\2~\177\7n\2\2\177\u0080\7g\2\2\u0080\34\3\2\2\2\u0081\u0082"+
		"\7n\2\2\u0082\u0083\7g\2\2\u0083\u0084\7R\2\2\u0084\36\3\2\2\2\u0085\u0086"+
		"\7n\2\2\u0086\u0087\7v\2\2\u0087 \3\2\2\2\u0088\u0089\7n\2\2\u0089\u008a"+
		"\7v\2\2\u008a\u008b\7R\2\2\u008b\"\3\2\2\2\u008c\u008d\7p\2\2\u008d\u008e"+
		"\7g\2\2\u008e$\3\2\2\2\u008f\u0090\7p\2\2\u0090\u0091\7g\2\2\u0091\u0092"+
		"\7R\2\2\u0092&\3\2\2\2\u0093\u0094\7u\2\2\u0094\u0095\7k\2\2\u0095\u0096"+
		"\7|\2\2\u0096\u0097\7g\2\2\u0097\u0098\7G\2\2\u0098\u0099\7s\2\2\u0099"+
		"(\3\2\2\2\u009a\u009b\7u\2\2\u009b\u009c\7k\2\2\u009c\u009d\7|\2\2\u009d"+
		"\u009e\7g\2\2\u009e\u009f\7I\2\2\u009f\u00a0\7g\2\2\u00a0*\3\2\2\2\u00a1"+
		"\u00a2\7u\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7|\2\2\u00a4\u00a5\7g\2\2"+
		"\u00a5\u00a6\7I\2\2\u00a6\u00a7\7v\2\2\u00a7,\3\2\2\2\u00a8\u00a9\7u\2"+
		"\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7|\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad"+
		"\7N\2\2\u00ad\u00ae\7g\2\2\u00ae.\3\2\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1"+
		"\7k\2\2\u00b1\u00b2\7|\2\2\u00b2\u00b3\7g\2\2\u00b3\u00b4\7N\2\2\u00b4"+
		"\u00b5\7v\2\2\u00b5\60\3\2\2\2\u00b6\u00b7\7u\2\2\u00b7\u00b8\7k\2\2\u00b8"+
		"\u00b9\7|\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb\7P\2\2\u00bb\u00bc\7g\2\2"+
		"\u00bc\62\3\2\2\2\u00bd\u00be\7k\2\2\u00be\u00bf\7u\2\2\u00bf\u00c0\7"+
		"G\2\2\u00c0\u00c1\7o\2\2\u00c1\u00c2\7r\2\2\u00c2\u00c3\7v\2\2\u00c3\u00c4"+
		"\7{\2\2\u00c4\64\3\2\2\2\u00c5\u00c6\7k\2\2\u00c6\u00c7\7u\2\2\u00c7\u00c8"+
		"\7P\2\2\u00c8\u00c9\7q\2\2\u00c9\u00ca\7v\2\2\u00ca\u00cb\7G\2\2\u00cb"+
		"\u00cc\7o\2\2\u00cc\u00cd\7r\2\2\u00cd\u00ce\7v\2\2\u00ce\u00cf\7{\2\2"+
		"\u00cf\66\3\2\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7u\2\2\u00d2\u00d3\7"+
		"P\2\2\u00d3\u00d4\7q\2\2\u00d4\u00d5\7v\2\2\u00d5\u00d6\7P\2\2\u00d6\u00d7"+
		"\7w\2\2\u00d7\u00d8\7n\2\2\u00d8\u00d9\7n\2\2\u00d98\3\2\2\2\u00da\u00db"+
		"\7k\2\2\u00db\u00dc\7u\2\2\u00dc\u00dd\7P\2\2\u00dd\u00de\7w\2\2\u00de"+
		"\u00df\7n\2\2\u00df\u00e0\7n\2\2\u00e0:\3\2\2\2\u00e1\u00e2\7c\2\2\u00e2"+
		"\u00e3\7p\2\2\u00e3\u00e4\7f\2\2\u00e4<\3\2\2\2\u00e5\u00e6\7q\2\2\u00e6"+
		"\u00e7\7t\2\2\u00e7>\3\2\2\2\u00e8\u00e9\7p\2\2\u00e9\u00ea\7q\2\2\u00ea"+
		"\u00eb\7v\2\2\u00eb@\3\2\2\2\u00ec\u00f0\t\2\2\2\u00ed\u00ef\t\3\2\2\u00ee"+
		"\u00ed\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2"+
		"\2\2\u00f1B\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u00f4\7)\2\2\u00f4\u00f5"+
		"\4\63\64\2\u00f5\u00f6\4\62;\2\u00f6\u00f7\4\62;\2\u00f7\u00fc\4\62;\2"+
		"\u00f8\u00f9\7\62\2\2\u00f9\u00fd\4\63;\2\u00fa\u00fb\7\63\2\2\u00fb\u00fd"+
		"\4\62\64\2\u00fc\u00f8\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fd\u0106\3\2\2\2"+
		"\u00fe\u00ff\7\62\2\2\u00ff\u0107\4\63;\2\u0100\u0101\7\63\2\2\u0101\u0107"+
		"\4\62;\2\u0102\u0103\7\64\2\2\u0103\u0107\4\62;\2\u0104\u0105\7\65\2\2"+
		"\u0105\u0107\4\62\63\2\u0106\u00fe\3\2\2\2\u0106\u0100\3\2\2\2\u0106\u0102"+
		"\3\2\2\2\u0106\u0104\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0109\7)\2\2\u0109"+
		"D\3\2\2\2\u010a\u010c\4\62;\2\u010b\u010a\3\2\2\2\u010c\u010d\3\2\2\2"+
		"\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0113"+
		"\7\60\2\2\u0110\u0112\4\62;\2\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2"+
		"\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114F\3\2\2\2\u0115\u0113\3"+
		"\2\2\2\u0116\u011f\7\62\2\2\u0117\u011b\4\63;\2\u0118\u011a\4\62;\2\u0119"+
		"\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011c\3\2"+
		"\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u0116\3\2\2\2\u011e"+
		"\u0117\3\2\2\2\u011fH\3\2\2\2\u0120\u0125\7)\2\2\u0121\u0124\5K&\2\u0122"+
		"\u0124\n\4\2\2\u0123\u0121\3\2\2\2\u0123\u0122\3\2\2\2\u0124\u0127\3\2"+
		"\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0128\3\2\2\2\u0127"+
		"\u0125\3\2\2\2\u0128\u0129\7)\2\2\u0129J\3\2\2\2\u012a\u012b\7^\2\2\u012b"+
		"\u012c\t\5\2\2\u012cL\3\2\2\2\u012d\u012f\t\6\2\2\u012e\u012d\3\2\2\2"+
		"\u012f\u0130\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u0132"+
		"\3\2\2\2\u0132\u0133\b\'\2\2\u0133N\3\2\2\2\16\2\u00ee\u00f0\u00fc\u0106"+
		"\u010d\u0113\u011b\u011e\u0123\u0125\u0130";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}