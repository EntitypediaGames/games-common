package org.entitypedia.games.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.common.signature.SharedConsumerSecret;
import org.springframework.security.oauth.provider.ExtraTrustConsumerDetails;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Introduces ExtraTrustConsumerDetails and SharedConsumerSecret to WordGameUser.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public aspect WordGameUserConsumerDetails {

    @JsonIgnore
    private final static List<GrantedAuthority> USER_AUTHORITY =
            new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("ROLE_PLAYER")));

    declare parents:org.entitypedia.games.common.model.WordGameUser+ implements ExtraTrustConsumerDetails,SharedConsumerSecret,SignatureSecret;

    // SharedConsumerSecret
    @JsonIgnore
    public String org.entitypedia.games.common.model.WordGameUser.getConsumerSecret() {
        return this.getPassword();
    }

    // ExtraTrustConsumerDetails
    @JsonIgnore
    public boolean org.entitypedia.games.common.model.WordGameUser.isRequiredToObtainAuthenticatedToken() {
        // we're doing 2-legged here
        return false;
    }

    // ConsumerDetails
    @JsonIgnore
    public String org.entitypedia.games.common.model.WordGameUser.getConsumerKey() {
        return this.getUid();
    }

    // ConsumerDetails
    @JsonIgnore
    public String org.entitypedia.games.common.model.WordGameUser.getConsumerName() {
        return "Player " + this.getUid();
    }

    // ConsumerDetails
    @JsonIgnore
    public SignatureSecret org.entitypedia.games.common.model.WordGameUser.getSignatureSecret() {
        return (SignatureSecret) this;
    }

    // ConsumerDetails
    @JsonIgnore
    public List<GrantedAuthority> org.entitypedia.games.common.model.WordGameUser.getAuthorities() {
        return USER_AUTHORITY;
    }
}