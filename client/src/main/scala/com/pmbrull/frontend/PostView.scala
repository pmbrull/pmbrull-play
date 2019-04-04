package com.pmbrull.frontend

import org.scalajs.dom
import org.scalajs.dom.html.Element

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import posts._

@JSExportTopLevel("PostView")
object PostView {

  @JSExport
  def view(name: String): Unit = {

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
