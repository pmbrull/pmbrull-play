package com.pmbrull.frontend.posts.deepLearning

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object IntroductionToPytorchII extends PostTemplate {

  val title = "Introduction to PyTorch II"
  val date = new Date("2019-06-10")
  val category = "Deep Learning"
  val description: String =
    """
      After a brief introduction about Deep Learning origins and how we can use PyTorch to create neural networks, we
      still need to prepare a model that learns from our data. What we prepared was just a carcass, therefore we yet
      have to develop the methods that convert the forward propagation we defined of the input data into actual
      information.
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "IntroductionToPyTorch")(
      h1(title),
      p(cls := "top-padding")(description),



      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
