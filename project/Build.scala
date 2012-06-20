import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "sociul"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "mysql" % "mysql-connector-java" % "5.1.18",
      "org.jsoup" % "jsoup" % "1.6.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
