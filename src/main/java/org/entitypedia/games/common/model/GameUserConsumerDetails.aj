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
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public aspect GameUserConsumerDetails {

    @JsonIgnore
    private final static List<GrantedAuthority> USER_AUTHORITY =
            new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("ROLE_PLAYER")));

    declare parents:org.entitypedia.games.common.model.GameUser+ implements ExtraTrustConsumerDetails,SharedConsumerSecret,SignatureSecret;

    // SharedConsumerSecret
    @JsonIgnore
    public String GameUser.getConsumerSecret() {
        return this.getPassword();
    }

    // ExtraTrustConsumerDetails
    @JsonIgnore
    public boolean GameUser.isRequiredToObtainAuthenticatedToken() {
        // we're doing 2-legged here
        return false;
    }

    // ConsumerDetails
    @JsonIgnore
    public String GameUser.getConsumerKey() {
        return this.getUid();
    }

    // ConsumerDetails
    @JsonIgnore
    public String GameUser.getConsumerName() {
        return "Player " + this.getUid();
    }

    // ConsumerDetails
    @JsonIgnore
    public SignatureSecret GameUser.getSignatureSecret() {
        return (SignatureSecret) this;
    }

    // ConsumerDetails
    @JsonIgnore
    public List<GrantedAuthority> GameUser.getAuthorities() {
        return USER_AUTHORITY;
    }
}