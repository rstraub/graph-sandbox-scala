package nl.codecraftr.katabase.scala.inspect

import scala.quoted.*

object AnnotationInspector:
  inline def getAnnotations[A]: List[String] = ${ getAnnotationsImpl[A] }

  private def getAnnotationsImpl[A: Type](using Quotes): Expr[List[String]] = {
    import quotes.reflect.*
    val annotations = TypeRepr.of[A].typeSymbol.annotations.map(_.tpe.show)
    Expr.ofList(annotations.map(Expr(_)))
  }
