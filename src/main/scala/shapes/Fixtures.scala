package edu.luc.cs.pl.montecarlo
package shapes

/**
 * Created by sauloaguiar on 2/20/15.
 */
object Fixtures {

  val precision = 0.005

  val lineYequals1 = new LineSegment(new Point(1,1), new Point(2,1))

  val lineYequalsMinus1 = new LineSegment(new Point(1,-1), new Point(2,-1))

  val lineXequals1 = new LineSegment(new Point(1,1), new Point(1,2))

  val lineXequalsMinus1 = new LineSegment(new Point(-1,1), new Point(-1, 0))

  val lineXequalsY = new LineSegment(new Point(0,0), new Point(2,2))

  val lineXequalsMinusY = new LineSegment(new Point(-1,1), new Point(0,0))

  val point00 = new Point(0,0)
  val point01 = new Point(0,1)
  val point11 = new Point(1,1)
  val point10 = new Point(1,0)
  val point22 = new Point(2,2)
  val point40 = new Point(4,0)
  val point42 = new Point(4,2)

  val point02 = new Point(0,2)
  val point20 = new Point(2,0)
  val point1Minus1 = new Point(1,-1)
  val point2Minus4 = new Point(2,-4)
  val point0Minus1 = new Point(0,-1)
  val pointMinus2Minus4 = new Point(-2,-4)
  val pointMinus1Minus1 = new Point(-1,-1)
  val pointMinus20 = new Point(-2,0)
  val point21 = new Point(2,1)
  val pointMinus10 = new Point(-1,0)

  val point20_0 = new Point(20, 0)
  val point40_10 = new Point(40, 10)
  val point40_20 = new Point(40, 20)
  val point30_30 = new Point(30, 30)
  val point30_40 = new Point(30, 40)
  val point20_30 = new Point(20, 30)
  val point10_35 = new Point(10, 35)
  val point20_20 = new Point(20, 20)
  val point10_10 = new Point(10, 10)

  val p1 = Point(20, 10)
  val p2 = Point(40, 30)
  val p3 = Point(20, 50)
  val p4 = Point(50, 70)
  val p5 = Point(80, 50)
  val p6 = Point(80, 30)
  val p7 = Point(60, 20)
  val p8 = Point(60, 0)
  val p9 = Point(40, 20)

  val triangle = new Polygon(point00, point22, point40, point00)

  val square = new Polygon(point00, point01, point11, point10, point00)

  val star = new Polygon(point02, point10, point20, point1Minus1, point2Minus4, point0Minus1, pointMinus2Minus4,
    pointMinus1Minus1, pointMinus20, pointMinus10, point02)

  val complex = new Polygon(p1, p2, p3, p4, p5, p6, p7, p8, p9, p1)

  val triangleTranslated = new Location(point20, triangle)
}
