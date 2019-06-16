package com.pmbrull.frontend.posts.deepLearning

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object IntroductionToPyTorchVI extends PostTemplate {

  val title = "Introduction to PyTorch VI"
  val date = new Date("2019-06-16") // any-mes-dia
  val category = "Deep Learning"
  val description: String =
    """
      Finally we have reached the last chapter on this PyTorch notes from Udacity's Secure and Private AI course, where
      we have had a brief introduction on just a few tools that are already provided by this framework, but there is
      yet to use one last ingredient that usually becomes a must. Training models is hard, mostly because getting the
      right data is not an easy task and with DL models we require huge computational power and time. But if we can
      just save some results, how about using pre-trained models?
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "IntroductionToPyTorch")(
      h1(title),
      p(cls := "top-padding")(description),
      h2("Transfer Learning"),
      p(
        """
          |If we are training a net for the first time, it is just an empty carcass that still has to understand all
          |basic aspects from an image: shapes, colours, edges... And it is from that knowledge when it can start
          |differentiating correctly our classes. However, why not skip this first step and jump directly to the
          |tuning of features that are actually useful for our business? The idea behind """.stripMargin,
        b("Transfer Learning"), """ is exactly that. By using models that have been already (pre)-trained on
          |1 millions images - for the ImageNet model - we already have a net with a clear, global understanding
          |of images.
        """.stripMargin
      ),
      p(
        """
          |The good part of following this strategy is that it also is comfortable, as some of the most popular
          |models are directly available from the """, p(cls := "icode")("torch"), """ module. To start using those,
          |we just need to keep in mind that we should apply the same PIL transforms on our input data that the
          |ones that were applied when training the knowledge we are about to transfer. More information about
          |this can be found """,
        a(cls := "annotation px-2", href := "\"https://pytorch.org/docs/0.3.0/torchvision/models.html\"")("here.")
      ),
      p(
        """
          |The must do, though, are the transforms regarding size and normalization, size being 224x224 images
          |and normalized with the following mean and std:
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """transforms = transforms.Compose([...,
          |                                 transforms.RandomResizedCrop(224),
          |                                 ...,
          |                                 transforms.ToTensor(),
          |                                 transforms.Normalize([0.485, 0.456, 0.406],
          |                                                      [0.229, 0.224, 0.225])])""".stripMargin)
      ),
      p(
        """
          |PyTorch has followed an interesting approach that eases our way towards applying these models with our own
          |purposes. For that, the model object has been split in two main parts:
        """.stripMargin
      ),
      ol(
        li(b("Feature:"), p(style := "display: inline;")(
          """
            |Usually built up from multiple convolutional layers which are in charge of extracting general feature
            |information about the images. These are, at the end of the day, the layers that can tell us if in the
            |image there are tails, eyes or paws and how they are different between cats and dogs.
          """.stripMargin)
        ),
        li(b("Classifier:"), p(style := "display: inline;")(
          """
            |This is the part of the net that performs the - ehem - classification.
          """.stripMargin)
        )
      ),
      p(
        """
          |The interesting point of this strategy is that it is a completely divisible approach. Indeed, we do not even
          |need to apply a net for the classifier! The feature part of the network has already extracted the necessary
          |information from the images so that they are now numeric values any mathematical algorithm can understand.
          |Thus, we could apply an SVC or powerful models such as XGBoost from that point. However, we will stick
          |with networks now for the sake of completion.
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """import torch
          |from torch import nn
          |from torchvision import datasets, transforms, models
          |
          |model = models.densenet121(pretrained=True)
          |
          |# Freeze parameters to not apply backpropagation on them
          |for param in model.parameters():
          |    param.requires_grad = False
          |
          |from collections import OrderedDict
          |classifier = nn.Sequential(nn.Linear(1024, 256),
          |                           nn.ReLU(),
          |                           nn.Dropout(0.2),
          |                           nn.Linear(256, 2),
          |                           nn.LogSoftmax(dim=1))
          |
          |model.classifier = classifier""".stripMargin)
      ),
      p(
        """
          |Note how we are stopping the model to re-calculate any weight in the feature part of the model.
        """.stripMargin
      ),
      h2("GPU"),
      p(
        """
          |One last thing to address here is that now we are using really huge networks, and pass images through all
          |the layers can take simply too much time. In order to optimize this, NVIDIA has a parallel computing
          |platform developed by them called CUDA. Whenever it is possible, it is advised to use the power of GPUs
          |to speed up all the process. To do so, we just need to specify which device we want to use:
        """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
          |model.to(device)""".stripMargin)
      ),
      p(
        """
          |This is not all, though, as we also need to send the data to the right device, as the underlying objects
          |are different: """.stripMargin, p(cls := "icode")("torch.cuda.FloatTensor"), " vs. ",
        p(cls := "icode")("torch.FloatTensor"), ".",
          """
            |Therefore, the initial train / test snippet we used a few posts ago becomes something like this:
          """.stripMargin
      ),
      pre(cls := "top-padding")(code(cls := "python")(
        """epochs = 1
          |steps = 0
          |running_loss = 0
          |print_every = 5
          |for epoch in range(epochs):
          |    for inputs, labels in trainloader:
          |        steps += 1
          |        # Move input and label tensors to the default device
          |        inputs, labels = inputs.to(device), labels.to(device)
          |
          |        optimizer.zero_grad()
          |
          |        logps = model.forward(inputs)
          |        loss = criterion(logps, labels)
          |        loss.backward()
          |        optimizer.step()
          |
          |        running_loss += loss.item()
          |
          |        if steps % print_every == 0:
          |            test_loss = 0
          |            accuracy = 0
          |            model.eval()
          |            with torch.no_grad():
          |                for inputs, labels in testloader:
          |                    inputs, labels = inputs.to(device), labels.to(device)
          |                    logps = model.forward(inputs)
          |                    batch_loss = criterion(logps, labels)
          |
          |                    test_loss += batch_loss.item()
          |
          |                    # Calculate accuracy
          |                    ps = torch.exp(logps)
          |                    top_p, top_class = ps.topk(1, dim=1)
          |                    equals = top_class == labels.view(*top_class.shape)
          |                    accuracy += torch.mean(equals.type(torch.FloatTensor)).item()
          |
          |            print(f"Epoch {epoch+1}/{epochs}.. "
          |                  f"Train loss: {running_loss/print_every:.3f}.. "
          |                  f"Test loss: {test_loss/len(testloader):.3f}.. "
          |                  f"Test accuracy: {accuracy/len(testloader):.3f}")
          |            running_loss = 0
          |            model.train()""".stripMargin)
      ),




      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
