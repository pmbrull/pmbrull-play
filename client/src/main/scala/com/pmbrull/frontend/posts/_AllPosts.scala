package com.pmbrull.frontend.posts

import com.pmbrull.frontend.posts.sparkSeries.PredictingRiskOfCancerKNN

object _AllPosts {

  val postList = List(
    SomeTestTitle,
    PredictingRiskOfCancerKNN
  )

  val categoryList: List[String] = postList.map(_.getPost.category).distinct

}
