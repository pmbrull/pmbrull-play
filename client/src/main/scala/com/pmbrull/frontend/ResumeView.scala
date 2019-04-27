package com.pmbrull.frontend

import org.scalajs.dom
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("ResumeView")
object ResumeView {

  @JSExport
  def view(): Unit = {

    dom.document.getElementById("content").appendChild(
      section(id := "Resume")(
        h1("Resume"),
        div(
          h2(cls := "top-padding")("Work Experience"),
          h3("Current - Big Data Developer at ServiZurich, Technology Delivery Center"),
          li(
            """ETL development on an on-premise cluster focusing on historical data archival
              |and schema evolution using Spark, Scala, Hive and Avro.""".stripMargin
          ),
          h3(cls := "top-padding")("Feb 2017 to Oct 2018 - Data Analyst and Developer at BaseTIS"),
          li(
            """
              |Alvian Comunicaciones: Designed a solution from scratch for a daily ETL process
              |applying complex rules with AWS EC2, S3 and Redshif, MongoDB, Python and
              |Apache-Airflow. Managed a 3 people team to develop the solution that better fit
              |the client
            """.stripMargin
          ),
          li(
            """Zeus (Gas Natural): Analyst at an ETL project with large data volumes. Validate
              |the transformation and set the algorithms with SQL and Oracle’s PL/SQL""".stripMargin
          ),
          li("""Prepare dashboards and scripts to have real-time control on the data transformation
            |process and allow database monitoring through a web app with Python""".stripMargin
          ),
          li(
            """Fira Barcelona: Automatic data extraction and format transformation for their
              |web and mobile application using Python. Automatization of technical specifica-
              |tion sheets with JS and QCAD API.""".stripMargin
          ),
          li("GenCat, TMB: Preparation of dashboards with PowerBI"),
          li("Machine Learning and Deep Learning workshop organization"),
          hr()
        ),
        div(cls := "top-padding")(
          h2("Education"),
          h3("Master in Big Data Management, Technologies and Analytics, 9.1/10. UPC School."),
          h3(cls := "top-padding")("Degree in Mathematics, Universitat Politècnica de Catalunya"),
          li("Erasmus at the École polytechnique Fédérale de Lausanne"),
          li("Thesis: “Simulation and Estimation of Lévy driven stochastic processes.”"),
          h3(cls := "top-padding")("Certifications"),
          li("Functional Programming Principles in Scala, Coursera"),
          li("Functional Programming Design, Coursera"),
          hr()
        )

      ).render
    )
  }

}
