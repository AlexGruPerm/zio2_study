package app

import zio.Console._
import zio._

import scala.language.existentials

object main extends zio.ZIOAppDefault {
  final def run :URIO[Console,ExitCode] =
    myAppLogic.exitCode

  val myAppLogic = for {
    _ <- printLine ("Begin client myAppLogic")
  _ <- printLine ("End client myAppLogic")
  } yield ()

}
