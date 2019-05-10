package com.pmbrull.frontend.posts.sparkSeries

import com.pmbrull.frontend.FrontUtils.RecentPosts
import org.scalajs.dom.html.Element
import scalatags.JsDom._
import tags2.section
import scalatags.JsDom.all._

import scala.scalajs.js.Date
import shared.{Post, PostTemplate}

object PredictingRiskOfCancerKNN extends PostTemplate {

  val title = "Predicting Risk of Cancer using KNN"
  val date = new Date(2019, 10, 5)
  val category = "Spark Series"
  val description: String =
    """
      In this post we are going to use Pyspark to implement the classification
      of breast tumors as malign or bening, performing predictions with the
      simplest version of the K Nearest Neighbour: 1NN.
    """.stripMargin


  def buildBody(title: String): Element = {
    section(id := "PredictingRiskOfCancerKNN")(
      h1(title),
      p(cls := "top-padding")(description),
      p("We will follow these guidelines:"),
      ol(
        li("Split the train and test sets."),
        li("Generate all possible combinations: cartesian product."),
        li(
          """For each combination $\{train_i, test_j\} \forall i, j$
            |calculate Euclidian distance of the predictor variables.""".stripMargin
        ),
        li("For each test instance, predict with the observation of the training set with minimum distance."),

        pre(cls := "top-padding")(code(cls := "python")(
          """sc = SparkContext.getOrCreate()
             |
             |cancerRDD = sc.textFile("../input/Tumor/breast.csv")
             |print('The file has {} lines.'.format(cancerRDD.count()))""".stripMargin)
        ),
        pre("The file has 570 lines."),

        pre(cls := "top-padding")(code(cls := "python")(
          """header = cancerRDD.first()
            |predictors = [x for x in header.split(',')
            |              if x not in ['id','diagnosis','train']]
            |
            |print(predictors)""".stripMargin)
        ),
        pre("['mradius', 'mtexture', 'mperimeter', 'marea', 'msmooth', 'mcompact', 'mconcavity', 'mconc_p', 'msymmetry', 'mfractal', 'sradius', 'stexture', 'sperimeter', 'sarea', 'ssmooth', 'scompact', 'sconcavity', 'sconc_p', 'ssymmetry', 'sfractal', 'lradius', 'ltexture', 'lperimeter', 'larea', 'lsmooth', 'lcompact', 'lconcavity', 'lconc_p', 'lsymmetry', 'lfractal']"),

        p("We are going to create two RDD's from this one, so we will cache the result of splitting the lines in " +
          "order to not reading from disk twice. Train/test distinction is in the last position:"),

        pre(cls := "top-padding")(code(cls := "python")(
          """splitRDD = cancerRDD.filter(lambda line: line != header)\
            |            .map(lambda line: line.split(','))\
            |            .cache()
            |
            |trainRDD = splitRDD.filter(lambda f: f[32] == '1')
            |testRDD = splitRDD.filter(lambda f: f[32] == '0')
            |
            |fullRDD = testRDD.cartesian(trainRDD)
            |print('The cartesian product has {} lines.'
            |      .format(fullRDD.count()))""".stripMargin)
        ),
        pre("The cartesian product has 65440 lines."),

        p(
          """Now we have tuples {key,value} where keys are the test instances and values each train observation.
            |In order to get the minimum distance we need to group all values
            |together for each key, calculate the distance.""".stripMargin
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """num_pred = len(predictors)
            |
            |def calc_dist(Tuple):
            |    train = Tuple[1]
            |    test = Tuple[0]
            |    dist = 0.0
            |    for i in range(2,32):
            |        dist = dist + (float(train[i]) - float(test[i]))**2
            |    return (test[0],((dist) ** 0.5, train[1] + '_' + test[1]))""".stripMargin)
        ),

        p(
          """The function calc_dist returns, given an input tuple {key=test, value=train},
            |another tuple with key the test *id* and value the distance calculated using the predictors.""".stripMargin
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """distRDD = fullRDD.map(calc_dist)
            |distRDD.take(5)""".stripMargin)
        ),
        pre("""[('842302', (341.7302620944424, 'M_M')),
              |('842302', (376.45576487702664, 'M_M')),
              |('842302', (538.0234879671779, 'M_M')),
              |('842302', (1389.4612365464616, 'M_M')),
              |('842302', (1206.344557277833, 'M_M'))]""".stripMargin
        ),

        h2(cls := "top-padding")("1NN"),
        p(
          """Let's operate over every value of each key and keep the tuple {distance,prediction}
            |that has the minimum value. Then, readjust the key, making it the X_Y value
            |and compute the count of each distinct one:""".stripMargin
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """pred = distRDD.reduceByKey(lambda val1, val2:
            |                           val1 if val1[0]<val2[0] else val2)\
            |            .mapValues(lambda value: value[1])\
            |            .values()\
            |            .map(lambda x: (x,1))\
            |            .reduceByKey(lambda val1, val2: val1 + val2)\
            |            .sortByKey()\
            |            .collect()
            |
            |print('The final prediction set consists of:')
            |for elem in pred:
            |    print(elem)""".stripMargin)
        ),
        pre("""The final prediction set consists of:
              |('B_B', 94)
              |('B_M', 5)
              |('M_B', 4)
              |('M_M', 57)""".stripMargin
        ),

