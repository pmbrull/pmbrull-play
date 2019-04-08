package com.pmbrull.frontend

import com.pmbrull.frontend.FrontUtils.RecentPosts
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Index")
object Index {

  /**
    * Landing page view
    */
  @JSExport
  def view(): Unit = RecentPosts.view(RecentPosts.getOrderedPostList)

  /**
    * Main always gets executed. Leave it empty
    * to have control over which views we load.
    */
  def main(args: Array[String]): Unit = {}

}
