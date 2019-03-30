package com.pmbrull.frontend.Index

import scala.scalajs.js
import js.Dynamic.{ global => g }

object Index {

  def main(args: Array[String]): Unit = {
    g.document.getElementById("scalajsShoutOut").textContent = "Speaking from ScalaJS"
  }

}
