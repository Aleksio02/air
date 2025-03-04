package dao

import com.google.inject.{Inject, Singleton}
import models.{Account, AccountId, FrequentFlyer, FrequentFlyerId}
import org.apache.pekko.Done
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

@Singleton
class FrequentFlyerDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(override implicit val executionContext: ExecutionContext)
  extends BaseDao[FrequentFlyerId, FrequentFlyer, FrequentFlyer] {

  import profile.api._

  override protected val updater
      : (FrequentFlyer, FrequentFlyer) => FrequentFlyer =
    (prev, update) =>
      FrequentFlyer(
        frequentFlyerId = prev.frequentFlyerId,
          firstName = update.firstName,
          lastName = update.lastName,
          title = update.title,
          cardNum = update.cardNum,
          level = update.level,
          awardPoints = update.awardPoints,
          email = update.email,
          phone = update.phone,
        updateTs = update.updateTs.orElse(prev.updateTs)
      )

  override protected def createQuery(
      row: FrequentFlyer
  ): DBIO[FrequentFlyerId] = {
    (frequentFlyers += row).map(_ => row.frequentFlyerId)
  }

  override protected def createListQuery(
      rows: Seq[FrequentFlyer]
  ): DBIO[Done] = {
    (frequentFlyers ++= rows).map(_ => Done)
  }

  override def updateQuery(update: FrequentFlyer): DBIO[FrequentFlyer] = {
    for {
      prev <- frequentFlyers.filter(_.p.frequentFlyerId === update.frequentFlyerId).result.head
      updated = updater(prev, update)
      _ <- frequentFlyers.filter(_.p.frequentFlyerId === update.frequentFlyerId).update(updated)
    } yield {
      updated
    }
  }

  override protected def deleteQuery(id: FrequentFlyerId): DBIO[Done] = {
    frequentFlyers
      .filter(_.p.frequentFlyerId === id)
      .delete
      .map(_ => Done)
  }

  override protected def findByIdQuery(
      id: FrequentFlyerId
  ): DBIO[Option[FrequentFlyer]] = {
    frequentFlyers
      .filter(t => t.p.frequentFlyerId === id)
      .result
      .headOption
  }

  /*override protected def findByFrequentFlyerQuery(
      login: String
  ): DBIO[Seq[FrequentFlyer]] = {
    frequentFlyers
      .filter(t => t.p.login.ilike(login))
      .result
  }*/
}