package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Mopeds."))
  }

	def moped = Action {
		Ok(views.html.moped("Mopeds"))
	}

}