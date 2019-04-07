package com.pmbrull.frontend

import org.scalajs.dom
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import shared.Utils
import posts._

@JSExportTopLevel("PostView")
object PostView {

  @JSExport
  def view(name: String): Unit = {

    name match {
      case x if x == Utils.postTitleToUrl(SomeTestTitle.getPost) => addDom(SomeTestTitle.getPost.body)
      case _ => Error.ErrorView
    }
  }

  def addDom(post: Element): Node = {
    dom.document
      .getElementById(elementId = "content")
      .appendChild(post)
  }

}
