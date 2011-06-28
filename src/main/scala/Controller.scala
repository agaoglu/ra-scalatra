import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.extended.MySQLDriver.Implicit._

import java.security.MessageDigest

object Result {
  val db = Database.forURL("jdbc:mysql:///ss?user=root", driver = "com.mysql.jdbc.Driver")
  def of(id:String, passwd:String) = {
    db withSession {
      val q = for (e <- Results if e.id === id) yield e
      val data = q.first
      if (data._3 == md5(passwd)) Some(data) else None
    }
  }

  def md5(s:String) = {
    val md5 = MessageDigest.getInstance("MD5")
    md5.update(s.getBytes("UTF-8"))
    md5.digest().map(0xFF & _).map("%02x".format(_)).foldLeft(""){_ + _}
  }
}

object Run {
  //val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
  val db = Database.forURL("jdbc:mysql:///ss?user=root", driver = "com.mysql.jdbc.Driver")
  def main(args:Array[String]) {
    db withSession {
      (Results.ddl) create
    }
    /*
    Result of("1005918", "1005918") match {
      case Some(_) => println("found")
      case _ => println("not found")
    }
    val q1 = for(u <- Results) yield u
    db withSession {
      //Results insert ("123", "Erdem", "R")
      println(q1.first)
    }
    */
  }
}
