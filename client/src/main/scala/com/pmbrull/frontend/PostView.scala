package com.pmbrull.frontend

import org.scalajs.dom
import org.scalajs.dom.html.Element
import scala.scalajs.js.annotation.JSExport

import posts._

@JSExport
object PostView {

  @JSExport
  def main(name: String): Unit = {

    name match {
      case "test" => addDom(Test.getPost.body)
      case _ => Error.ErrorView
    }
  }

  def addDom(post: Element) = {
    dom.document
      .getElementById(elementId = "content")
      .appendChild(post)
  }

}
