package com.pmbrull.frontend.posts.deepLearning

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object IntroductionToPyTorchIII extends PostTemplate {

  val title = "Introduction to PyTorch III"
  val date = new Date("2019-12-06") // any-dia-mes
  val category = "Deep Learning"
  val description: String =
    """
      This will be the third post on our PyTorch series, based on the notes taken from Udacity's Secure and Private AI
      course. By now we should already have a brief understanding on how neural network work and know how to create
      them using PyTorch framework. Therefore, we are ready to train a model and validate it on new data.
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "IntroductionToPyTorch")(
      h1(title),
      p(cls := "top-padding")(description),
      p(
        """
          |Here it may be interesting to draw the parallelism between neural networks and ensemble methods. Let's
          |take, for example, random forests. The underlying logic is that if using a single decision tree does not
          |give good enough results as it cannot really explain all different aspects of the data, why not use
          |multiple trees that become specialists in more focused characteristics. With neural nets the same happens
          |but with neurons.
        """.stripMargin
      ),
      p(
        """
          |Usually these ensemble methods work well with models that tend to overfitting, i.e., learning too much
          |from the input data and thus, generalization becomes harder. Moreover, there are more """.stripMargin,
        b("Regularization Methods"), """ such as """, b("Dropout"), """. This is an interesting technique, as it
          switches neurons off by a given probability, and thus it keeps the neurons from learning "too much".
        """.stripMargin
      ),




      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
