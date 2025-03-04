package services

import cats.data.EitherT
import com.google.inject.{Inject, Singleton}
import dao.AccountDao
import dao.model.views.AccountView
import models.{Account, AccountId}
import org.apache.pekko.Done

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountService @Inject()(dao: AccountDao)(implicit
                                              ec: ExecutionContext
) {
  def create(
              row: Account
            ): Future[Either[Throwable, AccountId]] = {
    dao.create(row)
  }

  def createList(
                  rows: Seq[Account]
                ): Future[Either[Throwable, Done]] = {
    dao.createList(rows)
  }

  def update(
              update: Account
            ): Future[Either[Throwable, Account]] = {
    EitherT(dao.update(update)).value
  }

  def delete(id: AccountId): Future[Either[Throwable, Done]] = {
    dao.delete(id)
  }

  def findById(
                id: AccountId
              ): Future[Either[Throwable, Option[AccountView]]] = {
    println(id)
    EitherT(dao.findById(id)).value.map(x => {
      println(x)
      x
    })
  }

  /*def findByName(
                  name: String
                ): Future[Either[Throwable, Seq[WebPasswordRow]]] = {
    EitherT(dao.findByName(name)).map(_.map(WebPasswordRow(_))).value
  }*/
}
