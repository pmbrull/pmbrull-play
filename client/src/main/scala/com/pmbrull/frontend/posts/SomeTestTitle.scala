package com.pmbrull.frontend.posts

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object SomeTestTitle extends PostTemplate {

  val title = "Some test title"
  val date = new Date(2019, 5, 3)
  val category = "posts"
  val description: String =
    """
      I am a description
    """.stripMargin


  def buildBody(title: String): Element = {
    section(id := "TestPost")(
      h1(title),
      p("somebody once told me..."),
      pre(code(cls := "C#")("""public ActionResult Index()
                              |    {
                              |    return View();
                              |    }""".stripMargin)),
      pre(code(cls := "Scala")("case class Hola(ketal: String)")),
      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
