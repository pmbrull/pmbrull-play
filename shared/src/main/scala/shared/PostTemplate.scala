package shared

import org.scalajs.dom.html.Element
import scala.scalajs.js.Date

trait PostTemplate {

  def buildPost(title: String, date: Date,
              description: String): Post = {
    Post(
      title = title,
      date = date,
      description = description,
      body = buildBody(title)
    )
  }

  def buildBody(title: String): Element

  def getPost: Post

}
