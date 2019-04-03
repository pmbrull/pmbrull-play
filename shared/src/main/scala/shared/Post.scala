package shared


case class Post(
               title: String,
               date: java.sql.Date,
               description: String,
               body: org.scalajs.dom.html.Element
               )
