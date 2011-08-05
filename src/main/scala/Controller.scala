import voldemort.client._

import java.security.MessageDigest

object Result {
  
  val url = "tcp://localhost:6666"
  val v = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(url)).getStoreClient[String, String]("test")

  def of(id:String, passwd:String) = {
    render(v.get(id).getValue.split(',').toList, passwd)
  }
  
  def render(data: List[String], passwd: String) =
    if (data(1) == md5(passwd)) Some(((data(0), data(2)), data(3).split('|').toList)) else None
  
  def preload() {
    println("Loading db into voldemort")
    for {
        l <- scala.io.Source.fromFile("db").getLines(); 
        val p = l.split(",").toList}
      v.put(p(0), l)
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
