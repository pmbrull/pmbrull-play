package com.pmbrull.frontend.Index

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

object Index {

  def main(args: Array[String]): Unit = {

    dom.document.getElementById("index").appendChild(
            section(id:="index")(
              h1("Recent posts")
            ).render
          )

  }

}
