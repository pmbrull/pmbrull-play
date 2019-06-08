package com.pmbrull.frontend.posts.deepLearning

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object IntroductionToPyTorch extends PostTemplate {

  val title = "Introduction to PyTorch"
  val date = new Date(2019, 8, 6)
  val category = "Deep Learning"
  val description: String =
    """
      I recently started Udacity's Secure and Private AI course, so I will try to write down some posts with my personal
      notes about the course to keep everything organized! First chapter is a brief but pretty complete introduction
      to python's deep learning framework PyTorch. I am really enjoying the course, so I encourage you to take the time
      to give it a go!
    """.stripMargin


  def buildBody(title: String): Element = {
    section(id := "IntroductionToPyTorch")(
      h1(title),
      p(cls := "top-padding")(description),
      p("You can find all the information about the course",
        a(cls := "annotation px-2", href := "https://eu.udacity.com/course/secure-and-private-ai--ud185")("here.")
      ),
      p(
        """Deep learning has been around for quite a while. However, it just looks like it appeared now. This is because
          |of the evolution undergone by hardware components these last few years. As deep learning techniques require
          |huge amounts of computational power, people could start applying it once this power became available for
          |a much lower price.
          |""".stripMargin
      ),
      p(
        """The whole idea behind neural networks is to mimic how a brain works. At any given moment we are processing
          |tons of information, but just a part of this information is actually valuable. We are able to respond to
          |any important impulse or stimulation because we are granting it a higher weight than the one we give to the
          |rest of the noise we usually perceive. The most simple model that represents this behavior is the one
          |consisting in just one neuron, called """.stripMargin, b("perceptron")
      ),
      div(cls := "top-padding", style := "width:100%; text-align:center")(
        img(src := "/assets/images/deep-learning/introduction-to-pytorch/perceptron.png", alt := "perceptron",
          cls := "w-full")
      ),
      p(cls := "top-padding")(
        """As shown in the figure above, we are just giving some weights to our incoming features, summimg them up
          |and finally applying what is called an""".stripMargin, b("activation function"), """. Mathematically, it
          |looks like this:
          |""".stripMargin
        ),
      p(
        """
          |$$ y = f \left( \sum_i w_i x_i + b \right), $$
        """.stripMargin),
      p(
        """or what the same, apply the activation function to the dot product of two vectors with the sum of a
           |bias factor. With PyTorch we use this second abstraction, where we can represent both features and
           |weights as """.stripMargin, b("tensors"), """, which are vectors of any arbitrary dimension.
        """.stripMargin),



      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
