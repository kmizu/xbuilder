package com.github.kmizu.xbuilder

import org.scalatest.FunSpec
import scala.xml._
import language.dynamics

class XBuilderSpec extends FunSpec {
  describe("XBuilder example") {
    it("text") {
      assertResult(Text("Foo"))(% text "Foo")
    }
    it("<project></project>") {
      val element = %.project {
      }
      assertResult(element)(<project/>)
    }
    it("""<project name="xbuilder"""") {
      val element = %.project(name="xbuilder") {

      }
      assertResult(element)(<project name="xbuilder"/>)
    }
    it("""<project><license></license></project""") {
      val element = %project {
        %license {

        }
      }
      assertResult(element)(<project><license></license></project>)
    }
  }
}
