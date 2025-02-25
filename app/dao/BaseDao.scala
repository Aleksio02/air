package dao

import cats.implicits._
import org.apache.pekko.Done

import scala.concurrent.Future

trait BaseDao[DbEntityId, DbEntity, DbEntityUpdate] extends Tables {

  import profile.api._

  protected val updater: (DbEntity, DbEntityUpdate) => DbEntity

  protected def createQuery(row: DbEntity): DBIO[DbEntityId]

  def create(row: DbEntity): Future[Either[Throwable, DbEntityId]] = db
    .run(createQuery(row))
    .map(_.asRight)
    .recover(_.asLeft)

  protected def createListQuery(row: Seq[DbEntity]): DBIO[Done]

  def createList(rows: Seq[DbEntity]): Future[Either[Throwable, Done]] = db
    .run(createListQuery(rows))
    .map(_.asRight)
    .recover(_.asLeft)

  def updateQuery(update: DbEntityUpdate): DBIO[DbEntity]

  def update(update: DbEntityUpdate): Future[Either[Throwable, DbEntity]] = db
    .run(updateQuery(update))
    .map(_.asRight)
    .recover(_.asLeft)

  protected def deleteQuery(id: DbEntityId): DBIO[Done]

  def delete(id: DbEntityId): Future[Either[Throwable, Done]] = db
    .run(deleteQuery(id))
    .map(_.asRight)
    .recover(_.asLeft)

  protected def findByIdQuery(id: DbEntityId): DBIO[Option[DbEntity]]

  def findById(id: DbEntityId): Future[Either[Throwable, Option[DbEntity]]] = db
    .run(findByIdQuery(id))
    .map(_.asRight)
    .recover(_.asLeft)

  /*protected def findByPhoneQuery(phone: String): DBIO[Seq[DbEntity]]

  def findByPhone(phone: String): Future[Either[Throwable, Seq[DbEntity]]] = db
    .run(findByPhoneQuery(phone))
    .map(_.asRight)
    .recover(_.asLeft)*/

  def getById(id: DbEntityId): Future[Either[Throwable, DbEntity]] = db
    .run(findByIdQuery(id))
    .map(_.toRight(new NoSuchElementException(id.toString)))
    .recover(_.asLeft)

}

