package shared


case class Post(
               title: String,
               date: scala.scalajs.js.Date,
               category: String,
               description: String,
               body: org.scalajs.dom.html.Element
               )
