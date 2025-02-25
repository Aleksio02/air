package dao

import dao.model.lifted.LiftedDbPhoneRow
import models.{AccountId, Phone, PhoneId}
import models.CustomFormats._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted

import java.sql.Timestamp
import java.time.{LocalDateTime, ZoneId}
import scala.concurrent.ExecutionContext

trait Tables extends HasDatabaseConfigProvider[JdbcProfile] {
  protected val dbConfigProvider: DatabaseConfigProvider
  implicit val executionContext: ExecutionContext

  import profile.api._

  implicit val timeMapping: BaseColumnType[LocalDateTime] =
    MappedColumnType.base[LocalDateTime, Timestamp](
      t => new Timestamp(t.atZone(ZoneId.systemDefault()).toEpochSecond),
      ts => ts.toLocalDateTime
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
          Rep[Option[LocalDateTime]]
        ),
      LiftedDbPhoneRow,
      (
          PhoneId,
          Option[AccountId],
          Option[String],
          Option[String],
          Option[Boolean],
          Option[LocalDateTime]
        ),
      Phone
    ](LiftedDbPhoneRow.tupled, Phone.tupled)

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
      accountId = column[Option[AccountId]]("account_id"),
      phone = column[Option[String]]("phone"),
      phoneType = column[Option[String]]("phone_type"),
      primaryPhone = column[Option[Boolean]]("primary_phone"),
      updateTs = column[Option[LocalDateTime]]("update_ts")
    )

    def * : lifted.ProvenShape[Phone] = p
  }

  val phones = lifted.TableQuery[Phones]
}
