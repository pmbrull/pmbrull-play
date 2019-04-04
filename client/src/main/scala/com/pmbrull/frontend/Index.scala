package com.pmbrull.frontend.Index

import com.pmbrull.frontend.posts.AllPosts
import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import shared.Post

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
        for {
          post <- postList
        } yield postPreview(post)
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
      h2(post.title),
      p(post.date.toDateString),
      p(post.description)
    )
  }

}
