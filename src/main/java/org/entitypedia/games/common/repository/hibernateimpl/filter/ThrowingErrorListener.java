package org.entitypedia.games.common.repository.hibernateimpl.filter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.entitypedia.games.common.exceptions.FilterParsingException;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class ThrowingErrorListener extends BaseErrorListener {

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		throw new FilterParsingException(
				"Error in parsing filter expression at " +
						"[" + line + ":" + charPositionInLine + "]" + offendingSymbol + ": " + msg);
	}
}