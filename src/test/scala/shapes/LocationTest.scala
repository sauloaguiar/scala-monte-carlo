package edu.luc.cs.pl.montecarlo
package shapes

import org.scalatest.FunSuite

/**
 * Created by sauloaguiar on 2/23/15.
 */
class LocationTest extends FunSuite {

  test("Assert that triangle polygon under Location was translated"){
    val fixture = Fixtures.triangleTranslated
    //val triangle = new Polygon(point00, point22, point40, point00) + point20
    assert(fixture.points(0) == new Point(2, 0))
    assert(fixture.points(1) == new Point(4, 2))
    assert(fixture.points(2) == new Point(6, 0))
    assert(fixture.points(3) == new Point(2, 0))
  }

  test("Assert that star polygon under Location was translated") {
    val fixture = Fixtures.complex

    assert(fixture.points(0) == Fixtures.p1)
    assert(fixture.points(1) == Fixtures.p2)
    assert(fixture.points(2) == Fixtures.p3)
    assert(fixture.points(3) == Fixtures.p4)
    assert(fixture.points(4) == Fixtures.p5)
    assert(fixture.points(5) == Fixtures.p6)
    assert(fixture.points(6) == Fixtures.p7)
    assert(fixture.points(7) == Fixtures.p8)
    assert(fixture.points(8) == Fixtures.p9)

    val translated = new Location(new Point(10,15), fixture)

    assert(translated.points(0) == new Point(30, 25))
    assert(translated.points(1) == new Point(50, 45))
    assert(translated.points(2) == new Point(30, 65))
    assert(translated.points(3) == new Point(60, 85))
    assert(translated.points(4) == new Point(90, 65))
    assert(translated.points(5) == new Point(90, 45))
    assert(translated.points(6) == new Point(70, 35))
    assert(translated.points(7) == new Point(70, 15))
    assert(translated.points(8) == new Point(50, 35))
  }

}
