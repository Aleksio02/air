package controllers

import akka.Done
import akka.util.ByteString
import cats.data.EitherT
import cats.implicits.catsSyntaxOptionId
import models.{Phone, PhoneId}
import play.api.http.Writeable
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import play.mvc.Http.MimeTypes
import services.PhoneService

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class PhoneController @Inject()(
                                 bodyParser: PlayBodyParsers,
                                 phoneService: PhoneService
                               )(implicit ec: ExecutionContext)
  extends InjectedController {

  implicit def writable[A: Writes]: Writeable[A] = new Writeable(
    a => ByteString(Json.stringify(Json.toJson(a))),
    MimeTypes.JSON.some
  )
  implicit val doneWritable: Writeable[Done] =
    new Writeable(_ => ByteString("Ok"), MimeTypes.JSON.some)

  def create: Action[Phone] =
    Action.async(bodyParser.json[Phone]) { r =>
      EitherT(phoneService.create(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  def createList: Action[Seq[Phone]] =
    Action.async(bodyParser.json[Seq[Phone]]) { r =>
      EitherT(phoneService.createList(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  def update: Action[Phone] =
    Action.async(bodyParser.json[Phone]) { r =>
      EitherT(phoneService.update(r.body))
        .fold(t => BadRequest(t.getMessage), Ok(_))
    }

  /*def delete(id: String): Action[AnyContent] = Action.async { _ =>
    EitherT(phoneService.delete(PasswordRowKey(id)))
      .fold(t => BadRequest(t.getMessage), Ok(_))
  }*/

  def findById(id: String): Action[AnyContent] = Action.async { _ =>
    EitherT(phoneService.findById(PhoneId(Integer.parseInt(id))))
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

