import org.scalatra._
import java.net.URL
import scalate.ScalateSupport

class ResultAnnouncer extends ScalatraFilter with ScalateSupport {
  
  override def initialize(config: Config) = {
    super.initialize(config)
    Result.preload()
  }

  before {
    contentType = "text/html"
  }
  
  get("/") {
    templateEngine.layout("/WEB-INF/scalate/templates/main.jade")
  }

  post("/r/:idn") {
    val result = Result of (params("idn"), params("pass"))
    result match {
      case Some(_) => {
        val v = result.get
        templateEngine.layout(
          "/WEB-INF/scalate/templates/result.jade", Map("detail"->v._1, "score"->v._2))
      }
      case _ => templateEngine.layout(
        "/WEB-INF/scalate/templates/main.jade", Map("formErr"->"ID Number and/or password is wrong"))
    }
  }
}
