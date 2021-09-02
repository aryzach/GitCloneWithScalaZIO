import zio.test._
import zio.test.Assertion._
import zio.test.environment._

import com.refDiscovery._


object RefDiscoverySpec extends DefaultRunnableSpec {
  def spec = suite("RefDiscoverySpec")(
    test("pktLine") {
      val output = RefDiscovery.pktLine("a\n")
      assert(output)(equalTo("0006a\n"))
    },

    test("gitProtoRequest") {
      val output = RefDiscovery.gitProtoRequest("localhost")("testrepo")
      assert(output)(equalTo("002dgit-upload-pack /testrepo\u0000host=localhost\u0000")) 
    },

    test("lsRemote") {
      val output = RefDiscovery.lsRemote(Remote("testHost")(Some(1))("testrepo")) 
      assertM(output)(equalTo("IO[Remote]??????")) 
    }

    )
}

