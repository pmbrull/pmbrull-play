package com.pmbrull.frontend.posts

import com.pmbrull.frontend.posts.deepLearning.{IntroductionToPyTorch, IntroductionToPyTorchIII, IntroductionToPytorchII}
import com.pmbrull.frontend.posts.sparkSeries.PredictingRiskOfCancerKNN

object _AllPosts {

  val postList = List(
//    SomeTestTitle,
    PredictingRiskOfCancerKNN,
    IntroductionToPyTorch,
    IntroductionToPytorchII,
    IntroductionToPyTorchIII
  )

  val categoryList: List[String] = postList.map(_.getPost.category).distinct

}
