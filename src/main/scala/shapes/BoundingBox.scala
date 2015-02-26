package edu.luc.cs.pl.montecarlo
package shapes

import scala.collection.mutable

/**
 * Created by sauloaguiar on 2/23/15.
 */
object BoundingBox {

  def apply(shape: Shape): (Point, Point) = shape match {
    case Polygon(points @ _*) =>
      val bounds = mutable.Map("xmin"-> Int.MaxValue.toDouble ,"ymin" -> Int.MaxValue.toDouble,"xmax" -> Int.MinValue.toDouble,"ymax" -> Int.MinValue.toDouble)
      points.foldLeft(bounds)((bound, point) => findBounds(bounds, point))
      (new Point(bounds("xmin"), bounds("ymin")),  new Point(bounds("xmax"), bounds("ymax")))

    case Location(point, shape) =>
      val (point1, point2) = this.apply(shape)
      (new Point(point.x + point1.x, point.y + point1.y), new Point(point.x + point2.x, point.y + point2.y))

    //case Group(children @ _*) =>
      //shape.points.flat
    case _ => (new Point(0, 0),  new Point(0, 0) )
  }

  def findBounds(bounds: mutable.Map[String, Double], point: Point): mutable.Map[String, Double] = {
    bounds("xmin") = Math.min(bounds("xmin"), point.x)
    bounds("ymin") = Math.min(bounds("ymin"), point.y)
    bounds("xmax") = Math.max(bounds("xmax"), point.x)
    bounds("ymax") = Math.max(bounds("ymax"), point.y)

    bounds
  }

}

