package org.entitypedia.games.common.repository.hibernateimpl.filter;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.entitypedia.games.common.exceptions.FilterParsingException;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class ThrowingErrorListener extends BaseErrorListener {

	@Override
	public <T extends Token> void syntaxError(@NotNull Recognizer<T, ?> recognizer,
											  @Nullable T offendingSymbol,
											  int line,
											  int charPositionInLine,
											  @NotNull String msg,
											  @Nullable RecognitionException e) {
		throw new FilterParsingException("Error in parsing filter expression at " + offendingSymbol + ": " + msg);
	}
}