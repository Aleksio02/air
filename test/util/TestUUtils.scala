package util

import org.scalacheck.Test.Parameters
import org.scalacheck.{Gen, Prop, Test}
import play.api.Logger
import play.api.libs.json.{Format, Json}

import scala.util.Try

object TestUUtils {
  def doCheck(p: Prop): Boolean = {
    Test.check(Parameters.default.withMinSuccessfulTests(10), p).passed
  }

  def createProp[A](
                     generator: Gen[A]
                   )(implicit
                     format: Format[A],
                     logger: Logger
                   ): Prop = {
    Prop.forAll(generator)(
      obj =>
        Try {
          logger.info(s"Source: $obj")
          val serObj = Json.stringify(format.writes(obj))
          logger.info(s"Json:\n$serObj")
          val restoredObj = format.reads(Json.parse(serObj)).get
          logger.info(s"Restored obj: $restoredObj")
          obj == restoredObj
        }.fold(t => {
          logger.error("Error while serializing", t)
          false
        },
          identity
        )
    )
  }
}
