import com.mongodb.casbah.Imports._

import java.security.MessageDigest

object Result {
  val mongo = MongoConnection()("test")
  val mongoColl = mongo("ra")

  def of(id:String, passwd:String) = {
    val data = mongoColl.findOneByID(id)
    data match {
      case Some(_) => { 
        val d = data.get
        render((d("_id").toString, d("p").toString, d("n").toString, d("r").toString), passwd)
      }
      case _ => None
    }
  }
  
  def render(data: (String,String,String,String), passwd: String) =
    if (data._2 == md5(passwd)) Some((data._1, data._3), List.fromString(data._4, '|')) else None
  
  def preload() {
    println("Loading db into mem")
    mongoColl.drop()
    for {
        l <- scala.io.Source.fromFile("db").getLines(); 
        val p = l.split(",").toList;
        val mo = MongoDBObject(
          "_id" -> p(0), 
          "p" -> p(1),
          "n" -> p(2),
          "r" -> p(3))}
      mongoColl.insert(mo)
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
