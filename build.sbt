name := "zio2_study"

ThisBuild / organization := "yakushev"
ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.15"

val Version_zio  = "2.0.0-RC1"

// PROJECTS
lazy val global = project
  .in(file("."))
  .settings(commonSettings)
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    client,server
  )

lazy val client = (project in file("client"))
  .settings(
    Compile / mainClass        := Some("app.main"),
    assembly / assemblyJarName := "client.jar",
    name := "client",
    commonSettings,
    libraryDependencies ++= commonDependencies
  )

lazy val server = (project in file("server"))
  .settings(
    Compile / mainClass        := Some("app.main"),
    assembly / assemblyJarName := "server.jar",
    name := "server",
    commonSettings,
    libraryDependencies ++= commonDependencies
  )

lazy val dependencies =
  new {
    val logbackV = "1.2.3"
    val logback = "ch.qos.logback" % "logback-classic" % logbackV
    val zio = "dev.zio" %% "zio" % Version_zio
    val lb = List(logback)
    val zioDep = List(zio)
  }

val commonDependencies = {
  dependencies.lb ++
  dependencies.zioDep
}

lazy val compilerOptions = Seq(
        "-deprecation",
        "-encoding", "utf-8",
        "-explaintypes",
        "-feature",
        "-unchecked",
        "-language:postfixOps",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-Xcheckinit",
        "-Xfatal-warnings",
        "-Ywarn-unused:params,-implicits"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("public"),
    Resolver.sonatypeRepo("releases"),
    Resolver.DefaultMavenRepository,
    Resolver.mavenLocal,
    Resolver.bintrayRepo("websudos", "oss-releases")
  )
)

client / assembly / assemblyMergeStrategy := {
  case PathList("module-info.class") => MergeStrategy.discard
  case x if x.endsWith("/module-info.class") => MergeStrategy.discard
  case PathList("META-INF", xs @ _*)         => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}