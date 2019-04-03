package com.pmbrull.frontend

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.JSExport

@JSExport
object PostView {

  @JSExport
  def main(name: String): Unit = {

    name match {
      case "hola" =>
        dom.document.getElementById("content").appendChild(
          section(id:="post")(
            h2(s"Welcome to $name page!")
          ).render
        )
      case _ => Error.ErrorView
    }

  }

}
