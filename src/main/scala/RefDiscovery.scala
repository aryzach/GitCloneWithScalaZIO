
/*
gitProtoRequest :: String -> String -> String
gitProtoRequest host repo = pktLine $ "git-upload-pack /" ++ repo ++ "\0host="++host++"\0"

pktLine :: String -> String
pktLine msg = printf "%04s%s" (toHex . (4 +) $ length msg) msg
*/

object RefDiscovery extends App {
  def pktLine(msg: String): String = msg

  def gitProtoRequest(host: String)(repo: String): String = host
}


