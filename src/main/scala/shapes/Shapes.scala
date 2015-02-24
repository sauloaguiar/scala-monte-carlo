package edu.luc.cs.pl.montecarlo
package shapes

import scala.collection.mutable
/**
 * Created by sauloaguiar on 2/18/15.
 */
case class Point(x: Double, y: Double)

trait Segment {
  def intercept(raySegment: Ray): Boolean
  def contains(point: Point): Boolean
}

trait Shape {
  val points: Seq[Point]
  def area(): Double
}


case class Location(point: Point, shape: Shape) extends Shape {
  require(shape != null, "null shape in location")
  override val points = shape.points.map(p => new Point((point.x + p.x), (point.y + p.y)))
  override def area(): Double = { shape.area() }
}

case class Group(val children: Shape*) extends Shape {
  require(children != null, "null children in " + getClass.getSimpleName)
  require(! children.contains(null), "null child in " + getClass.getSimpleName)

  override val points = Seq[Point]()

  override def area(): Double = {
    children.foldLeft(0.0)((acc, shape) => acc + shape.area())
    //0.0
  }
}

case class LineSegment(starting: Point, second: Point) extends Segment {
  require(starting != null && second != null && starting != second)

  def intercept(line: LineSegment): Boolean = {
    val point = interceptAtAny(line)
    if (point != None) {
      contains(point.get) && line.contains(point.get)
    } else {
      false
    }
  }

  def intercept(raySegment: Ray): Boolean = {
    val point = interceptAtAny(raySegment)
    //println("Point from intersection: " + point)
    if (point != None){
      contains(point.get) && raySegment.contains(point.get)
    } else {
      false
    }
  }

  def interceptAtAny(lineSegment: LineSegment): Option[Point] = {
    val line = GeometryUtils.getLine(starting, second)
    val ray = GeometryUtils.getLine(lineSegment.starting, lineSegment.second)

    val det = line.A * ray.B - ray.A * line.B
    if (det == 0) None
    else {
      val x = (ray.B * line.C - line.B * ray.C)/det
      val y = (line.A*ray.C - ray.A*line.C)/det
      Some(new Point(x,y))
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
    val line = GeometryUtils.getLine(starting, second)
    (math.min(starting.x, second.x) <= point.x &&  point.x <= math.max(starting.x, second.x)) &&
      (math.min(starting.y, second.y) <= point.y && point.y <= math.max(starting.y, second.y)) &&
        ((line.A * point.x + line.B * point.y) == line.C)
  }

  def contains2(point: Point): Boolean = {
    false
  }
}

object GeometryUtils {

  def getLine(starting: Point): Equation = {
    getLine(starting, new Point(starting.x + 1, starting.y))
  }

  /**
   * Line is the format Ax + By = C
   * Generates a Line parallel to X axis based on the starting point
   * @return Line coefficients as stated in http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=geometry2
   */
  def getLine(starting: Point, ending: Point): Equation = {
    val A = ending.y.toDouble - starting.y.toDouble
    val B = starting.x.toDouble - ending.x.toDouble
    val C = A * starting.x.toDouble + B * starting.y.toDouble

    //println("A, B, C: " + A + ", " + B + ", " + C)
    new Equation(A,B,C)
  }

  /**
   * Calculates the area of a bounding box delimited by points min an max
   * A = width * height
   * @param min
   * @param max
   * @return
   */
  def boundingBoxArea(min: Point, max: Point): Double = {
    ((max.x - min.x) * (max.y - min.y))
  }

}

class Equation(val A: Double, val B: Double, val C: Double) {
  override def equals(o: Any) = o match {
    case that: Equation => this.A == that.A && this.B == that.B && this.C == that.C
    case _ => false
  }
}

case class Ray(starting: Point) {
  require(starting != null)

  def intercept(lineSegment: Segment): Boolean = {
    lineSegment.intercept(this)
  }

  def contains(point: Point): Boolean = {
    val line = GeometryUtils.getLine(starting)
    (point.x >= starting.x && point.y >= starting.y) && ((line.A * point.x + line.B * point.y) == line.C)
  }
}

case class Polygon(points: Point*) extends Shape {
  require(points.size > 3)
  require(points.toList(0) == points.last)

  def getLines(): List[LineSegment] = {
    var lineSegmentList: List[LineSegment] = List()
    val it = points.iterator
    var currentPoint = it.next()
    while (it.hasNext) {
      val nextPoint = it.next()
      lineSegmentList = lineSegmentList :+ new LineSegment(currentPoint, nextPoint)
      currentPoint = nextPoint
    }
    lineSegmentList
  }

  def pointLaysOnAnyEdge(point: Point): Boolean = {
    getLines().exists(edge => edge.contains(point))
  }

  def encompass(point: Point): Boolean = {
    val edge = pointLaysOnAnyEdge(point)
    val inside = pointLiesInsidePolygon(point)
     edge || inside
  }

  def pointLiesInsidePolygon(point: Point): Boolean = {
    val ray = new Ray(point)
    val counter = getLines().foldLeft(0) { (acc, edge) =>
      if (edge.intercept(ray)) {
        acc + 1
      } else {
        acc
      }
    }
    if (counter > 0 && counter % 2 != 0) true
    else false
  }

  def area(): Double = {
    val box = boundingBox()
    val attempts = 1000000.0
    var counter = 0
    for ( step <- 1 to attempts.toInt) {
      val x = random(box._1.x, box._2.x)
      val y = random(box._1.y, box._2.y)
      val p = new Point(x, y)
      if (encompass(p)) counter += 1
    }

    (counter/attempts) * GeometryUtils.boundingBoxArea(box._1, box._2)
  }


  private def random(min: Double, max: Double): Double = {
    scala.util.Random.nextDouble() * (min + (max - min))
  }

  def boundingBox(): (Point, Point) = {
    BoundingBox.apply(this)
  }
}
