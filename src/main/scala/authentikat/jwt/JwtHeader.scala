package authentikat.jwt

import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.util.Try

case class JwtHeader(algorithm: Option[String], contentType: Option[String], typ: Option[String]) {
  def asJsonString: String = {
    val toSerialize = algorithm.map(("alg", _)) ++ contentType.map(("cty", _)) ++ typ.map(("typ", _))

    import org.json4s.native.Serialization.write
    implicit val formats = org.json4s.DefaultFormats

    write(toSerialize.toMap)
  }
}

object JwtHeader {
  import org.json4s.DefaultFormats

  implicit val formats = DefaultFormats

  def apply(algorithm: String, contentType: String = null, typ: String = "JWT"): JwtHeader = {
    JwtHeader(Option(algorithm), Option(contentType), Option(typ))
  }

  def fromJsonString(jsonString: String): JwtHeader = {
    val ast = parse(jsonString)

    val alg = (ast \ "alg").extract[Option[String]]
    val cty = (ast \ "cty").extract[Option[String]]
    val typ = (ast \ "typ").extract[Option[String]]

    JwtHeader(alg, cty, typ)
  }

  def fromJsonStringOpt(jsonString: String): Option[JwtHeader] =
    Try(fromJsonString(jsonString)).toOption
}
