import zio._
import zio.Console._
import com.refDiscovery._
import java.io.IOException

object Main extends App {

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = 
    RefDiscovery.lsRemote(RefDiscovery.testRemote).map(f => println(f)).exitCode

}



/*


object Main {
  def main(args: Array[String]) = {
    val a = RefDiscovery.lsRemote(RefDiscovery.testRemote)
  }
}
*/


