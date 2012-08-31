package org.entitypedia.games.common.exceptions;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class MapperAlreadyOverwrittenException extends GamesCommonException {

    public MapperAlreadyOverwrittenException() {
        super("Mapper already overwritten! Probably mixins are applied twice.");
    }
}
