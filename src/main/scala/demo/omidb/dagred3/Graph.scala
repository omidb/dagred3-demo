import scala.scalajs.js
import js.annotation._


package Dagre {

import demo.ReactUtil

trait DagreFactory extends js.Object {
  var graphlib: GraphLib = js.native

  def layout(graph: Graph): Unit = js.native
}

trait GraphAtrs extends js.Object {
  //Direction for rank nodes.
  // Can be TB, BT, LR, or RL, where T = top, B = bottom, L = left, and R = right.
  def rankdir: String = js.native

  //Number of pixels that separate nodes horizontally in the layout.
  def nodesep: Double = js.native

  //Number of pixels that separate nodes horizontally in the layout.
  def edgesep: Double = js.native

  //Number of pixels between each rank in the layout
  def ranksep: Double = js.native

  //Number of pixels to use as a margin around the left and right of the graph.
  def marginx: Double = js.native

  //Number of pixels to use as a margin around the left and right of the graph.
  def marginy: Double = js.native
}

object GraphAtrs {
  def apply(rankdir:String, nodesep:Double, edgesep:Double, ranksep:Double, marginx:Double, marginy:Double):GraphAtrs = {
    js.Dynamic.literal(rankdir = rankdir, nodesep = nodesep, edgesep = edgesep, ranksep = ranksep, marginx = marginx, marginy = marginy).asInstanceOf[GraphAtrs]
  }
}


trait GraphNode extends js.Object {
  def label: String = js.native

  def width: Double = js.native

  def height: Double = js.native

  def x: Double = js.native

  def y: Double = js.native
}

object GraphNode {
  def apply(label:String, width:Double, height:Double, x:Double, y:Double):GraphNode = {
    js.Dynamic.literal(label = label, width = width, height = width, x = x, y = y).asInstanceOf[GraphNode]
  }

  def apply(label:String, width:Double, height:Double):GraphNode = {
    js.Dynamic.literal(label = label, width = width, height = width).asInstanceOf[GraphNode]
  }

  def apply(label:String):GraphNode = {
    val a = ReactUtil.textBoundingBox(js.Array(label))
    js.Dynamic.literal(label = label, width = a.x, height = a.y).asInstanceOf[GraphNode]
  }
}

trait DagrePoint extends js.Object {
  def x:Double = js.native
  def y:Double = js.native
}

trait GraphEdge extends js.Object {
  def v: String = js.native

  def w: String = js.native

//  def points: Array[(Double,Double)] = js.native
}

trait GraphEdgePath extends js.Object {
  def points: js.Array[DagrePoint] = js.native
  def x:js.UndefOr[Double] = js.native
  def y:js.UndefOr[Double] = js.native
}

object GraphEdge {
  def apply(v:String, w:String):GraphEdge = {
    js.Dynamic.literal(v = v, w = w).asInstanceOf[GraphEdge]
  }
}




trait Graph extends js.Object {
  /* ??? ConstructorMember(FunSignature(List(),List(),Some(TypeRef(TypeName(Graph),List())))) */
  def edges(): js.Array[GraphEdge] = js.native

  def edge(id: GraphEdge): GraphEdgePath = js.native

  def nodes(): js.Array[String] = js.native

  def node(id: String): GraphNode = js.native

  def setDefaultEdgeLabel(callback: js.Function0[js.Object]): Graph = js.native

  def setEdge(sourceId: String, targetId: String): Graph = js.native
  def setEdge(sourceId: String, targetId: String,  label:js.Object): Graph = js.native


  def setGraph(options: js.Any): Graph = js.native

  def setGraph(options: GraphAtrs): Graph = js.native

  def setNode(id: String, node: GraphNode): Graph = js.native
}


trait GraphLib extends js.Object {
  def Graph:Graph = js.native
}

}


package object Dagre extends js.GlobalScope {
  val dagre: Dagre.DagreFactory = js.native
}
