import mill._
import mill.define.Target
import mill.scalalib._
import mill.util.Loose


val akkaActor2511 = ivy"com.typesafe.akka::akka-actor:2.5.11"
val akkaActor2510 = ivy"com.typesafe.akka::akka-actor:2.5.10"
val akkaActor259 = ivy"com.typesafe.akka::akka-actor:2.5.9"
val akkaStream259 = ivy"com.typesafe.akka::akka-stream:2.5.9"

trait BaseModule extends ScalaModule{

  val scalaVersion = "2.12.4"

  def validateDeps = T{
    compileClasspath.map{ cp =>
      val akkaDeps = cp.map(_.path.segments.last).filter(_.contains("akka"))
      if(akkaDeps.size == 1){
        println("OK")
      }else{
        sys.error(s"Multiple akka dependencies found: $akkaDeps")
      }
    }
  }
}

object sameWithin extends BaseModule {

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaActor259,
    akkaActor2510
  )
}

object sameTransitive extends BaseModule {

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaStream259,
    akkaActor2510
  )
}

object moduleTransitive extends BaseModule {
  override def moduleDeps = super.moduleDeps ++ Seq(
    sameWithin
  )

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    akkaActor2511
  )


}
