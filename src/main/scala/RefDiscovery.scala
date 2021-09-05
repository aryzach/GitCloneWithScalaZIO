/*
gitProtoRequest :: String -> String -> String
gitProtoRequest host repo = pktLine $ "git-upload-pack /" ++ repo ++ "\0host="++host++"\0"

pktLine :: String -> String
pktLine msg = printf "%04s%s" (toHex . (4 +) $ length msg) msg

data Remote = Remote {
  getHost         :: String
  , getPort         :: Maybe Int
  , getRepository   :: String
} deriving (Eq, Show)

lsRemote' :: Remote -> IO [PacketLine]
lsRemote' Remote{..} = withSocketsDo $
withConnection getHost (show $ fromMaybe 9418 getPort) $ \sock -> do
let payload = gitProtoRequest getHost getRepository
send sock payload
response <- receive sock
send sock flushPkt -- Tell the server to disconnect
return $ parsePacket $ L.fromChunks [response]

*/

package com.refDiscovery

import com.tcpClient._

import akka.actor.ActorSystem
import akka.util.ByteString
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future


import zio._

sealed case class Remote(getHost: String, getPort: Option[Int], getRepo: String)

object RefDiscovery {

  def pktLine(msg: String): String = {
    val hex = (4 + msg.length)
    f"$hex%04x$msg%s" 
  }

  // questionable \u0000 here. Need to look it up when have internet (vs haskells \0)
  def gitProtoRequest(host: String)(repo: String): String = pktLine("git-upload-pack /" ++ repo ++ "\u0000host="++host++"\u0000")

  def lsRemote(remote: Remote): ZIO[Any, Nothing, Future[String]] = {
    val payload: String = gitProtoRequest(remote.getHost)(remote.getRepo)
    val flush = "\u0000"

    // create tcp connection
    val actorSys = ActorSystem.create("MyActorSys")
    val tcpActor = actorSys.actorOf(AkkaTcpClient.props(remote.getHost, 9418))
    Thread.sleep(1000)
    implicit val timeout = Timeout(new DurationInt(5).seconds)

    val future = (tcpActor ? AkkaTcpClient.SendMessage(ByteString(payload))).mapTo[String]
    tcpActor ! AkkaTcpClient.SendMessage(ByteString(flush))
    ZIO.succeed(future)
  }

  val testRemote = Remote("127.0.0.1", Some(9418), "snake")

}


