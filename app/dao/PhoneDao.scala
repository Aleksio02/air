package dao

import com.google.inject.{Inject, Singleton}
import models.{Phone, PhoneId}
import org.apache.pekko.Done
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

@Singleton
class PhoneDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(override implicit val executionContext: ExecutionContext)
    extends BaseDao[PhoneId, Phone, Phone] {

  import profile.api._

  override protected val updater
      : (Phone, Phone) => Phone =
    (prev, update) =>
      Phone(
        phoneId = prev.phoneId,
        accountId = prev.accountId,
        phone = update.phone.orElse(prev.phone),
        phoneType = update.phoneType.orElse(prev.phoneType),
        primaryPhone = update.primaryPhone.orElse(prev.primaryPhone),
        updateTs = update.updateTs.orElse(prev.updateTs)
      )

  override protected def createQuery(
      row: Phone
  ): DBIO[PhoneId] = {
    (phones += row).map(_ => row.phoneId)
  }

  override protected def createListQuery(
      rows: Seq[Phone]
  ): DBIO[Done] = {
    (phones ++= rows).map(_ => Done)
  }

  override def updateQuery(update: Phone): DBIO[Phone] = {
    for {
      prev <- phones.filter(_.p.phoneId === update.phoneId).result.head
      updated = updater(prev, update)
      _ <- phones.filter(_.p.phoneId === update.phoneId).update(updated)
    } yield {
      updated
    }
  }

  override protected def deleteQuery(id: PhoneId): DBIO[Done] = {
    phones
      .filter(_.p.phoneId === id)
      .delete
      .map(_ => Done)
  }

  override protected def findByIdQuery(
      id: PhoneId
  ): DBIO[Option[Phone]] = {
    phones
      .filter(t => t.p.phoneId === id)
      .result
      .headOption
  }

  /*override protected def findByPhoneQuery(
      phone: String
  ): DBIO[Seq[Phone]] = {
    phones
      .filter(t => t.p.phone.ilike(phone))
      .result
  }*/
}
