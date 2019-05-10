package com.pmbrull.frontend

import com.pmbrull.frontend.posts._AllPosts.categoryList
import org.scalajs.dom
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._
import shared.Utils

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("CategoriesView")
object CategoriesView {

  @JSExport
  def view(): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id := "title")(
        h1("Categories"),
        div(cls := "top-padding")(
          for {
            category <- categoryList
          } yield categoryPreview(category)
        )
      ).render
    )
  }

  def categoryPreview(category: String): TypedTag[Element] = {
    div(cls := "top-padding")(
      a(href := "/" + Utils.stringToUrl(category))(
        h2(cls := "main-link")(category)
      ))
  }

}
