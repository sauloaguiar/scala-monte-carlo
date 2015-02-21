package edu.luc.cs.pl.montecarlo
package shapes

import junit.framework.Assert._
import org.junit._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * Created by sauloaguiar on 2/18/15.
 */
class GeometryUtilsTest extends FunSuite {

  test("assertThatGeometryGeneratesRay") {
    assert(GeometryUtils.getLine(new Point(1, 1)) == new Equation(0, -1, -1))

    assert(GeometryUtils.getLine(new Point(3, 2)) == new Equation(0, -1, -2))

    assert(GeometryUtils.getLine(new Point(-1, -3)) == new Equation(0, -1, 3))

    assert(GeometryUtils.getLine(new Point(0, 5)) == new Equation(0, -1, -5))
  }

  test("assertsThatGeometryGeneratesLine") {
    assert(GeometryUtils.getLine(new Point(2, 1), new Point(4, 3)) == new Equation(2, -2, 2))
  }

}