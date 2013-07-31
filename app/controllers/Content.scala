package controllers

import play.api.mvc._

object Content extends Controller {

  def contact = Action {
    Ok(views.html.Content.contact("Contact"))
  }

	def terms = Action {
		Ok(views.html.Content.terms("Terms"))
	}

	def sourcecode = Action {
		Ok(views.html.Content.sourcecode("Sourcecode"))
	}

}