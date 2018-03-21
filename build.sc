import mill._
import mill.define.Target
import mill.scalalib._
import mill.util.Loose
import sameWithin.expectedAkka


val akkaActor2511 = ivy"com.typesafe.akka::akka-actor:2.5.11"
val akkaActor2510 = ivy"com.typesafe.akka::akka-actor:2.5.10"
val akkaActor259 = ivy"com.typesafe.akka::akka-actor:2.5.9"
val akkaStream259 = ivy"com.typesafe.akka::akka-stream:2.5.9"

trait BaseModule extends ScalaModule{

  val scalaVersion = "2.12.4"

  def expectedAkka: String

  def validateDeps = T{
    compileClasspath.map{ cp =>
      val akkaDeps = cp.map(_.path.segments.last).filter(_.contains("akka-actor"))
      if(akkaDeps == Agg(expectedAkka)){
        println("OK")
      }else{
        sys.error(s"Expected $expectedAkka but found: $akkaDeps")
      }
    }
  }
}

object sameWithin extends BaseModule {

  val expectedAkka = "akka-actor_2.12-2.5.10.jar"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaActor259,
    akkaActor2510
  )
}

object sameTransitive extends BaseModule {

  val expectedAkka = "akka-actor_2.12-2.5.10.jar"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaStream259,
    akkaActor2510
  )
}

object moduleTransitive extends BaseModule {

  val expectedAkka = "akka-actor_2.12-2.5.11.jar"

  override def moduleDeps = super.moduleDeps ++ Seq(
    sameWithin
  )

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaActor2511
  )
}
