package demo.components

/**
 * Created by Omid on 5/23/2015.
 */

import Dagre.GraphNode
import com.sun.org.apache.xml.internal.security.utils.XMLUtils
import demo.{ReactUtil, RVector, RPoint}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all.{onClick,onMouseUp,id,className}
import japgolly.scalajs.react.vdom.svg.all._
import paths.mid._

import scala.scalajs.js






object SampleCom {

  case class UpState2(signalling:Boolean)

  case class State(upState: UpState2)


  case class Props(color:String
                   , onMsUp: (UpState2) => _)

  class Backend($: BackendScope[Props, _]) {
    def onClick() = {
      println("clicked!")
      //      $.props.onClick()
    }

    def onUp(e:ReactMouseEventI):Unit = {
      println("UP here!  " +  $.props.color)
      $.props.onMsUp(UpState2(true))
    }

    def onDown(e:ReactMouseEventI):Unit = {

    }
  }

  val sample = ReactComponentB[Props]("MNodeComponent")
    .stateless
    .backend(new Backend(_))
    .render((props, _, b) => {
//    svg( width := 800, height := 1000,

      g(
        circle(onMouseUp ==> b.onUp,cx := 100, cy := 100, r:= 25, fill:= props.color)
      )
//    )
  }
    )
    .build
}
