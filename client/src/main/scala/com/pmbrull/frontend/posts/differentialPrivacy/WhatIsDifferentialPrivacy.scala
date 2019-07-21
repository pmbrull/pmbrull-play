package com.pmbrull.frontend.posts.differentialPrivacy

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object WhatIsDifferentialPrivacy extends PostTemplate {

  val title = "What is Differential Privacy"
  val date = new Date("2019-06-17") // any-mes-dia
  val category = "Differential Privacy"
  val description: String =
    """
      |Following up with Udacity's Secure and Private AI course, we start our journey towards diving into
      |Differential Privacy.
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "DifferentialPrivacy")(
      h1(title),
      p(cls := "top-padding")(description),
      p(
        """
          |
        """.stripMargin),



      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
