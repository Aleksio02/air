package controllers

import cats.data.EitherT
import cats.implicits.catsSyntaxOptionId
import com.google.inject.{Inject, Singleton}
import models.{Account, AccountId}
import org.apache.pekko.Done
import org.apache.pekko.util.ByteString
import play.api.http.Writeable
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, AnyContent, InjectedController, PlayBodyParsers}
import play.mvc.Http.MimeTypes
import services.AccountService

import scala.concurrent.ExecutionContext

@Singleton
class AccountController @Inject()(
                                 bodyParser: PlayBodyParsers,
                                 phoneService: AccountService
                               )(implicit ec: ExecutionContext)
  extends InjectedController {

  implicit def writable[A: Writes]: Writeable[A] = new Writeable(
    a => ByteString(Json.stringify(Json.toJson(a))),
    MimeTypes.JSON.some
  )
  implicit val doneWritable: Writeable[Done] =
    new Writeable(_ => ByteString("Ok"), MimeTypes.JSON.some)

  def create: Action[Account] =
    Action.async(bodyParser.json[Account]) { r =>
      EitherT(phoneService.create(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  def createList: Action[Seq[Account]] =
    Action.async(bodyParser.json[Seq[Account]]) { r =>
      EitherT(phoneService.createList(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  def update: Action[Account] =
    Action.async(bodyParser.json[Account]) { r =>
      EitherT(phoneService.update(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  def delete(id: String): Action[AnyContent] = Action.async { _ =>
    EitherT(phoneService.delete(AccountId(Integer.parseInt(id))))
      .fold(t => BadRequest(t.getMessage), Ok(_))
  }

  def findById(id: String): Action[AnyContent] = Action.async { _ =>
    EitherT(phoneService.findById(AccountId(Integer.parseInt(id))))
      .fold(t => BadRequest(t.getMessage), Ok(_))
  }

  /*def findByName(name: String): Action[AnyContent] = Action.async { _ =>
    EitherT(phoneService.findByName(name))
      .fold(t => BadRequest(t.getMessage), Ok(_))
  }*/

  /*private def pathToFile(filename: Path): Path =
    Paths.get(s"/tmp/scv_files/$filename")

  def upload: Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData).async { request =>
      (for {
        path <- EitherT.fromEither[Future](
          request.body
            .file("csv_file")
            .map { f =>
              val filename = Paths.get(f.filename).getFileName
              f.ref.copyTo(pathToFile(filename), replace = true)
              filename
            }
            .toRight(new NoSuchElementException())
        )
        rows = CSVReader
          .open(pathToFile(path).toFile)
          .allWithHeaders()
          .flatMap(WebPasswordRowCreate(_))
        _ <- EitherT(phoneService.createList(rows))
      } yield {
        Done
      }).fold(t => BadRequest(t.getMessage), Ok(_))
    }*/
}