package demo.components

/**
 * Created by Omid on 5/19/2015.
 */



  import japgolly.scalajs.react._
  //import japgolly.scalajs.react.vdom.all._
  import japgolly.scalajs.react.vdom.all.{div,className,p,onClick,onMouseDown,onMouseUp,onMouseMove,draggable,pointerEvents}
  import fr.iscpif.scaladget.d3._
  import panel._
  import japgolly.scalajs.react.vdom.svg.all._


object Arrow {

  case class State(current: String)


  case class Props(color: String,
                   p1: (Double, Double), p2: (Double, Double),
                   onDown: (ReactMouseEvent) => _ ,
                   onUp: (ReactMouseEvent) => _,
                   onMove: (ReactMouseEvent) => _ )

  //  case class Props(color: String, onClick: Unit => Unit)

  class Backend($: BackendScope[Props, _]) {
    def onDown(e:ReactMouseEvent):Unit = {
      println("Down!")
      $.props.onDown(e)
    }

    def onUp(e:ReactMouseEvent):Unit = {
      println("UP!")
      $.props.onUp(e)
    }

    def onMove(e:ReactMouseEvent):Unit = {
      println("Moving!")
      $.props.onMove(e)
    }

    def secondUp(e:ReactMouseEvent):Unit = {
      println("I am BLUE")
      $.props.onMove(e)
    }

    def OnClick(e:ReactMouseEvent):Unit = {
      println("CLICK")
    }

  }

  val arrow = ReactComponentB[Props]("CircleComponent")
    .stateless
    .backend(new Backend(_))
    .render((props, _, b) =>
    g(//transform := "translate(100, 0)",
      //  onKeyPress ==> b.onClick,
      line(x1 := props.p1._1, y1 := props.p1._2, x2 := props.p2._1, y2 := props.p2._2
        , stroke := "gold", strokeWidth := 2),
      circle(
        onMouseDown ==> b.onDown,
        onMouseUp ==> b.onUp,
        onMouseMove ==> b.onMove,
        onClick ==> b.OnClick,
        cx := props.p1._1, cy := props.p1._2, r := "20", fill := props.color)
      ,
      circle(
        onClick ==> b.OnClick,
        onMouseUp ==> b.secondUp,
        cx := props.p2._1, cy := props.p2._2, r := "15", fill := "blue")
    ))
    .build
}