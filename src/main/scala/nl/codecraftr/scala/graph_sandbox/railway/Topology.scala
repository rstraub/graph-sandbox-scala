package nl.codecraftr.scala.graph_sandbox.railway

import scalax.collection.immutable.Graph

/** Serves the purpose of encapsulating the technicalities of using a graph.
  */
case class Topology(nodes: Set[TrackNode], edges: Set[TrackEdge]) {
  private val graph: Graph[TrackNode, TrackEdge] = Graph.from(nodes, edges)
  private type ConnectedNode = Topology.this.graph.NodeT

  def switches: Set[TrackNode] =
    graph.nodes
      .filter(_.degree >= 3)
      .map(parseNode)
      .toSet

  def neighbors(ofNode: TrackNode): Set[TrackNode] =
    graph.nodes
      .find(ofNode)
      .map(_.neighbors)
      .getOrElse(Set.empty)
      .map(parseNode)

    def validPath(nodes: TrackNode*): Boolean =
      connectedNode(nodes.head) shortestPathTo connectedNode(nodes.last) match {
        case Some(path) => path.nodes.map(parseNode).toList == nodes.toList
        case None       => false
      }

  private def connectedNode(node: TrackNode): ConnectedNode = graph.get(node)

  private def parseNode(n: ConnectedNode) = TrackNode(n.id, n.name)
}

object Topology {
  def of(
      nodes: Set[TrackNode],
      disconnectedEdges: Set[TrackEdgeDto]
  ): Topology = {
    val connectedEdges = disconnectedEdges.flatMap { e =>
      for {
        from <- nodes.find(_.id == e.from)
        to <- nodes.find(_.id == e.to)
      } yield TrackEdge(from, to, e.length)
    }

    Topology(nodes, connectedEdges)
  }
}
