import org.apache.hadoop.hbase._
import client._
import util.Bytes

import java.security.MessageDigest

object Result {
  implicit def _tb(s: String) = Bytes.toBytes(s)
  implicit def _ts(b: Array[Byte]) = Bytes.toString(b)
  
  val tname = "ra"
  val cfname = "f" 
  val conf = HBaseConfiguration.create
  val admin = new HBaseAdmin(conf)

  def of(id:String, passwd:String) = {
    val table = new HTable(conf, tname)
    val result = table.get(new Get(id))
    if (result.isEmpty()) None else {
      val r = result.getFamilyMap(cfname)
      render((result.getRow(), r.get(_tb("p")), r.get(_tb("n")), r.get(_tb("r"))), passwd)
    }
  }
  
  def render(data: (String,String,String,String), passwd: String) =
    if (data._2 == md5(passwd)) Some((data._1, data._3), List.fromString(data._4, '|')) else None
  
  def preload() {
    println("Loading db into HBase")
    val htd = new HTableDescriptor(tname)
    htd.addFamily(new HColumnDescriptor(cfname))
    
    try { admin.createTable(htd) }   
    catch {
      case e: Exception => None 
    }
    
    val table = new HTable(conf, tname)
    for (l <- scala.io.Source.fromFile("db").getLines()) {
      val p = l.split(",").toList;
      val put = new Put(p(0))
      put.add("f", "p", p(1));
      put.add("f", "n", p(2));
      put.add("f", "r", p(3))
      table.put(put)
    }
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
