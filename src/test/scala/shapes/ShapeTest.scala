package edu.luc.cs.pl.montecarlo
package shapes

import org.scalatest.FunSuite

/**
 * Created by sauloaguiar on 2/23/15.
 */
class ShapeTest extends FunSuite {

  test("Assert that basic shape has correct area") {
    val fixture = Fixtures.square

    assert(fixture.area() == 1)
  }

  test("Assert that group shape of size 2 has correct area") {
    val area = (new Group(Fixtures.square, new Location(Fixtures.point11, Fixtures.square))).area()
    assert(math.abs(area - 2.0) < Fixtures.precision)
  }

  test("Assert that group shape within nested group has correct area") {
    val area = (new Group(Fixtures.triangle, new Group(Fixtures.square, new Location(Fixtures.p1, Fixtures.triangle)))).area()
    assert(math.abs(area - 9.0) < Fixtures.precision)
  }
}
