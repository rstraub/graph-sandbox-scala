package nl.codecraftr.scala.graph_demo

import org.scalatest.AppendedClues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalax.collection.immutable.Graph

class RailwayTopologyTest extends AnyFlatSpec with Matchers with AppendedClues {
  private val node1 = TrackNode("1", "28W")
  private val node2 = TrackNode("2", "29W")
  private val node3 = TrackNode("3", "12Z")
  private val node4 = TrackNode("4", "13Z")
  private val node5 = TrackNode("5", "5Y")
  private val node6 = TrackNode("6", "6y")
  private val nodes: Set[TrackNode] = Set(
    node1,
    node2,
    node3,
    node4,
    node5,
    node6
  )

  private val edge1 = TrackEdge(node1, node2, 100)
  private val edge2 = TrackEdge(node2, node5, 200)
  private val edge3 = TrackEdge(node5, node6, 100)
  private val edge4 = TrackEdge(node2, node3, 50)
  private val edge5 = TrackEdge(node3, node4, 100)
  private val edge6 = TrackEdge(node4, node5, 50)
  private val edges: Set[TrackEdge] = Set(
    edge1,
    edge2,
    edge3,
    edge4,
    edge5,
    edge6
  )

  private val topology = Topology(nodes, edges)

  /*
                 100      200      100
             (1) --- (2) ---- (5) --- (6)
                    50 \      / 50
                       (3)--(4)
                         100
   */
  private val graph: Graph[TrackNode, TrackEdge] =
    Graph.from(
      nodes,
      edges
    )

    /** Interesting idea to use these to validate topologies. A topology is a
      * graph with some more specific constraints
      */
  "graph" should "be built" in {
    graph.isConnected shouldBe true withClue "nodes should not be isolated"
    graph.isComplete shouldBe false withClue "nodes should not have connections to each other node"
    graph.isDirected shouldBe false withClue "topology can only have undirected edges"
  }

  "switches" should "return all nodes connecting three or more nodes" in {
    topology.switches shouldBe Set(node2, node5)
  }

  "neighbours" should "return all nodes connected to the given node" in {
    topology.neighbors(node2) shouldBe Set(node1, node3, node5)
  }

  it should "return all nodes given an outer node" in {
    topology.neighbors(node1) shouldBe Set(node2)
  }

  "paths" should "return possible paths connecting two nodes" ignore {
//    topology.paths(node2, node5) shouldBe Set(
//      List(node2, node5),
//      List(node2, node3, node4, node5)
//    )
  }
}
