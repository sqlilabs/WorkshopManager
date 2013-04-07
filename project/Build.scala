import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

	val appName         = "Workshop-Manager"
	val appVersion      = "1.0-SNAPSHOT"

	/*
	 * Versions des dépendances, quand il définir plusieurs
	 * composants avec les mêmes numéros de version.
	 */

	// Add your project dependencies here,
	val appDependencies = Seq(

		"org.webjars" 					% "webjars-play" 					% "2.0",
    	"org.webjars" 					% "bootstrap" 						% "2.1.1",
    	"org.easytesting" 				% "fest-assert-core" 				% "2.0M7",
		"mysql" 						% "mysql-connector-java" 			% "5.1.20"
	)

  	val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	 	ebeanEnabled := true
	 )
}
