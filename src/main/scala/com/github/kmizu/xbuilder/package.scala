package com.github.kmizu
import language.dynamics
import scala.xml._
import scala.util.DynamicVariable

package object xbuilder {
  object % extends Dynamic {
    private[this] val scope = new DynamicVariable[NodeBuffer](new NodeBuffer)
    def buffer(content: => Any): NodeBuffer = {
      scope.withValue(new NodeBuffer) {
        content
        scope.value
      }
    }
    def applyDynamic(name: String)(content: => Any): Node = {
      val evaledContent = content
      if(name == "text" && evaledContent.isInstanceOf[String]) {
        val text = Text(evaledContent.asInstanceOf[String])
        scope.value += text
        text
      } else {
        this.applyDynamicNamed(name)(Seq():_*)(content)
      }
    }
    def selectDynamic(name: String): Node = applyDynamic(name)(null)
    def applyDynamicNamed(name: String)(attributes: (String, String)*)(block: => Any): Elem = {
      def createMetaData(attributeList: List[(String, String)]): MetaData = attributeList match {
        case Nil => Null
        case (key, value) :: xs => new UnprefixedAttribute(key, value, createMetaData(xs))
      }
      var attributeList : List[(String, String)] = attributes.toList.reverse
      var metadata: MetaData = createMetaData(attributeList)
      val newElement = scope.withValue(new NodeBuffer) {
        block
        Elem(null, name, metadata, TopScope, true, scope.value.toSeq:_*)
      }
      scope.value += newElement
      newElement
    }
  }
}
