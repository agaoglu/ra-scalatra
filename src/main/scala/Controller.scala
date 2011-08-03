import scala.collection.mutable.Map

import java.security.MessageDigest

object Result {
  val store = Map[String, (String, String, String, String)]()

  def of(id:String, passwd:String) = {
    val data = store.get(id)
    data match {
      case Some(_) => render(data.get, id)
      case _ => None
    }
  }
  
  def render(data: (String,String,String,String), passwd: String) =
    if (data._2 == md5(passwd)) Some((data._1, data._3), List.fromString(data._4, '|')) else None
  
  def preload() {
    println("Loading db into mem")
    for {
        l <- scala.io.Source.fromFile("db").getLines(); 
        val p = l.split(",").toList}
      store += p(0) -> (p(0), p(1), p(2), p(3))
    println("Loading - done")
  }

  def md5(s:String) = {
    val md5 = MessageDigest.getInstance("MD5")
    md5.update(s.getBytes("UTF-8"))
    md5.digest().map(0xFF & _).map("%02x".format(_)).foldLeft(""){_ + _}
  }
}

object Run {
  def main(args:Array[String]) {
  }
}
