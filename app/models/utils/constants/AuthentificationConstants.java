package models.utils.constants;

/**
 * Constant class for objects related to Authentification
 * 
 * @author ychartois
 *
 */
public class AuthentificationConstants {

	
	//<--------------------------------------------------------------------------->
	//-							 	Constants	        
	//<--------------------------------------------------------------------------->
	/**
	 * CALLBACK_GOOGLE_CODE: query String key send by Google
	 */
	public static final String		CALLBACK_GOOGLE_CODE		=	"code";
	
	/**
	 * CALLBACK_GOOGLE_CODE: query String key send by Google
	 */
	public static final String		CALLBACK_GOOGLE_ERROR		=	"error";
	
	/**
	 * CALLBACK_GOOGLE_INFO_INDEX: index of the code value
	 */
	public static final int			CALLBACK_GOOGLE_INFO_INDEX	=	0;
	
	
	
	//	URL
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	/**
	 * GOOGLE_ENDPOINT_URL: This endpoint is the target of the initial request for an access token. 
	 * It handles active session lookup, authenticating the user, and user consent. 
	 * The result of requests of this endpoint include access tokens, refresh tokens, and authorization codes.
	 */
	public static final String 		GOOGLE_ENDPOINT_URL			=	"https://accounts.google.com/o/oauth2/auth";
	
	/**
	 * GOOGLE_ENDPOINT_URL: This endpoint is the target of the initial request for an access token. 
	 * It handles active session lookup, authenticating the user, and user consent. 
	 * The result of requests of this endpoint include access tokens, refresh tokens, and authorization codes.
	 */
	public static final String 		GOOGLE_ACCESS_TOKEN_URL		=	"https://accounts.google.com/o/oauth2/token";
	
	
	/**
	 * GOOGLE_USER_INFO_URL: Returns basic user profile information, including name, userid, gender, birthdate, photo, 
	 * locale, and timezone. 
	 */
	public static final String 		GOOGLE_USER_INFO_URL		=	"https://www.googleapis.com/oauth2/v1/userinfo";
	
	
	//	VALUES
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * GOOGLE_CLIENT_ID: Indicates the client that is making the request. 
	 * The value passed in this parameter must exactly match the value shown in the APIs Console.
	 */
	public static final String		GOOGLE_CLIENT_ID			=	"client_id";
	
	/**
	 * GOOGLE_CLIENT_ID_PROP: the Google client ID key in the Application.conf properties file
	 */
	public static final String		GOOGLE_CLIENT_ID_PROP		=	"google.client.id";
	
	/**
	 * GOOGLE_REDIRECT_URL: Determines where the response is sent. 
	 * The value of this parameter must exactly match one of the values registered in the 
	 * APIs Console (including the http or https schemes, case, and trailing '/').
	 */
	public static final String		GOOGLE_REDIRECT_URL			=	"redirect_uri";
	
	/**
	 * GOOGLE_REDIRECT_URL_PROP: the Google Redirect URL key in the Application.conf properties file
	 */
	public static final String		GOOGLE_REDIRECT_URL_PROP	=	"google.redirect.uri";
	
	/**
	 * GOOGLE_SCOPE: Indicates the Google API access your application is requesting. 
	 * The values passed in this parameter inform the consent page shown to the user. 
	 * There is an inverse relationship between the number of permissions requested and the likelihood of obtaining user consent.
	 */
	public static final String		GOOGLE_SCOPE 				=	"scope";
	
	/**
	 * GOOGLE_SCOPE_PROP: the Google scope key in the Application.conf properties file
	 */
	public static final String		GOOGLE_SCOPE_PROP			=	"google.scope";
	
	/**
	 * GOOGLE_RESPONSE_TYPE: Determines if the Google Authorization Server returns an authorization code, or an opaque access token.
	 */
	public static final String		GOOGLE_RESPONSE_TYPE		=	"response_type";
	
	/**
	 * GOOGLE_RESPONSE_TYPE_PROP: the Google response type key in the Application.conf properties file
	 */
	public static final String		GOOGLE_RESPONSE_TYPE_PROP	=	"google.response.type";
	
	/**
	 * GOOGLE_CLIENT_SECRET: The client secret obtained during application registration
	 */
	public static final String		GOOGLE_CLIENT_SECRET		=	"client_secret";
	
	/**
	 * GOOGLE_CLIENT_SECRET_PROP: the Google client secret key in the Application.conf properties file
	 */
	public static final String		GOOGLE_CLIENT_SECRET_PROP	=	"google.client.secret";
	
	/**
	 * GOOGLE_GRANT_TYPE: As defined in the OAuth 2.0 specification, this field must contain a value of authorization_code
	 */
	public static final String		GOOGLE_GRANT_TYPE			=	"grant_type";
	
	/**
	 * GOOGLE_RESPONSE_TYPE_PROP: the Google grant type key in the Application.conf properties file
	 */
	public static final String		GOOGLE_GRANT_TYPE_PROP		=	"google.grant.type";
	
	/**
	 * GOOGLE_GRANT_TYPE: The authorization code returned from the initial request
	 */
	public static final String		GOOGLE_CODE					=	"code";
	
	
	//	USER INFOS RESPONSE AND QUERY STRING
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * Access token delivered by Google
	 */
	public static final String		GOOGLE_ACCESS_TOKEN			=	"access_token";
	
	/**
	 * The email address of the logged in user
	 */
	public static final String		GOOGLE_EMAIL				=	"email";
	
	/**
	 * The first name of the logged in user
	 */
	public static final String		GOOGLE_FIRST_NAME			=	"given_name";
	
	/**
	 * The last name of the logged in user
	 */
	public static final String		GOOGLE_LAST_NAME			=	"family_name";
	
	/**
	 * The URL to the user's profile picture. If the user has no public profile, this field is not included.
	 */
	public static final String		GOOGLE_PICTURE				=	"picture";

	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructor private because it's a constant class
	 */
	public AuthentificationConstants() {
		// TODO Auto-generated constructor stub
	}

}
