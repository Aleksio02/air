package dao.model.lifted

import models.{AccountId, PhoneId}
import slick.lifted.Rep

import java.time.OffsetDateTime

class LiftedDbAccountRow {

}


case class LiftedDbPhoneRow(
                             phoneId: Rep[PhoneId],
                             accountId: Rep[Option[AccountId]],
                             phone: Rep[Option[String]],
                             phoneType: Rep[Option[String]],
                             primaryPhone: Rep[Option[Boolean]],
                             updateTs: Rep[Option[OffsetDateTime]])
