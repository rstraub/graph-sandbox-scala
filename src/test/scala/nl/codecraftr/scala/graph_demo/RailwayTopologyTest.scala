package nl.codecraftr.scala.graph_demo

import org.scalatest.AppendedClues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalax.collection.immutable.Graph

class RailwayTopologyTest extends AnyFlatSpec with Matchers with AppendedClues {
  "topology" should "be built" in {
    val topology = buildBasicTopology

    topology.isConnected shouldBe true withClue "nodes should not be isolated"
    topology.isComplete shouldBe false withClue "nodes should not have connections to each other node"
    topology.isDirected shouldBe false withClue "topology can only have undirected edges"
  }

  /*
       100      200      100
   (1) --- (2) ---- (5) --- (6)
          50 \      / 50
             (3)--(4)
               100
   */
  private def buildBasicTopology: Graph[TrackNode, TrackEdge] = {
    val node1 = TrackNode("1", "28W")
    val node2 = TrackNode("2", "29W")
    val node3 = TrackNode("3", "12Z")
    val node4 = TrackNode("4", "13Z")
    val node5 = TrackNode("5", "5Y")
    val node6 = TrackNode("6", "6y")

    val nodes = Set(
      node1,
      node2,
      node3,
      node4,
      node5,
      node6
    )

    val edges = Set(
      TrackEdge(node1, node2, 100),
      TrackEdge(node2, node5, 200),
      TrackEdge(node5, node6, 100),
      TrackEdge(node2, node3, 50),
      TrackEdge(node3, node4, 100),
      TrackEdge(node4, node5, 50)
    )

    val g: Graph[TrackNode, TrackEdge] = Graph.from(nodes, edges)
    g
  }
}
