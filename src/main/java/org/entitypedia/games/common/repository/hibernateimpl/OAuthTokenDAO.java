package org.entitypedia.games.common.repository.hibernateimpl;

import org.entitypedia.games.common.model.OAuthToken;
import org.entitypedia.games.common.repository.IOAuthTokenDAO;
import org.hibernate.criterion.Restrictions;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class OAuthTokenDAO extends AbstractTypedDAO<OAuthToken, Long> implements IOAuthTokenDAO {

    public OAuthTokenDAO() {
        super(OAuthToken.class);
    }

    @Override
    public OAuthToken getTokenByUIDAndResource(String uid, String resourceId) {
        return (OAuthToken) getSessionFactory().getCurrentSession().
                createCriteria(OAuthToken.class).
                add(Restrictions.naturalId().set("uid", uid).set("resourceId", resourceId)).setCacheable(true).uniqueResult();
    }
}