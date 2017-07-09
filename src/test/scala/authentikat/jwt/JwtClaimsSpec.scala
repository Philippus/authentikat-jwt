package authentikat.jwt

import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

import org.scalatest.{FunSpec, Matchers}

class JwtClaimsSpec extends FunSpec with Matchers {
  import org.json4s.JsonDSL._
  import org.json4s.jackson.JsonMethods._

  describe("JwtClaimsSet") {
    val date = new Date
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
    val dateIso8601 = dateFormat.format(date)

    val claimsSet = JwtClaimsSetMap(Map("privateClaim" -> "foo", "iss" -> "Issuer", "exp" -> date))
    val claimsSetJsonString = claimsSet.asJsonString

    it("should contain private claims") {
      claimsSetJsonString should include("\"privateClaim\":\"foo\"")
    }

    it("should contain iss (Issuer) claim") {
      claimsSetJsonString should include("\"iss\":\"Issuer\"")
    }

    it("should contain exp (Expiration time) claim as ISO8601 date") {
      claimsSetJsonString should include("\"exp\":\"" + dateIso8601 + "\"")
    }
  }

  describe("JwtClaimsSet.asSimpleMap") {
    it("should return a failure on complex hierarchies.") {
      val jvalueTree = render("Hey" -> ("Hey" -> "foo"))
      val tryMap = JwtClaimsSetJValue(jvalueTree).asSimpleMap

      tryMap.isFailure should be(true)
    }

    it("should succeed on flat hierarchies.") {
      val jvalueTree = render("Hey" -> "foo")
      val tryMap = JwtClaimsSetJValue(jvalueTree).asSimpleMap

      tryMap.isFailure should be(false)
      tryMap.get should equal(Map("Hey" -> "foo"))
    }
  }
}
