package edu.luc.cs.pl.montecarlo
package shapes

import org.scalatest.FunSuite

/**
 * Created by sauloaguiar on 2/18/15.
 */
class PolygonTest extends FunSuite {

  test("Reject an open polygon") {
    intercept[IllegalArgumentException] {
      new Polygon(new Point(1,1), new Point(2,2), new Point(3,3), new Point(4,4))
    }
  }

  test("Assert that polygon is closed") {
    new Polygon(new Point(1,1), new Point(2,2), new Point(2,3), new Point(1,1))
  }

  test("Assert that polygon has at least four points") {
    intercept[IllegalArgumentException] {
      new Polygon(new Point(1,1), new Point(2,2), new Point(2,3))
    }
  }

  test("Assert that triangle polygon has these lines") {
    val lines = Fixtures.triangle.getLines()
    assert(lines(0) == new LineSegment(Fixtures.point00, Fixtures.point22))
    assert(lines(1) == new LineSegment(Fixtures.point22, Fixtures.point40))
    assert(lines(2) == new LineSegment(Fixtures.point40, Fixtures.point00))
  }

  test("Assert that square polygon has these lines") {
    val lines = Fixtures.square.getLines()
    assert(lines(0) == new LineSegment(Fixtures.point00, Fixtures.point01))
    assert(lines(1) == new LineSegment(Fixtures.point01, Fixtures.point11))
    assert(lines(2) == new LineSegment(Fixtures.point11, Fixtures.point10))
    assert(lines(3) == new LineSegment(Fixtures.point10, Fixtures.point00))
  }

  test("Assert that triangle polygon contains point") {
    val triangle = Fixtures.triangle
    assert(triangle.encompass(new Point(2,1)))

    assert(triangle.encompass(new Point(5,4)) == false)
  }

  test("Assert that lines contains the point") {
    val triangle = Fixtures.triangle

    assert(triangle.pointLaysOnAnyEdge(Fixtures.point11))
    assert(triangle.pointLaysOnAnyEdge(new Point(5,5)) == false)
  }

  test("Assert that interception counts right") {
    val triangle = Fixtures.triangle
    assert(triangle.pointLiesInsidePolygon(Fixtures.point21))
    assert(triangle.pointLiesInsidePolygon(Fixtures.point42) == false)
  }


  test("Assert that square polygon contains a set of points") {
    val square = Fixtures.square
    assert(square.encompass(Fixtures.point10))
    assert(square.encompass(Fixtures.point11))
    assert(square.encompass(new Point(0.5, 0.5)))
    assert(square.encompass(Fixtures.point1Minus1) == false)
    assert(square.encompass(Fixtures.point0Minus1) == false)
  }

  test("Assert that triangle polygon has the expected BoundingBox") {
    val triangle = Fixtures.triangle
    assert(triangle.boundingBox() == (Fixtures.point00, Fixtures.point42))
  }

  test("Assert that star polygon has the expected BoundingBox") {
    val star = Fixtures.star
    assert(star.boundingBox() == (Fixtures.pointMinus2Minus4, Fixtures.point22))
  }

  test("Assert that triangle polygon has a good approximation area") {
    val triangle = Fixtures.triangle

    assert(math.abs(4.0 - triangle.area()) < Fixtures.precision)
  }

  test("Assert that complex polygon has a good approximation area") {
    val complex = Fixtures.star
    val area = complex.area()
    println("area: " + area)
    assert(math.abs(2 - area) < Fixtures.precision)
  }


}
