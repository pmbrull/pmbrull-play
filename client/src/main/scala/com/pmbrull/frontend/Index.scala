package com.pmbrull.frontend

import com.pmbrull.frontend.posts.AllPosts
import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._
import shared.{Post, Utils}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Index")
object Index {

  val postList: List[Post] = getOrderedPostList

  /**
    * Landing page view
    */
  @JSExport
  def view(): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id:="index")(
        h1("Recent posts"),
        div(cls := "top-padding")(
          for {
            post <- postList
          } yield postPreview(post)
        )
      ).render
    )

  }

  /**
    * Main always gets executed. Leave it empty
    * to have control over which views we load.
    */
  def main(args: Array[String]): Unit = {}

  /**
    * Get the last 10 published posts
    * @return List[Post] ordered by date
    */
  def getOrderedPostList: List[Post] = {
    AllPosts.postList
      .map(_.getPost)
      .sortBy(_.date.toDateString())
      .take(10)
  }

  def postPreview(post: Post) = {
    section(id := post.title)(
      div(cls := "top-padding")(

        h2(cls := "main-link")(
          a(post.title, href := Utils.getPostUrl(post))
        ),
        div(
          i(cls := "fa fas fa-folder-open annotation"),
          p(cls := "annotation annotation-title")("Category:"),
          p(cls := "annotation annotation-content")(post.category) // post.date.toLocaleDateString)
        ),
        p(post.description)
      )
    )
  }

}
