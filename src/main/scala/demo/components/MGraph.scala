package demo.components

/**
 * Created by Omid on 5/19/2015.
 */


import demo.{ReactUtil, SGraph, RVector, RPoint}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all.{div,className,onMouseMove,onMouseUp,ref}
import japgolly.scalajs.react.vdom.svg.all._

import scala.scalajs.js


object MGraph {

  case class RRec(toplef:RPoint,size:RVector)

  case class REdge(id:String, /*label: Option[String], labelProp:Option[RRec],*/
                   points:js.Array[RPoint])
  case class RNode(id:String, label: String, labelProp:RRec)

  case class DrawingState(nodes: IndexedSeq[RNode], edges:IndexedSeq[REdge])

  case class DragState(arrowsDrgStates:Map[String,MArrow.DraggingState],
                       nodesUpStates:Map[String,MNode.UpState])

  case class State(dragState:DragState, drw:DrawingState, gr:Dagre.Graph)

  case class GraphProps(g:Dagre.Graph, sg:SGraph, changeGraph: (String,(String,String)) => _)

  object DrawUtil {
    def getAllNode (s:State, b:Backend) = {
      val nds = s.drw.nodes
      nds.map(n =>
        MNode.mnode(
          MNode.Props(n.id, n.label, n.labelProp.toplef, n.labelProp.size,b.onNodeUp _)))
    }

    def getAllEdges (s:State, b:Backend) = {
      val eds = s.drw.edges
      eds.map(e =>
      MArrow.marrow(
        MArrow.Props(e.id,
          e.points.map(p => RPoint(p.x, p.y)),
          MArrow.DraggingState(e.id, false),
          b.onDownEdge _))
      )
    }
  }


  class Backend($: BackendScope[GraphProps, State]) {

    val svgref = Ref("svg")

    def onDownEdge(d:MArrow.DraggingState) :Unit = {
      val current = $.state.dragState.arrowsDrgStates
      val modified = current + (d.id -> d)

      $.modState(_.copy(dragState = $.state.dragState.copy(arrowsDrgStates = modified)))
      println("DownEdge!")
    }

    def onMoveSVG(e: ReactMouseEventI):Unit = {
      val p = RPoint(getPos(e.clientX,e.clientY)._1,getPos(e.clientX,e.clientY)._2)

      val drgObj = $.state.dragState.arrowsDrgStates.filter(_._2.startDraging == true)


      if(drgObj.size > 0){
        val ed = $.state.drw.edges.zipWithIndex.filter(_._1.id == drgObj.head._1).head
        val modifiedED = ed._1.copy(points =
          js.Array(p,
            RPoint((p.x + ed._1.points.last.x)/2,(p.y + ed._1.points.last.y)/2)
            ,ed._1.points.last))
        val modifiedEdgeList = $.state.drw.edges.updated(ed._2, modifiedED)
//        val modifiedDrw = $.state.drw.copy(edges = modifiedEdgeList)
        $.modState(_.copy(drw = $.state.drw.copy(edges = modifiedEdgeList)))
      }
    }

    def getPos(x:Double, y:Double) = {
      val r = svgref($).get.getDOMNode().asInstanceOf[TopNode].getBoundingClientRect()
      (x - r.left, y - r.top)
    }
    //
    def onUpSVG(e: ReactMouseEventI):Unit = {
      println("on Up SVG")
      //if($.state.dragState.nodesUpStates.)
      $.setState(getInitState($.props))

//      val startDrgStates = $.state.dragState.arrowsDrgStates.filter(_._2.startDraging == true)
//      val startDrgUpStates = $.state.dragState.nodesUpStates.filter(_._2.UPed == true)




//      println($.state.dragState.nodesUpStates)
//      if(startDrgStates.size > 0 && startDrgUpStates.size>0) {
//        val s = startDrgUpStates.head._2
//
//        val edgeID = startDrgStates.head._1
//        val oldEdge = $.props.sg.edges.filter(_.id == edgeID).head
//        println("Droppppeed  "+edgeID)
//        $.modState(_.copy(dragState =
//          $.state.dragState.copy(arrowsDrgStates = $.state.dragState
//            .arrowsDrgStates
//            .updated(startDrgStates.head._1,
//              startDrgStates.head._2.copy(startDraging = false)))))
//
//        $.props.changeGraph(
//          oldEdge.id,
//          (s.id,ReactUtil.parseEdgeID(edgeID)(1)))
//
//        //$.setState(getInitState($.props))
//        //$.modState(_.copy(dragState = $.state.dragState.copy(nodesUpStates = modified)))
//      }
    }

    def onNodeUp(s: MNode.UpState):Unit = {
      val current = $.state.dragState.nodesUpStates
      val modified = current.updated(s.id, s)
      println(modified)
      $.modState(_.copy(dragState = $.state.dragState.copy(nodesUpStates = modified)))
      val startDrgStates = $.state.dragState.arrowsDrgStates.filter(_._2.startDraging == true)
      if(startDrgStates.size > 0) {
        val edgeID = startDrgStates.head._1
        val oldEdge = $.props.sg.edges.filter(_.id == edgeID).head
        println("Droppppeed  "+edgeID)

        //
        $.modState(_.copy(dragState =
          $.state.dragState.copy(arrowsDrgStates = $.state.dragState
          .arrowsDrgStates
          .updated(startDrgStates.head._1,
            startDrgStates.head._2.copy(startDraging = false)))))

        $.props.changeGraph(
          oldEdge.id,
          (s.id,ReactUtil.parseEdgeID(edgeID)(1)))
        $.forceUpdate()
        //$.setState(getInitState($.props))
        //$.modState(_.copy(dragState = $.state.dragState.copy(nodesUpStates = modified)))
      }

    }

  }

  def getInitState(pr:GraphProps):State = {

    def getAllNodes = {
      val g = pr.g
      g.nodes().map(ns => {
        val n = g.node(ns)
        RNode(ns,n.label,RRec(RPoint(n.x, n.y),RVector(n.width,n.height)))
        //MNode.mnode(MNode.Props(n.label, n, RPoint(n.x, n.y), RVector(n.width, n.height)))
      })
    }

    def getAllEdges = {
      val g = pr.g
      g.edges().map {
        es => {
          val e = g.edge(es)
          //if (e.x.isDefined) println(e.x.get)
          REdge(
            ReactUtil.getEdgeID(es.v , es.w),
//            None, //it should be label
//            None, //it should be label bounding box
            e.points.map(p => RPoint(p.x, p.y))
          )
        }
      }
    }

    State(
      DragState(
        pr.sg.edges.map(e => e.id -> MArrow.DraggingState(e.id, false)).toMap,
        pr.sg.nodes.map(n => n.id -> MNode.UpState(n.id, false)).toMap),
      DrawingState(getAllNodes, getAllEdges),pr.g)
  }

  //val MGraph = ReactComponentB[GraphProps]("Top level component")

  val mGraph = ReactComponentB[GraphProps]("Top level component")
    .getInitialState(getInitState)
    //.initialState(State(DragState(Map.empty),GraphProps(None)))
    .backend(new Backend(_))
    .render((props,state,b) => {
    //div(id:="topDiv",className := "container",
      svg(ref:= b.svgref, width := 800, height := 1000,
        onMouseMove ==> b.onMoveSVG,
        onMouseUp ==> b.onUpSVG,
//        g(
          DrawUtil.getAllEdges(state, b),
          DrawUtil.getAllNode(state, b)
//        )
      )
    //)
  }).build

}