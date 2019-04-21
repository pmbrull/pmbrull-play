package com.pmbrull.frontend.FrontUtils

import com.pmbrull.frontend.posts.AllPosts
import org.scalajs.dom
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._
import shared.Utils.stringToUrl
import shared.{Post, Utils}

import scala.scalajs.js.Date

object RecentPosts {

  /**
    * Landing page view
    */
  def view(postList: List[Post]): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id:="recent-posts")(
        div(cls := "top-padding")(
          for {
            post <- postList
          } yield postPreview(post)
        )
      ).render
    )

  }

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

  /**
    * For each post, prepare the preview that will contain post title, link,
    * description and category + update annotation.
    *
    * @param post Post to be shown
    * @return Section for the post
    */
  def postPreview(post: Post): TypedTag[Element] = {
    section(id := post.title)(
      div(cls := "top-padding")(
        a(href := Utils.getPostUrl(post))(
          h2(cls := "main-link")(post.title)
        ),
        div(
          postCategoryAnnotation(post.category),
          postUpdateAnnotation(post.date)
        ),
        p(post.description)
      )
    )
  }

  /**
    * Post preview in card shape
    *
    * @param post Post to be shown
    * @return Section for the post
    */
  def postCardPreview(post: Post): TypedTag[Element] = {
    section(id := post.title)(
      div(cls := "my-2 mx-auto p-relative bg-white shadow-1 card blue-hover blue-card")(
        a(cls:= "top-padding", href := Utils.getPostUrl(post))(
          h2(cls := "mt-0 mb-1")(post.title)
        ),
        div(cls := "px-2 py-2")(
          postCategoryAnnotation(post.category),
          postUpdateAnnotation(post.date)
        ),
        p(post.description)
      )
    )
}

  /**
    * Prepares the category annotation for the recent posts listing
    *
    * @param category Post category
    * @return Div with font-awesome logo, title and content for the annotation
    */
  def postCategoryAnnotation(category: String): TypedTag[Element] = {
    div(cls := "annotation-block")(
      i(cls := "fa fas fa-folder-open annotation"),
      p(cls := "annotation annotation-title")("Category: "),
      a(cls := "btn btn-outline-secondary btn-sm annotation annotation-content",
        href := "/" + stringToUrl(category))(category)
    )
  }

  /**
    * Prepare the update annotation for the recent posts listing
    *
    * @param date Post updating date
    * @return Div with font-awesome logo, title and content for the annotation
    */
  def postUpdateAnnotation(date: Date): TypedTag[Element] = {
    div(cls := "annotation-block")(
      i(cls := "fa fa-calendar-alt annotation"),
      p(cls := "annotation annotation-title")("Updated: "),
      p(cls := "annotation annotation-content")(date.toLocaleDateString())
    )
  }
}
