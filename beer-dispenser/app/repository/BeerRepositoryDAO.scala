package repository

import anorm.{Macro, RowParser, SQL}
import models.{APIError, Beer}
import org.postgresql.util.PSQLException
import play.api.Logging
import play.api.db.Database

import java.sql.Connection
import java.util.UUID
import javax.inject.Inject


class BeerRepositoryDAO @Inject()(db: Database) extends Logging  {
  val parser: RowParser[Beer] = Macro.namedParser[Beer]

  def get()(implicit conn: Connection): List[Beer] = {
    SQL("SELECT * FROM Beer").as(parser.*)
  }

  def insert(beer: Beer)(implicit conn: Connection): Option[APIError] = {
    try {
      SQL(
        "INSERT INTO Beer (id, duration, dispenser_id, total, day) VALUES ({id}::uuid, {duration}, {dispenserId}::uuid, {total}, {day})")
        .on("id" -> beer.id,
          "duration" -> beer.duration,
          "dispenserId" -> beer.dispenserId,
          "total" -> beer.total,
          "day" -> beer.day)
        .execute()
      None
    } catch {
      case e: PSQLException => {
        logger.error((e.getMessage()))
        Some(APIError("PSQL error when inserting beer"))
      }
      case e: Exception => {
        logger.error((e.getMessage()))
        Some(APIError("Unknown error when inserting beer"))
      }
    }
  }
}
