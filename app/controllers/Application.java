package controllers;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.handler.ssl.SslBufferPool;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
  
  public static Result index() {
	  
    return ok(index.render("Your new application is ready.", getWorkshops()));
  }
 
  public static List<String> getWorkshops(){
	  List<String> wks = new ArrayList<String>();
	  for (int i=1; i<=5; i++) {
		  StringBuffer sb = new StringBuffer();
		  sb.append("WorksShop_");
		  sb.append(i);
		  wks.add(sb.toString());
	  }
	  return wks;
  }
  
}