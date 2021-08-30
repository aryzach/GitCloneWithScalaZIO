class RefDiscoveryTest extends org.scalatest.funsuite.AnyFunSuite {
  test("RefDiscovery.pktLine") {
    assert(RefDiscovery.pktLine("a\n") === "0006a\n")
  }
}
