package com.tcpClient

import java.net.InetSocketAddress
import akka.actor.{Actor, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString

object AkkaTcpClient {
  def props(host: String, port: Int) =
    Props(classOf[AkkaTcpClient], new InetSocketAddress(host, port))

  final case class SendMessage(message: ByteString)
  final case class Ping(message: String)
}

class AkkaTcpClient(remote: InetSocketAddress) extends Actor {
  import akka.io.Tcp._
  import context.system

  println("Connecting to " +  remote.toString)

  val manager = IO(Tcp)
  manager ! Connect(remote)

  override def receive: Receive = {
    case CommandFailed(con: Connect) =>
      println("Connection failed")
      println(con.failureMessage.toString)
      context stop self

    case c @ Connected(remote, local) =>
      println(s"Connection to $remote succeeded")
      val connection = sender
      connection ! Register(self)

      context become {
        case AkkaTcpClient.SendMessage(message) =>
          println("Sending message: " + message.utf8String)
          connection ! Write(message)
        case AkkaTcpClient.Ping(message) =>
          println("hello: " + message)
        case data: ByteString =>
          println("Sending request data: " + data.utf8String)
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          println("Failed to write request.")
          println(w.failureMessage.toString)
        case Received(data) =>
          println("Received response.")
          println("data: " + data.utf8String)
          connection ! data.utf8String
        case "close" =>
          println("Closing connection")
          connection ! Close
        case _: ConnectionClosed =>
          println("Connection closed by server.")
          context stop self
      }
        case _ =>
          println("Something else is happening")

  }
}
