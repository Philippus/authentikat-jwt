package authentikat.jwt

import org.scalatest.{ FunSpec, Matchers }

class JsonWebTokenSpec extends FunSpec with Matchers {
  import org.json4s.JsonDSL._
  import org.json4s.jackson.JsonMethods._

  describe("JsonWebToken") {
    val header = JwtHeader("HS256")
    val claims = JwtClaimsSetMap(Map("Hey" -> "foo"))
    val jValueClaims = render("Hey" -> ("Hey" -> "foo"))

    it("should work for NONE") {
      val result = JsonWebToken.apply(JwtHeader("none"), claims, "secretkey")
      JsonWebToken.unapply(result).get._1 should equal(JwtHeader("none"))
      JsonWebToken.unapply(result).get._2.asJsonString should equal(claims.asJsonString)
      JsonWebToken.validate(result, "secretkey")
    }
  }
//    it("should have three parts for a token created with claims map claims") {
//      val result = JsonWebToken.apply(header, claims, "secretkey")
//      result.split("\\.").length should equal(3)
//      JsonWebToken.unapply(result).get._1 should equal(header)
//      JsonWebToken.unapply(result).get._2.asJsonString should equal(claims.asJsonString)
//    }
//
//    it("should have three parts for a token created with a jvalue claims") {
//      val result = JsonWebToken.apply(header, JwtClaimsSetJValue(jValueClaims), "secretkey")
//      result.split("\\.").length should equal(3)
//    }
//
//    it("should have three parts for a token created with a string claims") {
//      val result = JsonWebToken.apply(header, JwtClaimsSetJValue("{\"json\":42}"), "secretkey")
//      result.split("\\.").length should equal(3)
//    }
//
//    it("should produce the same results for all three claims types") {
//      val expectedResult = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJIZXkiOiJmb28ifQ.fTW9f2w5okSpa7u64d6laQQbpBdgoTFvIPcx5gi70R8"
//
//      val res1 = JsonWebToken.apply(header, JwtClaimsSetMap(Map("Hey" -> "foo")), "secretkey")
//      val res2 = JsonWebToken.apply(header, JwtClaimsSetJValue("Hey" -> "foo"), "secretkey")
//      val res3 = JsonWebToken.apply(header, JwtClaimsSetJsonString("{\"Hey\":\"foo\"}"), "secretkey")
//
//      res1 should equal(expectedResult)
//      res2 should equal(expectedResult)
//      res3 should equal(expectedResult)
//    }
//
//    it("should be extracted by extractor") {
//      val jwt = JsonWebToken.apply(header, claims, "secretkey")
//      val result = jwt match {
//        case JsonWebToken(_, _, _) ⇒ true
//        case _ ⇒ false
//      }
//      result should equal(true)
//    }
//
//    it("extracted claims set should be jvalue") {
//      val jwt = JsonWebToken.apply(header, claims, "secretkey")
//      val result = jwt match {
//        case JsonWebToken(_, x, _) ⇒ Some(x)
//        case _ ⇒ None
//      }
//      result.get should equal(JwtClaimsSetJValue("Hey" -> "foo"))
//    }
//
//    it("should validate a token successfully if same key is used") {
//      val jwt = JsonWebToken.apply(header, claims, "secretkey")
//      JsonWebToken.validate(jwt, "secretkey") should equal(true)
//    }
//
//    it("should fail to validate a token if different key is used") {
//      val jwt = JsonWebToken.apply(header, claims, "secretkey")
//      JsonWebToken.validate(jwt, "here be dragons") should equal(false)
//    }
//
//    it("should report validation failure and not crash if the token is incorrectly formatted") {
//      JsonWebToken.validate("", "secretkey") should equal(false)
//    }
//
//    it("should report a validation failure and not crash if the token components are incorrectly formatted") {
//      JsonWebToken.validate("..", "secretkey") should equal(false)
//    }
//  }
//
//  describe("JwtHeader") {
//    it("should render to json as per spec") {
//      val header = JwtHeader("algorithm", "contentType")
//      val expectedJson = """{"alg":"algorithm","cty":"contentType","typ":"JWT"}"""
//
//      header.asJsonString should equal(expectedJson)
//    }
//  }
//
//  describe("comments should be disabled") {
//    val header = JwtHeader("HS256")
//    val claims = "x\",\"username\":\"kevinmitnick\",\"role\":\"admin\"}\\/\\/"
//    val claimsSet = JwtClaimsSet(Map(
//      "username" -> claims,
//      "role" -> "user"))
//
//    val jwt: String = JsonWebToken(header, claimsSet, "secretkey")
//
//    val parsedClaims: Option[Map[String, String]] = jwt match {
//      case JsonWebToken(_, claimsSet, _) ⇒
//        claimsSet.asSimpleMap.toOption
//      case _ ⇒
//        None
//    }
//
//    parsedClaims.get("role") should be("user")
//  }
}
