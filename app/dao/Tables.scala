package dao

import dao.model.lifted.LiftedDbPhoneRow
import models.{AccountId, Phone, PhoneId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import java.sql.Timestamp
import java.time.{Instant, OffsetDateTime, ZoneId}
import scala.concurrent.ExecutionContext

trait Tables extends HasDatabaseConfigProvider[JdbcProfile] {
  protected val dbConfigProvider: DatabaseConfigProvider
  implicit val executionContext: ExecutionContext

  import profile.api._

  implicit val timeMapping: BaseColumnType[OffsetDateTime] =
    MappedColumnType.base[OffsetDateTime, Timestamp](
      t => new Timestamp(t.toEpochSecond),
      ts => OffsetDateTime.ofInstant(Instant.ofEpochMilli(ts.getTime), ZoneId.systemDefault())
    )

  implicit val phoneIdRowKeyMapping: BaseColumnType[PhoneId] =
    MappedColumnType.base[PhoneId, Int](_.phoneId, PhoneId(_))

  implicit val accountIdRowKeyMapping: BaseColumnType[AccountId] =
    MappedColumnType.base[AccountId, Int](_.accountId, AccountId(_))

  implicit object PhoneRowShape
    extends CaseClassShape[
      Product,
      (
          Rep[PhoneId],
          Rep[Option[AccountId]],
          Rep[Option[String]],
          Rep[Option[String]],
          Rep[Option[Boolean]],
          Rep[Option[OffsetDateTime]]
        ),
      LiftedDbPhoneRow,
      (
          PhoneId,
          Option[AccountId],
          Option[String],
          Option[String],
          Option[Boolean],
          Option[OffsetDateTime]
        ),
      Phone
    ](LiftedDbPhoneRow.tupled, (Phone.apply _).tupled)

  implicit class ILikeImpl(private val s: Rep[String]) {
    def ilike(p: Rep[String]): Rep[Boolean] = {
      val expr = SimpleExpression.binary[String, String, Boolean] {
        (s, p, qb) =>
          qb.expr(s)
          qb.sqlBuilder += " ILIKE "
          qb.expr(p)
      }
      expr.apply(s, p)
    }
  }

  class Phones(tag: Tag) extends Table[Phone](tag, "phone") {
    def p: LiftedDbPhoneRow = LiftedDbPhoneRow(
      phoneId = column[PhoneId]("phone_id", O.PrimaryKey),
      accountId = column[AccountId]("account_id").?,
      phone = column[String]("phone").?,
      phoneType = column[String]("phone_type").?,
      primaryPhone = column[Boolean]("primary_phone").?,
      updateTs = column[OffsetDateTime]("update_ts").?
    )

    def * : lifted.ProvenShape[Phone] = p
  }

  val phones = lifted.TableQuery[Phones]
}
