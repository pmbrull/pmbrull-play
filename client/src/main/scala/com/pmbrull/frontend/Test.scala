package com.pmbrull.frontend

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.JSExport

@JSExport
object Test {

  @JSExport
  def main(name: String): Unit = {
    dom.document.getElementById("hola").appendChild(
      section(id:="hola")(
        h2(s"Welcome to $name page!")
      ).render
    )
  }
}
