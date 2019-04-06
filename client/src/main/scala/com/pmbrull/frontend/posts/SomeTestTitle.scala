package com.pmbrull.frontend.posts

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
      |BLA bla bla uiuiui lorem ipsum
      |hola
      |holis
    """.stripMargin


  def buildBody(title: String): Element = {
    section(id := "TestPost")(
      h1(title),
      p(date.toLocaleDateString),
      p("somebody once told me...")
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