        h2(cls := "top-padding")("KNN"),
        ol(
          li("Obtain a list of tuples {distance, prediction} for each id: groupByKey."),
          li("Sort the list by the first value and keep the first K tuples: distance: mapValues + sorted + [:K]"),
          li("Convert list of tuples {dist,pred} to list of {pred}. "),
          li("Get most common element in list."),
          li("Again, readjust new key to the prediction and compute the counting.")
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """from collections import Counter
            |
            |pred = distRDD.groupByKey()\
            |            .mapValues(lambda vec:
            |                       sorted(vec, key=lambda x: x[0])[:11])\
            |            .mapValues(lambda vec: [x[1] for x in vec])\
            |            .mapValues(lambda vec:
            |                       Counter(vec).most_common(1)[0][0])\
            |            .values()\
            |            .map(lambda x: (x,1))\
            |            .reduceByKey(lambda val1, val2: val1 + val2)\
            |            .sortByKey()\
            |            .collect()
            |
            |print('The final prediction set consists of:')
            |for elem in pred:
            |    print(elem)
          """.stripMargin)
        ),
        pre("""The final prediction set consists of:
              |('B_B', 95)
              |('B_M', 4)
              |('M_B', 3)
              |('M_M', 58)""".stripMargin
        ),

        p(
          """With K=11 we get a small improvement. However, when testing with different numbers,
            |K=5 resulted in worse predictions than just using 1NN. Let's plot the errors with
            |different values of K:""".stripMargin
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """BM = []
            |MB = []
            |T = []
            |
            |def generate_errors(K):
            |    pred = distRDD.groupByKey()\
            |            .mapValues(lambda vec:
            |                       sorted(vec, key=lambda x: x[0])[:K])\
            |            .mapValues(lambda vec: [x[1] for x in vec])\
            |            .mapValues(lambda vec:
            |                       Counter(vec).most_common(1)[0][0])\
            |            .values()\
            |            .map(lambda x: (x,1))\
            |            .reduceByKey(lambda val1, val2: val1 + val2)\
            |            .sortByKey()\
            |            .values()\
            |            .collect()
            |
            |    BM.append(pred[1])
            |    MB.append(pred[2])
            |    T.append(pred[1]+pred[2])
            |
            |vecK = range(1,61)
            |for i in vecK:
            |    generate_errors(i)
            |""".stripMargin)
        ),

        pre(cls := "top-padding")(code(cls := "python")(
          """from plotly.offline import init_notebook_mode, iplot
            |import plotly.graph_objs as go
            |
            |init_notebook_mode()
            |
            |_PLOTLY_CONFIG = {"displaylogo": False,
            |                "modeBarButtonsToRemove":
            |                ["sendDataToCloud", "select2d", "lasso2d",
            |                 "resetScale2d"]}
            |
            |# Create traces
            |trace0 = go.Scatter(
            |    x = list(vecK),
            |    y = BM,
            |    mode = 'lines+markers',
            |    name = 'False Positives'
            |)
            |trace1 = go.Scatter(
            |    x = list(vecK),
            |    y = MB,
            |    mode = 'lines+markers',
            |    name = 'False Negatives'
            |)
            |trace2 = go.Scatter(
            |    x = list(vecK),
            |    y = T,
            |    mode = 'lines+markers',
            |    name = 'Total Errors'
            |)
            |
            |data = [trace0, trace1, trace2]
            |
            |layout = go.Layout(
            |    title='KNN Prediction Errors',
            |    xaxis=dict(
            |        title='Values of K'
            |    ),
            |    yaxis=dict(
            |        title='Number of Errors'
            |    )
            |)
            |
            |fig = go.Figure(data=data, layout=layout)
            |iplot(fig, filename='KNN Errors', config=_PLOTLY_CONFIG,
            |      show_link=False)
            |
            |print('Minimum number of False Positives: {}'.format(min(BM)))
            |print('Minimum number of False Negatives: {}'.format(min(MB)))
            |print('Minimum number of Errors: {}'.format(min(T)))
            |""".stripMargin)
        ),
        div(id := "dbea66bf-f1bf-4cb5-b6ad-bc6a45648053", style := "height: 100%; width: 800px;", cls := "plotly-graph-div"),
        script(`type` := "text/javascript")(
          """window.PLOTLYENV=window.PLOTLYENV || {};window.PLOTLYENV.BASE_URL="https://plot.ly";Plotly.newPlot("dbea66bf-f1bf-4cb5-b6ad-bc6a45648053", [{"type": "scatter", "x": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60], "y": [5, 5, 5, 5, 7, 6, 6, 4, 5, 5, 4, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 8, 9, 8, 10, 10, 10, 10, 11, 11, 12, 11, 12, 9, 12, 12, 12, 9, 10, 9, 10, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 10], "mode": "lines+markers", "name": "False Positives"}, {"type": "scatter", "x": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60], "y": [4, 4, 4, 3, 3, 3, 3, 3, 2, 4, 3, 2, 3, 2, 3, 2, 3, 3, 3, 2, 2, 2, 3, 2, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2], "mode": "lines+markers", "name": "False Negatives"}, {"type": "scatter", "x": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60], "y": [9, 9, 9, 8, 10, 9, 9, 7, 7, 9, 7, 7, 8, 8, 9, 8, 9, 9, 9, 8, 8, 9, 10, 10, 12, 11, 13, 12, 12, 12, 13, 13, 14, 13, 14, 11, 14, 14, 14, 11, 11, 11, 11, 10, 10, 10, 11, 11, 11, 12, 11, 12, 11, 11, 12, 12, 13, 12, 12, 12], "mode": "lines+markers", "name": "Total Errors"}],{"title": "KNN Prediction Errors", "xaxis": {"title": "Values of K"}, "yaxis": {"title": "Number of Errors"}}, {"legend": {"x": -0.1, "y": 1.2}}, {"showLink": false, "linkText": "Export to plot.ly", "modeBarButtonsToRemove": ["sendDataToCloud", "select2d", "lasso2d", "resetScale2d"], "displaylogo": false})"""
        ),
        pre("""Minimum number of False Positives: 4
              |Minimum number of False Negatives: 1
              |Minimum number of Errors: 7""".stripMargin
        ),

        p(
          """We can observe how there is a lower bound of error rate we cannot improve, with a minimum of 7 errors,
            |as the total errors curve shows an increasing tendency. The only type of errors that gets to
            |decrease are the false negatives. However this effect gets overwhelmed by the increasing
            |rate of false positives.""".stripMargin)

      ),
      div(cls := "top-padding "),
      RecentPosts.postCategoryAnnotation(category),
      RecentPosts.postUpdateAnnotation(date)
    ).render
  }

  def getPost: Post = buildPost(title, date, category, description)

}
