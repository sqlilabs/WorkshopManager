package services;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import models.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import play.libs.F;
import play.libs.WS;
import play.mvc.Http;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yannig
 * <p/>
 * Date: 2014-06-24 3:56 PM
 */
public class Google {

    private String authEndpoint;
    private String tokenEndpoint;
    private String userEndpoint;


    //<--------------------------------------------------------------------------->
    //-                        Authentication OAuth call
    //<--------------------------------------------------------------------------->

    /**
     * <p/>
     * Document infos example:
     * <p/>
     * {
     * "issuer": "accounts.google.com",
     * "authorization_endpoint": "https://accounts.google.com/o/oauth2/auth",
     * "token_endpoint": "https://accounts.google.com/o/oauth2/token",
     * "userinfo_endpoint": "https://www.googleapis.com/plus/v1/people/me/openIdConnect",
     * "revocation_endpoint": "https://accounts.google.com/o/oauth2/revoke",
     * "jwks_uri": "https://www.googleapis.com/oauth2/v2/certs",
     * "response_types_supported": [
     * "code",
     * "token",
     * "id_token",
     * "code token",
     * "code id_token",
     * "token id_token",
     * "code token id_token",
     * "none"
     * ],
     * "subject_types_supported": [
     * "public"
     * ],
     * "id_token_alg_values_supported": [
     * "RS256"
     * ],
     * "token_endpoint_auth_methods_supported": [
     * "client_secret_post"
     * ]
     * }
     */
    public F.Promise<JsonNode> getEndpoint() {

        F.Promise<WS.Response> document = WS.url( Application.conf( "oauth.google.document" ) ).get();

        return document.map( new F.Function<WS.Response, JsonNode>() {
            @Override
            public JsonNode apply( WS.Response response ) throws Throwable {

                JsonNode jsonNode = response.asJson();
                authEndpoint = jsonNode.get( "authorization_endpoint" ).asText();
                tokenEndpoint = jsonNode.get( "token_endpoint" ).asText();
                userEndpoint = jsonNode.get( "userinfo_endpoint" ).asText();

                return jsonNode;
            }
        } );

    }

    /**
     */
    public String authenticationUrl( String token ) throws URISyntaxException, MalformedURLException {
        HttpGet endpoint = new HttpGet( authEndpoint );
        URIBuilder builder = new URIBuilder( endpoint.getURI() );
        builder.addParameter( "client_id", Application.conf("oauth.google.clientID") )
                .addParameter( "response_type", Application.conf( "oauth.google.responseType" ) )
                .addParameter( "scope", Application.conf( "oauth.google.scope" ) )
                .addParameter( "redirect_uri", Application.conf( "oauth.verifyUri" ) )
                .addParameter( "state", token );

        return builder.build().toURL().toString();
    }

    /**
     * <p/>
     * Token info JSON example:
     * {
     * "access_token": "ya29.1.AADtN_UoDvNkY-6HzKuXNyMSgJjrZa6ToKBVaLGeDnRLHMjM1SiOw7c3Ci7Nsk20vuVyaJ4",
     * "token_type": "Bearer",
     * "expires_in": 3600,
     * "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjAwYzVmZjE3NzQ4OTAyYmY3MzI5YmIyNDRiZmViZmZlMDg2MGY3M2IifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTA0NTczNjQyMjcyNzQ0MjE5OTkxIiwiYXpwIjoiMjE2OTg2Mjc5MDUwLWQycTRpNW1ybGVpbmRnOGV2NWF0ZDZ0OTlvczBtZXY3LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJ5Y2hhcnRvaXNAeXNkZXYuZnIiLCJhdF9oYXNoIjoiVjNLa0pFMl9RV1pEM1RQS1kyT2dOdyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdWQiOiIyMTY5ODYyNzkwNTAtZDJxNGk1bXJsZWluZGc4ZXY1YXRkNnQ5OW9zMG1ldjcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJoZCI6InlzZGV2LmZyIiwiaWF0IjoxMzk4ODUwMDYyLCJleHAiOjEzOTg4NTM5NjJ9.ezk20Jf28gcsRPu4bRqec4Y4QIMeM89NAFXYCnnQ4vg836RB0ooMoNb9kgvhRXy2OcZrpsaDe1wsPBtg9_9oA4-PcncGeIUsM5mVcQ5qlqW5AADBhNT2L5gLTXQIlc-3aNVDt4XjZ794Q_QW_DDtrajsrVi2Alop1OUQf_gxBy8"
     * }
     */
    public F.Promise<JsonNode> getTokenInfo( Http.Request request ) {
        String code = request.queryString().get( "code" )[0];

        Map<String, String> postParams = new HashMap<>();
        postParams.put( "code", code );
        postParams.put( "client_id", Application.conf( "oauth.google.clientID" ) );
        postParams.put( "client_secret", Application.conf( "oauth.google.secret" ) );
        postParams.put( "grant_type", Application.conf( "oauth.google.grant.type" ) );
        postParams.put( "redirect_uri", Application.conf( "oauth.verifyUri" ) );

        F.Promise<WS.Response> tokenRequest = WS.url( tokenEndpoint )
                .setHeader( "Content-Type", "application/x-www-form-urlencoded" )
                .post( Application.postParams( postParams ) );

        return tokenRequest.map( new F.Function<WS.Response, JsonNode>() {
            @Override
            public JsonNode apply( WS.Response response ) throws Throwable {
                return response.asJson();
            }
        } );
    }

    /**
     * <p/>
     * User info JSON example:
     * {
     * "kind": "plus#personOpenIdConnect",
     * "sub": "1087545455544219991",
     * "name": "Yannick Chartois",
     * "given_name": "Yannick",
     * "family_name": "Chartois",
     * "picture": "https:https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg?sz=50",
     * "email": "ychartois@domain.com",
     * "email_verified": "true",
     * "locale": "fr",
     * "hd": "ysdev.fr"
     * }
     */
    public F.Promise<JsonNode> getUserInfo( JsonNode tokenInfo ) {
        F.Promise<WS.Response> userInfos = WS.url( userEndpoint )
                .setQueryParameter( "access_token", getToken( tokenInfo ) )
                .get();

        return userInfos.map( new F.Function<WS.Response, JsonNode>() {
            @Override
            public JsonNode apply( WS.Response response ) throws Throwable {
                return response.asJson();
            }
        } );
    }

    //<--------------------------------------------------------------------------->
    //-                  Convenient method to retrieve information
    //<--------------------------------------------------------------------------->

    public User getUser( JsonNode userInfo ) {

        User user = new User();
        user.firstName = userInfo.get( "given_name" ).asText();
        user.lastName = userInfo.get( "family_name" ).asText();
        user.email = userInfo.get( "email" ).asText();

        return user;
    }


    public String getEmail( JsonNode userInfo ) {
        return userInfo.get( "email" ).asText();
    }

    public String getToken( JsonNode tokenInfo ) {
        return tokenInfo.get( "access_token" ).asText();
    }
}
