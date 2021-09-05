import zio._
import zio.Console._
import com.refDiscovery._
import java.io.IOException
import scala.util.{Success, Failure}
import scala.concurrent._
import ExecutionContext.Implicits.global


object Main extends App {

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = 
    RefDiscovery.lsRemote(RefDiscovery.testRemote).map(f => 
        f onComplete { 
          case Success(s) => println(s)
          case Failure(t) => println(t.getMessage)
        }).exitCode

}



/*


object Main {
  def main(args: Array[String]) = {
    val a = RefDiscovery.lsRemote(RefDiscovery.testRemote)
  }
}
*/


