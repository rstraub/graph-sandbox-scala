package nl.codecraftr.scala.graph_sandbox.social

import nl.codecraftr.scala.graph_sandbox.Person
import org.scalatest.AppendedClues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalax.collection.OuterImplicits._
import scalax.collection.edges.{UnDiEdge, UnDiEdgeImplicits}
import scalax.collection.immutable.Graph

class PersonGraphTest extends AnyFlatSpec with Matchers with AppendedClues {
  private val p = Person("John", 30)
  private val c = Person("Junior", 2)
  private val f = Person("Jane", 28)

  "graph" should "be made" in {
    val graph = Graph[Person, UnDiEdge](f ~ p, c ~ f, p ~ c)

    println(graph)
    println("--outgoing--")
    graph.get(p).outgoing.foreach(println)
    println("--incoming--")
    graph.get(p).incoming.foreach(println)

    println(s"connected to $p : ${graph.get(p).degree}")

    graph.isMixed shouldBe false withClue "graph is undirectional"
    graph.isCyclic shouldBe true withClue "graph is cyclic"
    graph.isConnected shouldBe true withClue "nodes are not connected"
    graph.isComplete shouldBe true withClue "graph is incomplete"
  }
}
