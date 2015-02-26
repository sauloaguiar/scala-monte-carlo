package edu.luc.cs.pl.montecarlo
package shapes

/**
 * Created by sauloaguiar on 2/25/15.
 */
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
