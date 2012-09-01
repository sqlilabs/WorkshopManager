import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "Workshop-Manager"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.github.twitter" % "bootstrap" % "2.0.2",
      "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here     
      resolvers += "webjars" at "http://webjars.github.com/m2" 
    )

}
