import zio._
/*
object Main extends App {

  def run(args: List[String]): URIO[ZEnv, ExitCode] = ZIO.succeed(()).exitCode

}
*/



import com.tcpClientTest._
import com.refDiscovery._

object Main {
  def main(args: Array[String]) = {
   val a = RefDiscovery.lsRemote(RefDiscovery.testRemote)
  }
}



