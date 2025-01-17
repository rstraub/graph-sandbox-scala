package nl.codecraftr.scala.graph_sandbox.railway

import org.scalatest.{AppendedClues, OptionValues}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalax.collection.immutable.Graph

class TopologyTest
    extends AnyFlatSpec
    with Matchers
    with AppendedClues
    with OptionValues {
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

  /*
               100      200      100
           (1) --- (2) ---- (5) --- (6)
                  50 \      / 50
                     (3)--(4)
                       100
   */
  private val aTopology = Topology(nodes, edges)

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
    aTopology.switches shouldBe Set(node2, node5)
  }

  "neighbours" should "return all nodes connected to the given node" in {
    aTopology.neighbors(node2) shouldBe Set(node1, node3, node5)
  }

  it should "return all nodes given an outer node" in {
    aTopology.neighbors(node1) shouldBe Set(node2)
  }

  "pathValid" should "return true given a valid path" in {
    aTopology.validPath(node1, node2, node3) shouldBe true
  }

  it should "return false given invalid path" in {
    aTopology.validPath(node1, node3) shouldBe false
  }

  it should "return false given path in wrong order" in {
    aTopology.validPath(node1, node3, node2) shouldBe false
  }

  "disconnected topology" should "be converted into a connected one" in {
    val disconnectedEdges = Set(
      TrackEdgeDto("1", "2", 100),
      TrackEdgeDto("2", "3", 100)
    )

    Topology.of(Set(node1, node2, node3), disconnectedEdges) shouldBe
      Topology(Set(node1, node2, node3), Set(edge1, edge4))
  }

  /*
                       A                         B

              (1) --- (2) ---- (5)  |           (5) --- (6)
                        \           |           /
                        (3)         |   (3)--(4)

                                ------ +

                                Joined

                    (1) --- (2) ---- (5) --- (6)
                              \      /
                              (3)--(4)
   */
  "joining topologies" should "be when they are connected" in {
    val topologyA =
      Topology(Set(node1, node2, node3, node5), Set(edge1, edge2, edge4))
    val topologyB =
      Topology(Set(node3, node4, node5, node6), Set(edge5, edge6, edge3))

    val joined = topologyA.join(topologyB)

    joined.value shouldBe aTopology
  }

  /*
                       A                  B
                  (1) --- (2)    |     (5) --- (6)
                            \    |     /
                            (3)  |   (4)
   */
  it should "not be possible when they are disconnected" in {
    val topologyA = Topology(Set(node1, node2, node3), Set(edge1, edge4))
    val topologyB = Topology(Set(node5, node6, node4), Set(edge3, edge6))

    topologyA.join(topologyB) shouldBe None
  }
}
