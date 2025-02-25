package services

import cats.data.EitherT
import com.google.inject.{Inject, Singleton}
import dao.PhoneDao
import models.{Phone, PhoneId}
import org.apache.pekko.Done

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhoneService @Inject()(dao: PhoneDao)(implicit
                                            ec: ExecutionContext
) {
  def create(
              row: Phone
            ): Future[Either[Throwable, PhoneId]] = {
    dao.create(row)
  }

  def createList(
                  rows: Seq[Phone]
                ): Future[Either[Throwable, Done]] = {
    dao.createList(rows)
  }

  def update(
              update: Phone
            ): Future[Either[Throwable, Phone]] = {
    EitherT(dao.update(update)).value
  }

  def delete(id: PhoneId): Future[Either[Throwable, Done]] = {
    dao.delete(id)
  }

  def findById(
                id: PhoneId
              ): Future[Either[Throwable, Option[Phone]]] = {
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
