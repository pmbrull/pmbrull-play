package com.pmbrull.frontend

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

object Error {

  def ErrorView = {
    dom.document.getElementById("content").appendChild(
      section(id:="error")(
        h2(s"Page not found!")
      ).render
    )
  }

}
