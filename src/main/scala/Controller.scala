import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.extended.MySQLDriver.Implicit._

import java.security.MessageDigest

object Result {
  val db = Database.forURL(Conf.url, driver=Conf.driver, user=Conf.user, password=Conf.password)
  def of(id:String, passwd:String) = {
    db withSession {
      val q = for (e <- Results if e.id === id) yield e
      val data = q.first
      if (data._2 == md5(passwd)) Some((data._1, data._3),List.fromString(data._4, '|')) else None
    }
  }

  def md5(s:String) = {
    val md5 = MessageDigest.getInstance("MD5")
    md5.update(s.getBytes("UTF-8"))
    md5.digest().map(0xFF & _).map("%02x".format(_)).foldLeft(""){_ + _}
  }
}

object Run {
  def main(args:Array[String]) {
    Result.db withSession {
      (Results.ddl) create
    }
  }
}
