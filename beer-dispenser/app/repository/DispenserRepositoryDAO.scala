package repository

import java.sql.Connection
import java.util.UUID
import anorm.{Macro, RowParser, SQL}
import anorm.SqlParser.scalar

import javax.inject.Inject
import play.api.db.Database
import models.{APIError, Dispenser}
import org.postgresql.util.PSQLException
import play.api.Logging


class DispenserRepositoryDAO @Inject()(db: Database) extends Logging  {
  val parser: RowParser[Dispenser] = Macro.namedParser[Dispenser]

  def get()(implicit conn: Connection): List[Dispenser] = {
    SQL("SELECT * FROM dispenser").as(parser.*)
  }

  def getById(id: UUID)(implicit conn: Connection): Option[Dispenser] = {
    SQL(
      "SELECT * FROM dispenser WHERE id = {id}::uuid")
      .on("id" -> id)
      .as(parser.singleOpt)
  }


  def insert(dispenser: Dispenser)(implicit conn: Connection): Option[APIError] = {
    try {
      SQL(
        "INSERT INTO dispenser (id, flow_volume, is_open, updated_at, brand, price) VALUES ({id}::uuid, {flow_volume}, {is_open}, {updated_at}, {brand}, {price})")
        .on("id" -> dispenser.id,
          "flow_volume" -> dispenser.flow_volume,
          "is_open" -> dispenser.is_open,
          "updated_at" -> dispenser.updated_at,
          "brand" -> dispenser.brand,
          "price" -> dispenser.price)
        .execute()
      None
    } catch {
      case e: PSQLException => {
        logger.error((e.getMessage()))
        Some(APIError("PSQL error when inserting dispenser"))
      }
      case e: Exception => {
        logger.error((e.getMessage()))
        Some(APIError("Unknown error when inserting dispenser"))
      }
    }
  }

    def update(dispenser: Dispenser)(implicit conn: Connection): Int = {
      println(dispenser.is_open)
      SQL(
        "UPDATE dispenser SET is_open = {is_open}, updated_at = {updated_at} WHERE id = {id}::uuid")
        .on("id" -> dispenser.id,
          "is_open" -> dispenser.is_open,
          "updated_at" -> dispenser.updated_at)
        .executeUpdate()
    }

    def delete(id: Int)(implicit conn: Connection): Int = {
      SQL("DELETE FROM dispenser WHERE id = {id}::uuid").on("id" -> id).executeUpdate()
    }
}
