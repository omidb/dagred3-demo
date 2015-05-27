package demo

import scala.scalajs.js

/**
 * Created by Omid on 5/23/2015.
 */

case class RPoint(x:Double, y:Double)
case class RLine(p1:RPoint, p2:RPoint)
case class RVector(x:Double, y:Double)

object ReactUtil {
  def getEdgeID(e1:String, e2:String) = e1 + "-->" + e2
  def parseEdgeID(e:String) = e.split("-->")

  def move(p: js.Array[Double]) = s"translate(${p(0)},${p(1)})"

  def textBoundingBox(p: js.Array[String]) = RVector(p.map(_.size).max * 7.5,p.size * 20)
}
