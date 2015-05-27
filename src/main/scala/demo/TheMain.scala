package demo

import demo.components.MainComponent
import japgolly.scalajs.react.React
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
 * Created by Omid on 5/26/2015.
 */

@JSExport("TheMain")
object TheMain extends js.JSApp{
  @JSExport
  def main(): Unit = {
    React.render(MainComponent.TopLevel(), dom.document.body)
  }
}
