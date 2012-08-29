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
	val springVersion		= "3.1.2.RELEASE"
	val hibernateVersion	= "4.1.6.Final"
	val powermockVersion	= "1.4.12"
	
	// Add your project dependencies here,
	val appDependencies = Seq(
		// Nécesaire pour JPA, utilisez dans le persistence.xml
		"org.hibernate" 				% "hibernate-entitymanager"			% hibernateVersion,
		
		/*
		 * Dépendances Projet
		 */
		"org.hibernate" 				% "hibernate-core" 					% hibernateVersion,
		"org.springframework" 			% "spring-beans" 					% springVersion,
		
		/*
		 * Dépendances des tests
		 */
		"junit"							% "junit" 							% "4.10",
		"org.easytesting" 				% "fest-assert-core" 				% "2.0M7",
		"org.springframework" 			% "spring-test" 					% springVersion,
		"org.dbunit" 					% "dbunit" 							% "2.4.8",
		"com.h2database" 				% "h2" 								% "1.3.168",
		"org.mockito" 					% "mockito-core" 					% "1.9.0",
		"org.powermock" 				% "powermock-api-mockito" 			% powermockVersion,
		"org.powermock" 				% "powermock-module-junit4" 		% powermockVersion,
		"org.seleniumhq.selenium" 		% "selenium-java" 					% "2.25.0",
		"org.fluentlenium" 				% "fluentlenium-core" 				% "0.7.2",
		
		/*
		 * Regarder http://springtestdbunit.github.com/spring-test-dbunit/index.html
		 */
		"com.github.springtestdbunit"	% "spring-test-dbunit" 				% "1.0.0",
		
		/*
		 * Regarder http://code.google.com/p/powermock/wiki/PowerMockRule pour essayer
		 * d'utiliser @Rule
		 */
		"org.powermock" 				% "powermock-module-junit4-rule"	% powermockVersion,
		"org.powermock" 				% "powermock-classloading-xstream" 	% powermockVersion
		
	)
	
	val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
		// Add your own project settings here
		ebeanEnabled := false
	)

}
