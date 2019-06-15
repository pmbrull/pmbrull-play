package com.pmbrull.frontend.posts

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object SomeTestTitle extends PostTemplate {

  val title = "Test Title"
  val date = new Date("2019-06-10") // any-mes-dia
  val category = "Random category"
  val description: String =
    """
      I am a description
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "IntroductionToPyTorch")(
      h1(title),
      p(cls := "top-padding")(description),
      pre(cls := "top-padding")(code(cls := "python")(
        """""".stripMargin)
      ),



      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
