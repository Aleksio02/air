package dao.model.lifted

import models.{AccountId, FrequentFlyerId, PhoneId}
import slick.lifted.Rep

import java.time.{LocalDateTime, OffsetDateTime}


case class LiftedDbFrequentFlyerRow(frequentFlyerId: Rep[FrequentFlyerId],
                         firstName: Rep[String],
                         lastName: Rep[String],
                         title: Rep[String],
                         cardNum: Rep[String],
                         level: Rep[Int],
                         awardPoints: Rep[Int],
                         email: Rep[String],
                         phone: Rep[String],
                         updateTs: Rep[Option[OffsetDateTime]])

case class LiftedDbAccountRow(
                               accountId: Rep[AccountId],
                               login: Rep[String],
                               firstName: Rep[String],
                               lastName: Rep[String],
                               frequentFlyerId: Rep[Option[FrequentFlyerId]],
                               updateTs: Rep[Option[OffsetDateTime]])


case class LiftedDbPhoneRow(
                             phoneId: Rep[PhoneId],
                             accountId: Rep[Option[AccountId]],
                             phone: Rep[Option[String]],
                             phoneType: Rep[Option[String]],
                             primaryPhone: Rep[Option[Boolean]],
                             updateTs: Rep[Option[LocalDateTime]])
