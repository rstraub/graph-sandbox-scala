package nl.codecraftr.scala.graph_demo

import scalax.collection.generic.AbstractUnDiEdge

case class TrackEdge(from: TrackNode, to: TrackNode, length: Int)
    extends AbstractUnDiEdge(source = from, target = to)
