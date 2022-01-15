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

import zio._
import zio.clock._
import zio.console._
import zio.nio.channels._
import zio.nio._
import zio.nio.core._
import scala.concurrent.duration._

import java.net._
import java.io._
import scala.io._

sealed case class Remote(getHost: String, getPort: Option[Int], getRepo: String)

object RefDiscovery {

  def pktLine(msg: String): String = {
    val hex = (4 + msg.length)
    f"$hex%04x$msg%s" 
  }

  // questionable \u0000 here. Need to look it up when have internet (vs haskells \0)
  def gitProtoRequest(host: String)(repo: String): String = pktLine("git-upload-pack /" ++ repo ++ "\u0000host="++host++"\u0000")
  val testRemote = Remote("127.0.0.1", Some(9418), "pure-stocks")

  def lsRemote(remote: Remote): Unit = {
    val payload: String = gitProtoRequest(remote.getHost)(remote.getRepo)
    val flush = "\u0000"

    val s = new Socket(java.net.InetAddress.getByName("localhost"), 9418)
    lazy val in = new BufferedSource(s.getInputStream())
    in.getLines()


    val out = new PrintStream(s.getOutputStream())

    out.println(payload)
    out.flush()
    println("Received: " + in.next())

    s.close()

    return ()
  }

  /*
  val clientM: Managed[Exception, AsynchronousSocketChannel] = {
    AsynchronousSocketChannel()
      .mapM { client =>
        for {
          host    <- InetAddress.localHost
          address <- InetSocketAddress.inetAddress(host, 9418)
          _       <- client.connect(address)
        } yield client
      }
  }


  def write(s: String) = {
    val bb: ByteBuffer = ByteBuffer.wrap(buffer) ByteBuffer.wrap(s.toCharArray.map { _.toByte })
    return clientM.map(c => c.write(bb)) 
  }
  */


}

