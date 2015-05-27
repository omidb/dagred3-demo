package demo.components

/**
 * Created by Omid on 5/19/2015.
 */
import scala.scalajs.js
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._

object panel {

  case class PanelContent(id: Option[String], title: String, text: String)

  val Panel = ReactComponentB[PanelContent]("Panel")
    .render((content, children) => {
    div(className := "col-md-12", id := content.id,
      div(className := "panel panel-info",
        div(className := "panel-heading",
          h2(className := "panel-title", content.title)
        ),
        div(className := "panel-body",
          p(className := "alert alert-info", content.text),
          children
        )
      )
    )
  })
    .build
}