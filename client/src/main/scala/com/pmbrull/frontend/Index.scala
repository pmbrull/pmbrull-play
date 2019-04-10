package com.pmbrull.frontend

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Index")
object Index {

  /**
    * Landing page view
    */
  @JSExport
  def view(): Unit = {
    dom.document.getElementById("content").appendChild(
      section(id := "title")(
        h1("Recent Posts")
      ).render
    )

    RecentPosts.view(RecentPosts.getOrderedPostList)
  }

  /**
    * Main always gets executed. Leave it empty
    * to have control over which views we load.
    */
  def main(args: Array[String]): Unit = {}

}
