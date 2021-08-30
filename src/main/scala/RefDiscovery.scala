
/*
gitProtoRequest :: String -> String -> String
gitProtoRequest host repo = pktLine $ "git-upload-pack /" ++ repo ++ "\0host="++host++"\0"

pktLine :: String -> String
pktLine msg = printf "%04s%s" (toHex . (4 +) $ length msg) msg
*/

object RefDiscovery extends App {
  def pktLine(msg: String): String = {
    val hex = (4 + msg.length)
    f"$hex%04x$msg%s" //(toHex . (4 +) $ length msg) msg
  }

  def gitProtoRequest(host: String)(repo: String): String = host
}


