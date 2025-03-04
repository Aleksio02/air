package dao

import dao.model.lifted.{LiftedDbAccountRow, LiftedDbFrequentFlyerRow, LiftedDbPhoneRow}
import dao.model.views.{AccountView, LiftedAccountView}
import models.{Account, AccountId, FrequentFlyer, FrequentFlyerId, Phone, PhoneId}
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

  implicit val frequentFlyerIdRowKeyMapping: BaseColumnType[FrequentFlyerId] =
    MappedColumnType.base[FrequentFlyerId, Int](_.frequentFlyerId, FrequentFlyerId(_))

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

  implicit object AccountRowShape
    extends CaseClassShape[
      Product,
      (
        Rep[AccountId],
          Rep[String],
          Rep[String],
          Rep[String],
          Rep[Option[FrequentFlyerId]],
          Rep[Option[OffsetDateTime]]
        ),
      LiftedDbAccountRow,
      (
        AccountId,
          String,
          String,
          String,
          Option[FrequentFlyerId],
          Option[OffsetDateTime]
        )
      ,
      Account
    ](LiftedDbAccountRow.tupled, (Account.apply _).tupled)

  implicit object FrequentFlyerRowShape
    extends CaseClassShape[
      Product,
      (
        Rep[FrequentFlyerId],
          Rep[String],
          Rep[String],
          Rep[String],
          Rep[String],
          Rep[Int],
          Rep[Int],
          Rep[String],
          Rep[String],
          Rep[Option[OffsetDateTime]]
        ),
      LiftedDbFrequentFlyerRow,
      (
        FrequentFlyerId,
          String,
          String,
          String,
          String,
          Int,
          Int,
          String,
          String,
          Option[OffsetDateTime]
        ),
      FrequentFlyer
    ](LiftedDbFrequentFlyerRow.tupled, (FrequentFlyer.apply _).tupled)

  implicit object AccountViewRowShape
    extends CaseClassShape[
      Product,
      (
        Rep[Option[FrequentFlyerId]],
          Rep[String],
          Rep[String],
          Rep[String],
          Rep[String],
          Rep[Int],
          Rep[Int],
          Rep[String],
          Rep[String],
          Rep[AccountId],
          Rep[String]
        ),
      LiftedAccountView,
      (
        Option[FrequentFlyerId],
          String,
          String,
          String,
          String,
          Int,
          Int,
          String,
          String,
          AccountId,
          String
        ),
      AccountView
    ](LiftedAccountView.tupled, (AccountView.apply _).tupled)


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

  class Accounts(tag: Tag) extends Table[Account](tag, "account") {
    def p: LiftedDbAccountRow = LiftedDbAccountRow(
      accountId = column[AccountId]("account_id", O.PrimaryKey),
      login = column[String]("login"),
      firstName = column[String]("first_name"),
      lastName = column[String]("last_name"),
      frequentFlyerId = column[FrequentFlyerId]("frequent_flyer_id").?,
      updateTs = column[OffsetDateTime]("update_ts").?
    )

    def * : lifted.ProvenShape[Account] = p
  }

  class FrequentFlyers(tag: Tag) extends Table[FrequentFlyer](tag, "frequent_flyer") {
    def p: LiftedDbFrequentFlyerRow = LiftedDbFrequentFlyerRow(
      frequentFlyerId = column[FrequentFlyerId]("frequent_flyer_id", O.PrimaryKey),
      firstName = column[String]("first_name"),
      lastName = column[String]("last_name"),
      title = column[String]("title"),
      cardNum = column[String]("card_num"),
      level = column[Int]("level"),
      awardPoints = column[Int]("award_points"),
      email = column[String]("email"),
      phone = column[String]("phone"),
      updateTs = column[OffsetDateTime]("update_ts").?
    )

    def * : lifted.ProvenShape[FrequentFlyer] = p
  }

  val phones = lifted.TableQuery[Phones]
  val accounts = lifted.TableQuery[Accounts]
  val frequentFlyers = lifted.TableQuery[FrequentFlyers]

}


