import org.scalaquery.ql.basic.{BasicTable => Table}
import org.scalaquery.ql.TypeMapper._
import org.scalaquery.ql._


object Results extends Table[(String, String, String, String)]("results") {
  def id = column[String]("id", O PrimaryKey)
  def passwd = column[String]("passwd", O NotNull)
  def name = column[String]("name", O NotNull)
  def result = column[String]("result", O NotNull)
  def * = id ~ passwd ~ name ~ result
}

