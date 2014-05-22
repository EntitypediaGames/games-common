package org.entitypedia.games.common.oauth;

import org.entitypedia.games.common.model.WordGameUser;
import org.entitypedia.games.common.repository.IWordGameUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Returns consumer details for players.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class WordGameUserConsumerDetailsService implements ConsumerDetailsService {

    @Autowired
    private IWordGameUserDAO userDAO;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey) throws OAuthException {
        WordGameUser user = userDAO.getUserByUID(consumerKey);

        if (null == user) {
            throw new UsernameNotFoundException("Cannot find consumer by key: " + consumerKey);
        }

        // this cast requires an aspectj to weave proper interfaces (and their implementations) into WordGameUser
        return (ConsumerDetails) user;
    }
}