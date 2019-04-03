package com.pmbrull.frontend.Index

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js

object Index {

  def main(args: Array[String]): Unit = {

    dom.document.getElementById("index").appendChild(
              section(id:="index")(
                h2(args),
                h2("Welcome to the Landing Page!")
              ).render
            )
//    print("ARGS")
//    print(args)
//    print("Soc a l'index")
//
//
//    val url = args(0)
//
//    url match {
//      case "" =>
//        dom.document.getElementById("content").appendChild(
//          section(id:="index")(
//            h2("Welcome to the Landing Page!")
//          ).render
//        )
//
//      case "hola" =>
//        dom.document.getElementById("content").appendChild(
//        section(id:="hola")(
//          h2("I AM HOLAAAAAAAAAAa")
//        ).render
//      )
//
//      case _ => dom.document.body.appendChild(
//        section(id:="error")(
//          h2("something went wrong")
//        ).render
//      )
//
//    }

  }

}
