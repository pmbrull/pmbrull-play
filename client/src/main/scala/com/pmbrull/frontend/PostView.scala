package com.pmbrull.frontend

import com.pmbrull.frontend.posts.deepLearning.{IntroductionToPyTorch, IntroductionToPyTorchIII, IntroductionToPyTorchIV, IntroductionToPyTorchV, IntroductionToPytorchII}
import com.pmbrull.frontend.posts.sparkSeries.PredictingRiskOfCancerKNN
import org.scalajs.dom
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import shared.Utils
import posts._

@JSExportTopLevel("PostView")
object PostView {

  @JSExport
  def view(name: String): Unit = {

    name match {
      case x if x == Utils.stringToUrl(SomeTestTitle.title) => addDom(SomeTestTitle.getPost.body)
      case x if x == Utils.stringToUrl(PredictingRiskOfCancerKNN.title) => addDom(PredictingRiskOfCancerKNN.getPost.body)
      case x if x == Utils.stringToUrl(IntroductionToPyTorch.title) => addDom(IntroductionToPyTorch.getPost.body)
      case x if x == Utils.stringToUrl(IntroductionToPytorchII.title) => addDom(IntroductionToPytorchII.getPost.body)
      case x if x == Utils.stringToUrl(IntroductionToPyTorchIII.title) => addDom(IntroductionToPyTorchIII.getPost.body)
      case x if x == Utils.stringToUrl(IntroductionToPyTorchIV.title) => addDom(IntroductionToPyTorchIV.getPost.body)
      case x if x == Utils.stringToUrl(IntroductionToPyTorchV.title) => addDom(IntroductionToPyTorchV.getPost.body)
      case _ => Error.ErrorView
    }
  }

  def addDom(post: Element): Node = {
    dom.document
      .getElementById(elementId = "content")
      .appendChild(post)
  }

}
