package controllers

import controllers.Assets.Asset
import javax.inject._
import play.api.mvc._

class Application @Inject() (components: ControllerComponents, assets: Assets)
    extends AbstractController(components) {

  def index: Action[AnyContent] = Action {
    Ok(views.html.index())
  }

  def post(name: String): Action[AnyContent] = Action {
    Ok(views.html.post(name))
  }

  def category(category: String): Action[AnyContent] = Action {
    Ok(views.html.category(category))
  }

  def about: Action[AnyContent] = Action {
    Ok(views.html.about())
  }

  def categories: Action[AnyContent] = Action {
    Ok(views.html.categories())
  }

  def resume: Action[AnyContent] = Action {
    Ok(views.html.resume())
  }

  def versioned(path: String, file: Asset): Action[AnyContent] = assets.versioned(path, file)
}
