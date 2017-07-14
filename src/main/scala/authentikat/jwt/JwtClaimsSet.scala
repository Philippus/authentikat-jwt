package authentikat.jwt

import org.json4s._
import org.json4s.jackson.JsonMethods.compact
import scala.util.Try

sealed trait JwtClaimsSet {
  implicit val formats = DefaultFormats
  def asJsonString: String
}

case class JwtClaimsSetMap(claims: Map[String, Any]) extends JwtClaimsSet {
  def asJsonString: String = jackson.Serialization.write(claims)
}

case class JwtClaimsSetJValue(jvalue: JValue) extends JwtClaimsSet {
  def asJsonString: String = compact(jvalue)

  def asSimpleMap: Try[Map[String, String]] = Try(jvalue.extract[Map[String, String]])
}

case class JwtClaimsSetJsonString(json: String) extends JwtClaimsSet {
  def asJsonString: String = json
}

object JwtClaimsSet {
  def apply(claims: Map[String, Any]) = JwtClaimsSetMap(claims)
  def apply(jvalue: JValue) = JwtClaimsSetJValue(jvalue)
  def apply(json: String) = JwtClaimsSetJsonString(json)
}
