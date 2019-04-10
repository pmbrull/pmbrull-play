package shared

object Utils {

  final val BASE_URL = "www.pmbrull.com"
  final val POSTS_URL = BASE_URL + "/posts"

  def getPostUrl(post: Post): String = post.category + "/" + stringToUrl(post.title)

  def stringToUrl(s: String): String = s.replaceAll(" ", "-").toLowerCase

  def urlToString(url: String): String = url
    .split("-")
    .map(s => s.head.toUpper + s.tail)
    .mkString(" ")

}
