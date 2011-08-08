import me.prettyprint.hector.api._
import factory._
import ddl._
import me.prettyprint.cassandra.service._
import template._
import me.prettyprint.cassandra.serializers.StringSerializer

import java.security.MessageDigest
import java.util.Arrays

object Result {
  val cluster = HFactory.getOrCreateCluster("test-cluster", "localhost:9160")
  val keySpace = "RA"
  var _ksp: Keyspace = null
  var _template: ColumnFamilyTemplate[String, String] = null
  def ksp = {
    if (_ksp == null) 
      _ksp = HFactory.createKeyspace(keySpace, cluster)
    _ksp
  }
  def template = {
    if (_template == null) 
      _template = new ThriftColumnFamilyTemplate[String, String](ksp, "f", StringSerializer.get(), StringSerializer.get())
    _template
  }

  def of(id:String, passwd:String) = {
    val res = template.queryColumns(id);
    render((id, res.getString("p"), res.getString("n"), res.getString("r")) ,passwd)
  }
  
  def render(data: (String,String,String,String), passwd: String) =
    if (data._2 == md5(passwd)) Some((data._1, data._3), List.fromString(data._4, '|')) else None
  
  def preload() {
    println("Loading db into mem")
    if (cluster.describeKeyspace(keySpace) == null)
      createSchema();
    
    for {
        l <- scala.io.Source.fromFile("db").getLines(); 
        val p = l.split(",").toList;
        val u = template.createUpdater(p(0))} {
      u.setString("p", p(1))
      u.setString("n", p(2))
      u.setString("r", p(3))
      template.update(u)
    }
    println("Loading - done")
  }
  
  def createSchema() {
    val cfDef = HFactory.createColumnFamilyDefinition(keySpace, "f", ComparatorType.BYTESTYPE);
    val keyspace = HFactory.createKeyspaceDefinition(keySpace, ThriftKsDef.DEF_STRATEGY_CLASS, 1, Arrays.asList(cfDef));
    cluster.addKeyspace(keyspace);
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
