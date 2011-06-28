import org.scalatra._
import java.net.URL
import scalate.ScalateSupport

class ResultAnnouncer extends ScalatraFilter with ScalateSupport {

  before {
    contentType = "text/html"
  }
  
  get("/") {
    templateEngine.layout("/WEB-INF/scalate/templates/main.jade")
  }

  get("/r/:idn/:pass") {
    val r = Result of (params("idn"),params("pass"))
    r match {
      case Some(_) => {
        val v = r.get
        templateEngine.layout("/WEB-INF/scalate/templates/result.jade", Map("detail"->v._1, "score"->v._2))
      }
      case _ => templateEngine.layout("/WEB-INF/scalate/templates/main.jade", Map("formErr"->"ID Number and/or password is wrong"))
    }
  }

  notFound {
    // If no route matches, then try to render a Scaml template
    val templateBase = requestPath match {
      case s if s.endsWith("/") => s + "index"
      case s => s
    }
    val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".scaml"
    servletContext.getResource(templatePath) match {
      case url: URL => 
        contentType = "text/html"
        templateEngine.layout(templatePath)
      case _ => 
        filterChain.doFilter(request, response)
    } 
  }
}
