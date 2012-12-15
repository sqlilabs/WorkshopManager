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
	 * CALLBACK_GOOGLE_INFO_INDEX: index of the code value
	 */
	public static final int			CALLBACK_GOOGLE_INFO_INDEX	=	0;
	
	/**
	 * GOOGLE_ENDPOINT_URL: This endpoint is the target of the initial request for an access token. 
	 * It handles active session lookup, authenticating the user, and user consent. 
	 * The result of requests of this endpoint include access tokens, refresh tokens, and authorization codes.
	 */
	public static final String 		GOOGLE_ENDPOINT_URL			=	"https://accounts.google.com/o/oauth2/auth";
	
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
