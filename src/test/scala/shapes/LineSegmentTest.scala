package edu.luc.cs.pl.montecarlo
package shapes

import org.junit.Test
import org.scalatest.FunSuite

/**
 * Created by sauloaguiar on 2/19/15.
 */
class LineSegmentTest extends FunSuite {

  test("Assert That Line Segment Intersects Ray") {
    val line = new LineSegment(new Point(2,1), new Point(4,3))
    val ray = new Ray(new Point(1,1))
    assert(line.interceptAtAny(ray) == Some(Point(2, 1)))
  }

  test("Assert that Line Segment is Parallel to Ray") {
    val line = new LineSegment(new Point(1,2), new Point(2,2))
    val ray = new Ray(new Point(1,1))

    assert(line.interceptAtAny(ray) == None)
  }

  test("Assert that a Point belongs to a LineSegment") {
    val line = new LineSegment(new Point(2,1 ), new Point(4,3))
    assert(line.contains(new Point(2, 1)))

    val line2 = new LineSegment(new Point(1,2), new Point(1,6))
    assert(line2.contains(new Point(1,3)))
  }

  test("Assert that line and ray are parallel") {
    val line = new LineSegment(new Point(1,1), new Point(1,2))
    val ray = new Ray(new Point(1,3))

    assert(line.intercept(ray) == false)
  }

  test("Assert that fixture lines are parallel") {
    //assert(Fixtures.lineXequals1.intercept(Fixtures.lineXequalsMinus1))
  }
}
