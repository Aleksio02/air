package dao.model.views

import models.{AccountId, FrequentFlyerId}
import play.api.libs.json.{Format, Json}
import slick.lifted.Rep

case class AccountView(frequentFlyerId: Option[FrequentFlyerId],
                       firstName: String,
                       lastName: String,
                       title: String,
                       cardNum: String,
                       level: Int,
                       awardPoints: Int,
                       email: String,
                       phone: String,
                       accountId: AccountId,
                       login: String)

case class LiftedAccountView(frequentFlyerId: Rep[Option[FrequentFlyerId]],
                             firstName: Rep[String],
                             lastName: Rep[String],
                             title: Rep[String],
                             cardNum: Rep[String],
                             level: Rep[Int],
                             awardPoints: Rep[Int],
                             email: Rep[String],
                             phone: Rep[String],
                             accountId: Rep[AccountId],
                             login: Rep[String])


object AccountView {
  implicit val format: Format[AccountView] = Json.format[AccountView]
}