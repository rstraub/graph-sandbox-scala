package nl.codecraftr.scala.graph_demo

import scalax.collection.immutable.Graph

/** Serves the purpose of encapsulating the technicalities of using a graph.
  */
case class Topology(nodes: Set[TrackNode], edges: Set[TrackEdge]) {
  private val graph: Graph[TrackNode, TrackEdge] = Graph.from(nodes, edges)

  def switches: Set[TrackNode] =
    graph.nodes
      .filter(_.degree >= 3)
      .map(nt => TrackNode(nt.id, nt.name))
      .toSet
}
