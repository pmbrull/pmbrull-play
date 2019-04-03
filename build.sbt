import sbtcrossproject.{crossProject, CrossType}
import sbt.Project.projectToRef

name := """pmbrull"""
version := "1.0-SNAPSHOT"

lazy val clients = Seq(client)

lazy val server = (project in file("server")).settings(commonSettings).settings(
  scalaJSProjects := clients,
  routesImport += "config.Routes._",
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.2",
    guice,
    specs2 % Test
  ),
  // add heroku plugins
  herokuAppName in Compile := "pmbrull",
  herokuSkipSubProjects in Compile := false,
).enablePlugins(PlayScala).
  aggregate(clients.map(projectToRef):_*).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.5",
    "com.lihaoyi" %%% "scalatags" % "0.6.8",
    "com.lihaoyi" %%% "scalarx" % "0.4.0",
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.8",
  organization := "com.pmbrull"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}