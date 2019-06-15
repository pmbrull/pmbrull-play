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
  val date = new Date("2019-06-12") // any-mes-dia
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
      h2("Overfitting"),
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
      h2("Model Validation"),
      p(
        """
          |Now we are going to see how to apply dropout to a layer and to validate our model on data it has not
          |seen before to check if the model is overfitting - model performance increases in training data
          |but decreases in validation data. A good performance parameter for classification is the accuracy, so
          |we will work on that, so let's prepare a a snippet to calculate the accuracy.
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """model = nn.Sequential(...,
          |                      nn.Dropout(p=0.2),
          |                      nn.Linear(...),
          |                      nn.LogSoftmax(dim=1))
          |
          |images, labels = next(iter(data))
          |# We can get the class probabilities with an exponential
          |ps = torch.exp(model(images))
          |
          |# Get highest k-values, with k=1
          |top_p, top_class = ps.topk(1, dim=1)
          |equals = top_class == labels.view(*top_class.shape)
          |accuracy = torch.mean(equals.type(torch.FloatTensor))
          |print(f'Accuracy: {accuracy.item()*100}%')""".stripMargin)
      ),
      p(
        """
          |This way we have defined the accuracy of our model as the percentage of observation classified correctly.
          |For the validation of the model, though, there are a couple of tweaks that we should keep in mind.""".stripMargin
      ),
      ul(
        li("""First, there is no need to calculate the gradients when passing the validation data through the net.
          |Thus, we will be turning of the autograd feature of PyTorch that automatically performs these calculations by
          |running """.stripMargin, p(cls := "icode")("torch.no_grad"), "."
          ),
        li(
          """Secondly, as we want to get the full results from the model, we should deactivate the neuron dropout.
            |For that, we can change the model behavior into training or evaluation mode with """.stripMargin,
          p(cls := "icode")("model.train()"), " and ",  p(cls := "icode")("model.eval()"), "."
        ),
      ),
      p("The final snippet for training and evaluating the model would look like this:"),
      pre(cls := "top-padding")(code(cls := "python")(
        """epochs = 30
          |steps = 0
          |
          |train_losses, test_losses = [], []
          |for e in range(epochs):
          |    running_loss = 0
          |    for images, labels in trainloader:
          |
          |        optimizer.zero_grad()
          |
          |        log_ps = model(images)
          |        loss = criterion(log_ps, labels)
          |        loss.backward()
          |        optimizer.step()
          |
          |        running_loss += loss.item()
          |
          |    else:
          |        test_loss = 0
          |        accuracy = 0
          |
          |        with torch.no_grad():
          |            model.eval()
          |            for images, labels in testloader:
          |                log_ps = model(images)
          |                test_loss += criterion(log_ps, labels)
          |
          |                ps = torch.exp(log_ps)
          |                top_p, top_class = ps.topk(1, dim=1)
          |                equals = top_class == labels.view(*top_class.shape)
          |                accuracy += torch.mean(equals.type(torch.FloatTensor))
          |
          |        model.train()
          |
          |        train_losses.append(running_loss/len(trainloader))
          |        test_losses.append(test_loss/len(testloader))
          |
          |        print("Epoch: {}/{}.. ".format(e+1, epochs),
          |              "Training Loss: {:.3f}.. ".format(running_loss/len(trainloader)),
          |              "Test Loss: {:.3f}.. ".format(test_loss/len(testloader)),
          |              "Test Accuracy: {:.3f}".format(accuracy/len(testloader)))""".stripMargin)
      ),
      p(
        """
          |Where we are applying the exponential to recover the probabilities as we suppose that a LogSoftmax is
          |defined in the output layer.
        """.stripMargin),



      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
