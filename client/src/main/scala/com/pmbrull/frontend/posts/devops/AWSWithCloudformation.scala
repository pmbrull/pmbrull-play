package com.pmbrull.frontend.posts.devops

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object AWSWithCloudformation extends PostTemplate {

  val title = "AWS with Cloudformation"
  val date = new Date("2019-07-21") // any-mes-dia
  val category = "Devops"
  val description: String =
    """
      It's been a month since I enrolled into Udacity's Cloud DevOps Nanodegree program. After getting some
      cloud fundamentals knowledge regarding both usage and services, we were presented an awesome tool: Cloudformation.
      Cloudformation introduced the concept of Infrastructure as Code (IAC), where we treat services as we do with usual software in
      the sense of developing, testing and promoting environments.
    """.stripMargin

  def buildBody(title: String): Element = {
    section(id := "AWSWithCloudformation")(
      h1(title),
      p(cls := "top-padding")(description),
      h2("Infrastructure as Code"),
      p(
        """
          |IaC gets extremely powerful when working with cloud providers, as each project has its own
          |specific needs in terms of what services to use and even with security and networking.
          |Usually this means spinning up a really complex architecture even when we are just messing around with
          |a web server that needs some external storage.
        """.stripMargin
      ),
      p(
        """
          |Thus, what we aim to do is iteratively describing this architecture, testing it together with the software
          |it supports and scaling environments as we get the tests to work out. This means that we are not just applying
          |CI/CD pipelines for code, but also for the platform, thus avoiding any """.stripMargin,
        b("configuration drift"), """ that could occur when working with different environment descriptions. This does not
           mean, however, that then platform services become developer's responsibility, but rather getting the whole
           process to work as a single unit.
        """.stripMargin
      ),
      p(
        """
          |To illustrate this, the second nanodegree project consisted in preparing a web application obtaining
          |some code from an S3 bucket and using AWS services such as a Load Balancer and Autoscaling group to ensure
          |that we provide a service with high-availability and fault tolerance, as well as being flexible enough
          |to serve all the incoming users even when there are peaks with the requests. Not only that, but describing
          |all the security and networking rules has also been as hard as it was interesting. For the code of the project
          |you can refer to this github""".stripMargin,
           a(cls := "annotation px-2", href := "https://github.com/pmbrull/udacity-iac-project")("page.")
      ),
      img(src := "/assets/images/devops/IaC-architecture.png", alt := "architecture", style := "width: 100%;"),



      p("You can find all the information about the Nanodegree",
        a(cls := "annotation px-2", href := "https://eu.udacity.com/course/cloud-dev-ops-nanodegree--nd9991")("here.")
      ),
      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
