package nl.codecraftr.scala.graph_sandbox.railway

import scalax.collection.generic.AbstractUnDiEdge

case class TrackEdge(from: TrackNode, to: TrackNode, length: Int)
    extends AbstractUnDiEdge(source = from, target = to)
