package com.pmbrull.frontend

import com.pmbrull.frontend.FrontUtils.RecentPosts
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("CategoryView")
object CategoryView {

  @JSExport
  def view(category: String): Unit = RecentPosts.view(
    RecentPosts.getOrderedPostList.filter(_.category == category)
  )

}
