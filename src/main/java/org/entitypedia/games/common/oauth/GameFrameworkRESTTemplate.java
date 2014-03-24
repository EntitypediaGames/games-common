package org.entitypedia.games.common.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.entitypedia.games.common.client.WordGameClient;
import org.entitypedia.games.common.exceptions.WordGameException;
import org.entitypedia.games.common.model.ResultsPage;
import org.entitypedia.games.gameframework.client.IGameFrameworkClient;
import org.entitypedia.games.gameframework.common.api.IClueAPI;
import org.entitypedia.games.gameframework.common.api.IPlayerAPI;
import org.entitypedia.games.gameframework.common.api.IWordAPI;
import org.entitypedia.games.gameframework.common.model.Clue;
import org.entitypedia.games.gameframework.common.model.Player;
import org.entitypedia.games.gameframework.common.model.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.OAuthSecurityContextHolder;
import org.springframework.security.oauth.consumer.OAuthSecurityContextImpl;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Extends OAuthRestTemplate with proper authentication use.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class GameFrameworkRESTTemplate extends OAuthRestTemplate implements InitializingBean, IGameFrameworkClient {

    private static final Logger log = LoggerFactory.getLogger(GameFrameworkRESTTemplate.class);

    private OAuthConsumerTokenServices tokenServices;
    private ProtectedResourceDetails resource;

    private HttpClient httpClient;

    private String frameworkAPIRoot;
    private String frameworkSecureAPIRoot;

    private ResponseErrorHandler responseErrorHandler = new ThrowingResponseErrorHandler();

    public GameFrameworkRESTTemplate(ProtectedResourceDetails resource) {
        super(resource);
        this.resource = resource;
    }

    private void loadOAuthSecurityContext() {
        log.debug("Loading OAuthSecurityContext for resource {}", resource.getId());
        OAuthConsumerToken token = getTokenServices().getToken(resource.getId());
        if (null == token) {
            log.debug("Token not found. Clearing OAuthSecurityContext for resource {}", resource.getId());
            OAuthSecurityContextHolder.setContext(null);
        } else {
            OAuthSecurityContextImpl context = new OAuthSecurityContextImpl();
            Map<String, OAuthConsumerToken> accessTokens = new TreeMap<>();

            accessTokens.put(resource.getId(), token);
            context.setAccessTokens(accessTokens);
            OAuthSecurityContextHolder.setContext(context);
            log.debug("Loaded OAuthSecurityContext for resource {} with token {}", resource.getId(), token.getValue());
        }
    }

    private void clearOAuthSecurityContext() {
        log.debug("Clearing OAuthSecurityContext for resource {}", resource.getId());
        OAuthSecurityContextHolder.setContext(null);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            loadOAuthSecurityContext();
            return super.doExecute(url, method, requestCallback, responseExtractor);
        } finally {
            clearOAuthSecurityContext();
        }

    }

    public OAuthConsumerTokenServices getTokenServices() {
        return tokenServices;
    }

    public void setTokenServices(OAuthConsumerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setErrorHandler(responseErrorHandler);
        setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    private class ThrowingResponseErrorHandler implements ResponseErrorHandler {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().value() != 200;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            throw WordGameClient.processError(response.getBody(), mapper);
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getFrameworkAPIRoot() {
        return frameworkAPIRoot;
    }

    public void setFrameworkAPIRoot(String frameworkAPIRoot) {
        this.frameworkAPIRoot = frameworkAPIRoot;
    }

    public String getFrameworkSecureAPIRoot() {
        return frameworkSecureAPIRoot;
    }

    public void setFrameworkSecureAPIRoot(String frameworkSecureAPIRoot) {
        this.frameworkSecureAPIRoot = frameworkSecureAPIRoot;
    }

    @Override
    public Clue readClue(long clueID) {
        try {
            return getForObject(new URI(frameworkAPIRoot + IClueAPI.READ_CLUE.replaceAll("\\{.*\\}", Long.toString(clueID))), Clue.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public ResultsPage<Clue> listClues(Integer pageSize, Integer pageNo, String filter, String order) {
        try {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Class<ResultsPage<Clue>> clazz = (Class) ResultsPage.class;
            return getForObject(new URI(WordGameClient.addPageSizeAndNoAndFilterAndOrder(frameworkAPIRoot + IClueAPI.LIST_CLUES + "?", pageSize, pageNo, filter, order)), clazz);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void login() {
        try {
            getForObject(new URI(frameworkAPIRoot + IPlayerAPI.LOGIN_PLAYER), Void.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Player loginFacebook(String token) {
        try {
            return postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.LOGIN_FACEBOOK_PLAYER + "?token=" + URLEncoder.encode(token, "UTF-8")), null, Player.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Player loginGPlus(String code) {
        try {
            return postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.LOGIN_GPLUS_PLAYER + "?code=" + URLEncoder.encode(code, "UTF-8")), null, Player.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void activateEmail(String code) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.ACTIVATE_PLAYER_EMAIL + "?code=" + URLEncoder.encode(code, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void requestEmailActivation() {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.REQUEST_PLAYER_EMAIL_ACTIVATION), null, Void.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void resetPassword(String code, String password) {
        try {
            postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.RESET_PLAYER_PASSWORD + "?code=" + URLEncoder.encode(code, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void requestPasswordReset(String email) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.REQUEST_PLAYER_PASSWORD_RESET + "?email=" + URLEncoder.encode(email, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Player createPlayer(Player player) {
        try {
            return postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.CREATE_PLAYER), player, Player.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Player readPlayer(String playerID) {
        try {
            return getForObject(new URI(frameworkAPIRoot + IPlayerAPI.READ_PLAYER.replaceAll("\\{.*\\}", playerID)), Player.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Player readPlayer(long playerID) {
        try {
            return getForObject(new URI(frameworkAPIRoot + IPlayerAPI.READ_PLAYER.replaceAll("\\{.*\\}", Long.toString(playerID))), Player.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void deletePlayer(long playerID) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.DELETE_PLAYER + "?playerID=" + Long.toString(playerID)), null, Void.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayer(Player player) {
        try {
            postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.UPDATE_PLAYER), player, Void.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerPassword(long playerID, String password) {
        try {
            postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.UPDATE_PLAYER_PASSWORD + "?playerID=" + Long.toString(playerID)
                    + "&password=" + URLEncoder.encode(password, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerEmail(long playerID, String email) {
        try {
            postForObject(new URI(frameworkSecureAPIRoot + IPlayerAPI.UPDATE_PLAYER_EMAIL + "?playerID=" + Long.toString(playerID)
                    + "&email=" + URLEncoder.encode(email, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerFirstName(long playerID, String firstName) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.UPDATE_PLAYER_FIRST_NAME + "?playerID=" + Long.toString(playerID)
                    + "&firstName=" + URLEncoder.encode(firstName, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerLastName(long playerID, String lastName) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.UPDATE_PLAYER_FIRST_NAME + "?playerID=" + Long.toString(playerID)
                    + "&lastName=" + URLEncoder.encode(lastName, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerFacebook(long playerID, String token) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.UPDATE_PLAYER_FACEBOOK + "?playerID=" + Long.toString(playerID)
                    + "&token=" + URLEncoder.encode(token, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePlayerGPlus(long playerID, String code) {
        try {
            postForObject(new URI(frameworkAPIRoot + IPlayerAPI.UPDATE_PLAYER_GPLUS + "?playerID=" + Long.toString(playerID)
                    + "&code=" + URLEncoder.encode(code, "UTF-8")), null, Void.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public ResultsPage<Player> listPlayers(Integer pageSize, Integer pageNo) {
        try {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Class<ResultsPage<Player>> clazz = (Class) ResultsPage.class;
            return getForObject(new URI(WordGameClient.addPageSizeAndNo(frameworkAPIRoot + IPlayerAPI.LIST_PLAYERS + "?", pageSize, pageNo)), clazz);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public Word readWord(long wordID) {
        try {
            return getForObject(new URI(frameworkAPIRoot + IWordAPI.READ_WORD.replaceAll("\\{.*\\}", Long.toString(wordID))), Word.class);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public ResultsPage<Word> listWords(Integer pageSize, Integer pageNo, String filter, String order) {
        try {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Class<ResultsPage<Word>> clazz = (Class) ResultsPage.class;
            return getForObject(new URI(WordGameClient.addPageSizeAndNoAndFilterAndOrder(frameworkAPIRoot + IWordAPI.LIST_WORDS + "?", pageSize, pageNo, filter, order)), clazz);
        } catch (URISyntaxException e) {
            throw new WordGameException(e.getMessage(), e);
        }
    }

    @Override
    public String getApiEndpoint() {
        return frameworkAPIRoot;
    }

    @Override
    public void setApiEndpoint(String apiEndpoint) {
        frameworkAPIRoot = apiEndpoint;
    }

    @Override
    public boolean getSignConnection() {
        return false;
    }

    @Override
    public void setSignConnection(boolean signConnection) {
        // nop
    }
}