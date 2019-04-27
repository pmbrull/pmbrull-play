package com.pmbrull.frontend

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("AboutView")
object AboutView {

  @JSExport
  def view(): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id := "About")(
        h1("About"),
        getCard(),
        getContent()
      ).render
    )
  }

  def getCard(): TypedTag[Element] = {
    div(cls := "my-2 mx-auto p-relative bg-white shadow-1 card blue-hover")(
      div(cls := "top-padding", style := "width:100%; text-align:center")(
        img(src := "/assets/images/bio.jpg", alt := "bio",
            cls := "w-full", style := "border-radius: 50%; width: 35%;")
      ),
      div(cls := "px-2 py-2")(
        p(cls := "mb-1 small font-weight-medium text-uppercase mb-1 text-muted lts-2px")(
          "MATHEMATICIAN & DATA ENTHUSIAST"
        ),
        h1(cls := "ff-serif font-weight-normal text-black card-heading mb-2", style := "line-height: 1.25;")(
          "PMBRU\\\\"
        ),
        p(cls := "mb-1")("Located in Barcelona"),
        p(cls := "mb-1")("Big Data Developer at ",
          a(style := "text-decoration: underline;", href := "https://bcntdc.zurich.com/en/about")(
            "ServiZurich"
          )
        ),
        div(cls := "annotation-block mt-2")(
          a(cls := "annotation px-2", href := "https://github.com/pmbrull/")(
            i(cls := "fab fa-github")
          ),
          a(cls := "annotation px-2", href := "https://linkedin.com/in/pere-miquel-brull-borràs")(
            i(cls := "fab fa-linkedin")
          ),
          a(cls := "annotation px-2", href := "https://twitter.com/perembrull")(
            i(cls := "fab fa-twitter")
          )
        )
      )
    )
  }

  def getContent(): TypedTag[Element] = {
    div(cls := "top-padding")(
      p(
        """I find it impossible to stay still or with my head quiet. I love learning and
          |keeping my hands busy, usually coding. That’s why I started this blog, to be
          |able to share everything I spend time reading, trying to understand and applying
          |either because I find it useful or cool. Here you will mainly find Mathematics,
          |Machine Learning, Deep Learning, Big Data and Data Science posts.""".stripMargin
      ),
      div(cls := "top-padding")(
        h2("Credits"),
        p("""Some graphic functions implemented in python are extracted from Python Data Science Handbook.
          |Amazing material!""".stripMargin)
      )

    )
  }

}
