package com.github.kmizu
import language.dynamics
import scala.xml._
import scala.util.DynamicVariable

package object xbuilder {
  object % {
    private[this] val scope = new DynamicVariable[NodeBuffer](null)
    def applyDynamic(label: String)(content: String): Unit = {
      if(label == "text") {
        val text = Text(content)
        scope.value += text
      } else {
        throw new IllegalArgumentException(s"${label} must be text")
      }
    }
    def applyDynamicNamed(label: String)(attributes: (String, String)*): Node = {
      var attributeList : List[(String, String)] = attributes.toList.reverse
      var metadata: MetaData = null
      while(!attributeList.isEmpty) {
        val (name, value) = attributeList.head
        metadata = new UnprefixedAttribute(name, value, metadata)
        attributeList = attributeList.tail
      }
      ???
    }
  }
}
