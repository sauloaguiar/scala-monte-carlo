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

trait Shape {
  val points: Seq[Point]
  def area(): Double
  def boundingBox(): (Point, Point)
  def encompass(point: Point): Boolean = { false }
}


case class Location(point: Point, shape: Shape) extends Shape {
  require(shape != null, "null shape in location")
  override val points = shape.points.map(p => new Point((point.x + p.x), (point.y + p.y)))
  override def area(): Double = { shape.area() }
  override def boundingBox(): (Point, Point) = { shape.boundingBox() }
}

case class Group(val children: Shape*) extends Shape {
  require(children != null, "null children in " + getClass.getSimpleName)
  require(! children.contains(null), "null child in " + getClass.getSimpleName)

  override val points = children.flatMap(shape => shape.points)

  override def area(): Double = {
    children.foldLeft(0.0)((acc, shape) => acc + shape.area())
  }

  override def boundingBox(): (Point, Point) = {
    (new Point(points.minBy(_.x).x, points.minBy(_.y).y),
      new Point(points.maxBy(_.x).x, points.maxBy(_.y).y))
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

  override def encompass(point: Point): Boolean = {
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
    AreaCalculator.calculate(this)
  }



  def boundingBox(): (Point, Point) = {
    BoundingBox.apply(this)
  }
}
