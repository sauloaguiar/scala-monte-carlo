package edu.luc.cs.pl.montecarlo
package shapes

/**
 * Created by sauloaguiar on 2/18/15.
 */
case class Point(x: Double, y: Double)

trait Segment {
  def intercept(raySegment: Ray): Boolean
  def contains(point: Point): Boolean
}

case class LineSegment(starting: Point, second: Point) extends Segment {
  require(starting != null && second != null && starting != second)

  def intercept(raySegment: Ray): Boolean = {
    val point = interceptAtAny(raySegment)
    println("Point from intersection: " + point)
    if (point != None){
      contains(point.get) && raySegment.contains(point.get)
    } else {
      false
    }
  }

  /**
   * http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=geometry2
   * @param raySegment
   * @return Some[Point] if the parameter ray intercepts this LineSegment.
   *         Else, returns None
   */
  def interceptAtAny(raySegment: Ray): Option[Point] = {
    val line = GeometryUtils.getLine(starting, second)
    val ray = GeometryUtils.getLine(raySegment.starting)

    val det = line.A * ray.B - ray.A * line.B
    if (det == 0) None
    else {
      val x = (ray.B * line.C - line.B * ray.C)/det
      val y = (line.A*ray.C - ray.A*line.C)/det
      Some(new Point(x,y))
    }
  }

  def contains(point: Point): Boolean = {
    (math.min(starting.x, second.x) <= point.x &&  point.x <= math.max(starting.x, second.x)) &&
      (math.min(starting.y, second.y) <= point.y && point.y <= math.max(starting.y, second.y))
  }
}

object GeometryUtils {

  def getLine(starting: Point): Line = {
    getLine(starting, new Point(starting.x + 1, starting.y))
  }

  /**
   * Line is the format Ax + By = C
   * Generates a Line parallel to X axis based on the starting point
   * @return Line coefficients as stated in http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=geometry2
   */
  def getLine(starting: Point, ending: Point): Line = {
    val A = ending.y.toDouble - starting.y.toDouble
    val B = starting.x.toDouble - ending.x.toDouble
    val C = A * starting.x.toDouble + B * starting.y.toDouble

    println("A, B, C: " + A + ", " + B + ", " + C)
    new Line(A,B,C)
  }
}

class Line(val A: Double, val B: Double, val C: Double) {
  override def equals(o: Any) = o match {
    case that: Line => this.A == that.A && this.B == that.B && this.C == that.C
    case _ => false
  }
}


case class Ray(starting: Point) {
  require(starting != null)
  // check wether a point belongs to a ray

  def intercept(lineSegment: Segment): Boolean = {
    lineSegment.intercept(this)
  }

  def contains(point: Point): Boolean = {
    val line = GeometryUtils.getLine(starting)
    (point.x >= starting.x && point.y >= starting.y) && ((line.A * point.x + line.B * point.y) == line.C)
  }


}


