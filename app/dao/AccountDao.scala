package dao

import com.google.inject.{Inject, Singleton}
import dao.model.views.{AccountView, LiftedAccountView}
import models.{Account, AccountId}
import org.apache.pekko.Done
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

@Singleton
class AccountDao @Inject()(
                            protected val dbConfigProvider: DatabaseConfigProvider
                          )(override implicit val executionContext: ExecutionContext)
  extends BaseDao[AccountId, Account, AccountView] {

  import profile.api._

  override protected val updater
  : (Account, Account) => Account =
    (prev, update) =>
      Account(
        accountId = prev.accountId,
        login = prev.login,
        firstName = update.firstName,
        lastName = update.lastName,
        frequentFlyerId = update.frequentFlyerId.orElse(prev.frequentFlyerId),
        updateTs = update.updateTs.orElse(prev.updateTs)
      )

  override protected def createQuery(
                                      row: Account
                                    ): DBIO[AccountId] = {
    (accounts += row).map(_ => row.accountId)
  }

  override protected def createListQuery(
                                          rows: Seq[Account]
                                        ): DBIO[Done] = {
    (accounts ++= rows).map(_ => Done)
  }

  override def updateQuery(update: Account): DBIO[Account] = {
    for {
      prev <- accounts.filter(_.p.accountId === update.accountId).result.head
      updated = updater(prev, update)
      _ <- accounts.filter(_.p.accountId === update.accountId).update(updated)
    } yield {
      updated
    }
  }

  override protected def deleteQuery(id: AccountId): DBIO[Done] = {
    accounts
      .filter(_.p.accountId === id)
      .delete
      .map(_ => Done)
  }

  override def findByIdQuery(
                                        id: AccountId
                                      ): DBIO[Option[AccountView]] = {
    accounts
      .join(frequentFlyers)
      .on{ case (account, flyer) => account.p.frequentFlyerId === flyer.p.frequentFlyerId }
      .filter(t => t._1.p.accountId === id)
      .map { case (account, frequentFlyer) =>
        LiftedAccountView(
          account.p.frequentFlyerId,
          account.p.firstName,
          account.p.lastName,
          frequentFlyer.p.title,
          frequentFlyer.p.cardNum,
          frequentFlyer.p.level,
          frequentFlyer.p.awardPoints,
          frequentFlyer.p.email,
          frequentFlyer.p.phone,
          account.p.accountId,
          account.p.login
        )
      }
      .result
      .headOption
  }

  /*override protected def findByAccountQuery(
      login: String
  ): DBIO[Seq[Account]] = {
    accounts
      .filter(t => t.p.login.ilike(login))
      .result
  }*/
}
