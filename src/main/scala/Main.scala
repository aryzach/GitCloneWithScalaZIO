import zio._
/*
object Main extends App {

  def run(args: List[String]): URIO[ZEnv, ExitCode] = ZIO.succeed(()).exitCode

}
*/



import com.tcpClientTest._
import com.refDiscovery._
import akka.actor.ActorSystem
import akka.util.ByteString

object Main {
  def main(args: Array[String]) = {
    val addr = "127.0.0.1" // "localhost"
    val port = 9418
    val payload = RefDiscovery.gitProtoRequest(addr)("snake")

    // create tcp connection
    val actorSys = ActorSystem.create("MyActorSys")
    val tcpActor = actorSys.actorOf(AkkaTcpClient.props(addr, port), "tcpActor") 
    Thread.sleep(1000)

    tcpActor ! AkkaTcpClient.SendMessage(ByteString(payload))
    //    val client = Client(new InetSocketAddress("localhost", 9418), listener: ActorRef) 
    println("Hello, world")
  }
}



