import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

	val appName         = "Workshop-Manager"
	val appVersion      = "1.1.0-SNAPSHOT"

	// Add your project dependencies here,
  val appDependencies = Seq(
    javaCore, javaJdbc, javaEbean, cache,
    "org.webjars" % "webjars-play" % "2.1.0-1",
    "org.webjars" % "bootstrap" % "2.1.1",
    "org.easytesting" % "fest-assert-core" % "2.0M7",
    "commons-lang" % "commons-lang" % "2.6",
    "org.apache.commons" % "commons-io" % "1.3.2",
    "mysql" % "mysql-connector-java" % "5.1.20",
    "org.mockito" % "mockito-core" % "1.8.5"
  )

  	val main = play.Project(appName, appVersion, appDependencies).settings(
  		resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
  		resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
	 	  ebeanEnabled := true,
      compile in Test <<= PostCompile(Test),
Keys.fork in (Test) := false
	 )
}
