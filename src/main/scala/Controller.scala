import com.redis._

import java.security.MessageDigest

object Result {
  val host = "localhost"
  val port = 6379

  def of(id:String, passwd:String) = {
    val r = new RedisClient(host, port)
    render(r.lrange[String](id, 0, 3).get, passwd)
  }
  
  def render(data: List[Option[String]], passwd: String) =
    if (data(1).get == md5(passwd)) Some(((data(0).get, data(2).get), data(3).get.split('|').toList)) else None
  
  def preload() {
    val r = new RedisClient(host, port)
    println("Loading db into redis")
    for {
        l <- scala.io.Source.fromFile("db").getLines(); 
        val p = l.split(",").toList}
      p.foreach(r.rpush(p(0),_))
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
