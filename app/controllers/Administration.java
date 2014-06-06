package controllers;

import com.avaje.ebean.Ebean;
import models.Roles;
import models.Type;
import models.User;
import models.utils.helpers.UsersUtils;
import play.i18n.Messages;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repository.WorkshopRepository;
import scala.Int;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 11/8/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Administration extends Controller {

    /**
     * This action redirect on the users administration view
     *
     * @return the users administration view
     */
    @Security.Authenticated(Secured.class)
    public static Result adminUsers( int page ) {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

        List<User> users = User.find.orderBy("firstName asc").findList();
        int userPerPage = Integer.parseInt(Application.conf("user.per.page"));

        // Compute the number of page
        int modulo =  users.size() % userPerPage;
        int pageNumWithoutRest =  (int) Math.floor(users.size() / userPerPage);
        int pageNum = modulo == 0 ? pageNumWithoutRest : pageNumWithoutRest + 1; // if the modulo is not 0, we will need one page more for the remaining users

        // Compute the subList
        int lastIndex =  page + 1 == pageNum ?  users.size() : userPerPage * (page + 1); // for the last page, the sublist is maybe not full, so we end it with the users.size()
        List<User> subList = users.subList(userPerPage * page, lastIndex);

        return ok( views.html.admin.adminUsers.render( Messages.get("admin.users.title"), subList , page, pageNum ) );
    }

    /**
     * This action redirect on the users administration view
     *
     * @return the users administration view
     */
    @Security.Authenticated(Secured.class)
    public static Result adminTypes() {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

        List<Type> types = Type.find.all();


        return ok( views.html.admin.adminTypes.render("", types));
    }

    /**
     * This action allows to change the role of a User
     *
     * @return ok if the connected user have the right, forbidden otherwise
     */
    @Security.Authenticated(Secured.class)
    public static Result updateRole() {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

        Map<String, String[]> data 	= 	request().body().asFormUrlEncoded();
        User user 	= 	User.find.where().eq("email", data.get("user")[0]).findUnique();
        user.role	= 	Roles.valueOf( data.get("role")[0] );
        Ebean.save(user);
        return ok();
    }


    // <--------------------------------------------------------------------------->
    // -                                Constructor(s)
    // <--------------------------------------------------------------------------->
    /**
     * Default Constructor
     */
    private Administration() {
        super();
    }
}
