package models

import play.api.Play.current
import java.util.Date
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import mongoContext._

/**
 *
 * @author Ingo Pfennigstorf <i.pfennigstorf@gmail.com>
 */
case class Moped(
	id: ObjectId = new ObjectId,
	title: String,
	manufactured: String,
	manufacturer: String,
	description: String
)


/**
 * Further methods for Mopeds
 */
object Moped extends MopedDAO with MopedJson {
	def all(): List[Moped] = Nil
	def create(title: String) {}
	def delete(id: ObjectId) {}
}

/**
 * Trait with specific methods and queries
 */
trait MopedDAO extends ModelCompanion[Moped, ObjectId] {
	def collection = mongoCollection("mopeds")

	val dao = new SalatDAO[Moped, ObjectId](collection) {}
	val columns = List("_id", "title")

	// Indexes
	collection.ensureIndex(DBObject("title" -> 1), "title", unique = true)

	// Queries
	def findOneByMopedTitle(title: String): Option[Moped] = dao.findOne(MongoDBObject("title" -> title))
}

/**
 * Trait used to convert to and from json
 */
trait MopedJson {

	implicit val tagJsonWrite = new Writes[Moped] {
		def writes(m: Moped): JsValue = {
			Json.obj(
				"id" -> m.id,
				"manufactured" -> m.manufactured,
				"manufacturer" -> m.manufacturer,
				"description" -> m.manufacturer
			)
		}
	}
}