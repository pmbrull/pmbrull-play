package com.pmbrull.frontend

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._
import shared.Utils

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("CategoryView")
object CategoryView {

  @JSExport
  def view(category: String): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id := "title")(
        h1(Utils.urlToString(category))
      ).render
    )

    RecentPosts.view(
      RecentPosts.getOrderedPostList.filter(p => Utils.stringToUrl(p.category) == category)
    )
  }

}
