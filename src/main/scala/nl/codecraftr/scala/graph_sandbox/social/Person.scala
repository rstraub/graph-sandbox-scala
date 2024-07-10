package nl.codecraftr.scala.graph_sandbox.social

import scalax.collection.generic.{AbstractDiEdge, AnyEdge}

case class Person(name: String, age: Int)

sealed trait Relation extends AnyEdge[Person]

case class Parent(child: Person, parent: Person)
    extends AbstractDiEdge(source = child, target = parent)
    with Relation
