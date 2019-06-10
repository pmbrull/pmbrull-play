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
  val date = new Date("2019-06-8")
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
           |weights as """.stripMargin, b("tensors"), """, which are vectors of any arbitrary dimension. As with
           |pandas, we can use """.stripMargin, p(cls := "icode")("tensor.shape"), """ to recover the dimensions of
           |the tensor. Moreover in order to being able to correctly operate with the tensors and prepare a 1D input
           |for our input layer, we sometimes may need to change the shape of our data:
        """.stripMargin),
      ul(
        li(p(cls := "icode")("tensor.view(a, b)"), p(style := "display: inline;")(""" will create a new tensor, containing the same data but
            |arranged with the new dimensions specified.
          """.stripMargin)
        ),
        li("As we may not always care about immutability, we can also change the shape of a tensor in-place with ",
          p(cls := "icode")("tensor.resize_(a, b)"),
            """
              |The underscore at the end of the function marks that the method will be applied onto the same variable.
            """.stripMargin
          ),
        li(p(cls := "icode")("tensor.reshape(a, b)"),
          """ is slightly different than view(), but usually gives the same result. More information about this
            |can be found""".stripMargin,
          a(cls := "annotation px-2", href := "https://stackoverflow.com/questions/49643225/whats-the-difference-between-reshape-and-view-in-pytorch")("here.")
         )
      ),
      p(
        """The whole usage behind neural networks relies on building any kind of function generalization by
          |composing simple algebraic computations as the one showed in the perceptron. Using more than one
          |neuron at the same time gives us layers, and the usage of multiple layers derives in the concept of
          |deep learning. We can divide the layers in three main groups:
          |""".stripMargin),
      ol(
        li("Input layer - takes in the data."),
        li("Hidden layers - Possible multiple layers transforming the data sequentially."),
        li("Output layer - Returning a result that we can then transform into, for example, a given classification probability.")
      ),
      p("""
          |If we state that any model can be perfectly defined by a given function $f$, we just need to be able to get the best approximation
          |$\hat{f}$ possible to this function. However, $f$ will rarely be linear, so we need to use
          |specific activation functions that help us to get as close as possible to non-linear functions. This means
          |that these activation functions are requires to be non-linear too. A common activation function to apply
          |to the hidden layers is the ReLU function - Rectified Linear Unit:
          |
          |$$
          |f(x)  = \begin{cases}
          |    x & \mbox{if } x > 0 \\
          |    0 & \mbox{otherwise}
          |\end{cases}
          |$$
          |
          |Now we are ready to prepare our first model. Let's follow the courses example on classifying the MNIST digit data:
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """import torch.nn.functional as F
          |
          |class Network(nn.Module):
          |    def __init__(self):
          |        super().__init__()
          |        # Defining the layers, 128, 64, 10 units each
          |        self.fc1 = nn.Linear(784, 128)
          |        self.fc2 = nn.Linear(128, 64)
          |        # Output layer, 10 units - one for each digit
          |        self.fc3 = nn.Linear(64, 10)
          |
          |    def forward(self, x):
          |        ''' Forward pass through the network, returns the output logits '''
          |
          |        x = self.fc1(x)
          |        x = F.relu(x)
          |        x = self.fc2(x)
          |        x = F.relu(x)
          |        x = self.fc3(x)
          |        x = F.softmax(x, dim=1)
          |
          |        return x""".stripMargin)
      ),
      p(
        "Then, if we wanted to get the outputs of passing some data through our network, we would just need to call ",
        p(cls := "icode")("model.forward(data)"), " or the equivalent ", p(cls := "icode")("model(data)"),
          """. Note how in this case we are using a Softmax activation function for the outputs, as we want to get
            |a probability for all observations to belong to any of the ten available target classes. We use the
            |parameter """.stripMargin, p(cls := "icode")("dim=1"), """ so that the columns, which are the
            |classes probabilities, sum a total of 1, not the rows. However, this notation for the model creation
            |requires quite a bit of boilerplate. We can use PyTorch's Sequential function
            |for a more convenient approach:
            |""".stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
      """# Hyperparameters for our network
        |input_size = 784
        |hidden_sizes = [128, 64]
        |output_size = 10
        |
        |# Build a feed-forward network
        |model = nn.Sequential(nn.Linear(input_size, hidden_sizes[0]),
        |                      nn.ReLU(),
        |                      nn.Linear(hidden_sizes[0], hidden_sizes[1]),
        |                      nn.ReLU(),
        |                      nn.Linear(hidden_sizes[1], output_size),
        |                      nn.Softmax(dim=1))
      """.stripMargin)
      ),
      p(
        """Finally, the same result can be obtained using dictionaries, so for more complex networks, we can access
          |any layer directly by name:
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """from collections import OrderedDict
          |model = nn.Sequential(OrderedDict([
          |                      ('fc1', nn.Linear(input_size, hidden_sizes[0])),
          |                      ('relu1', nn.ReLU()),
          |                      ('fc2', nn.Linear(hidden_sizes[0], hidden_sizes[1])),
          |                      ('relu2', nn.ReLU()),
          |                      ('output', nn.Linear(hidden_sizes[1], output_size)),
          |                      ('softmax', nn.Softmax(dim=1))]))""".stripMargin)
      ),


      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
