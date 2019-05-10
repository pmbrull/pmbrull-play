package com.pmbrull.frontend.posts

object _AllPosts {

  val postList = List(
    SomeTestTitle
  )

  val categoryList: List[String] = postList.map(_.category).distinct

}
