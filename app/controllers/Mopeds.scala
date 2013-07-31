package controllers

import play.api._
import libs.iteratee.Enumerator
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play
import play.api.Play.current
import play.api.data.format.Formats._
import com.novus.salat._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.Implicits._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Formats._
import com.mongodb.casbah.gridfs.Imports._
import views._
import models._
import play.api.libs.json.Json
import play.api.libs.json
import java.security.MessageDigest
import com.google.common.hash

import java.util
import java.text.SimpleDateFormat
import com.mongodb.casbah.MongoCollection

object Mopeds extends Controller {

	def all = Action {
		implicit request =>
			Ok(views.html.Moped.all("List", mopeds))
	}

	def create = Action {
		implicit request =>

			mopedForm().bindFromRequest.fold(
				formWithErrors => BadRequest("Bad request"),
				moped => {
					Moped.insert(moped)
					Redirect(routes.Mopeds.all()).flashing("success" -> "Moped %s has been created".format(moped.title))
				}
			)
	}


	def sell = Action {
		Ok(views.html.Moped.sell("Mopeds", mopedForm()))
	}

	def buy(id: ObjectId) = Action {
		Ok(views.html.Moped.buy("Mopeds"))
	}

	def delete(id: ObjectId) = Action {
		Ok(views.html.Moped.delete("Delete"))
	}

	def show(id: ObjectId) = Action { implicit request =>
		val moped = Moped.findOneById(id)
		Ok(views.html.Moped.show("Moped", moped))
	}

	def showManufacturer(manufacturer: String) = Action {
		Ok(views.html.Moped.showManufacturer("Moped", manufacturer))
	}

	/**
	 * Form for adding tags
	 * @return
	 */
	def mopedForm(id: ObjectId = new ObjectId) = Form(
		mapping(
			"id" -> ignored(id),
			"title" -> text,
			"manufactured" -> text,
			"manufacturer" -> text,
			"description" -> text
		)(Moped.apply)(Moped.unapply)

	)

	def mopeds = Moped.findAll.toList

}