package edu.luc.cs.pl.montecarlo
package shapes

import org.scalatest.FunSuite

/**
 * Created by sauloaguiar on 2/20/15.
 */
class RayTest extends FunSuite {

  test("whether a point belongs to a ray") {
    val ray = new Ray(new Point(1,2))
    assert(ray.contains(new Point(0,2)) == false)
    assert(ray.contains(new Point(3,2)))
  }

  test("whether a ray intercepts a lineSegment") {
    val ray = new Ray(new Point(1,1))
    val line = new LineSegment(new Point(2,1), new Point(4,3))

    println("Ray intercept: ")
    assert(ray.intercept(line))
  }
}
