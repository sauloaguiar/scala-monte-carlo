package edu.luc.cs.pl.montecarlo
package shapes

/**
 * Created by sauloaguiar on 2/25/15.
 */
object AreaCalculator {

  def calculate(shape: Shape): Double = shape match {
    case Polygon(points @ _ *) =>
      val box = shape.boundingBox()
      val attempts = 1000000.0
      var counter = 0
      for ( step <- 1 to attempts.toInt) {
        val x = random(box._1.x, box._2.x)
        val y = random(box._1.y, box._2.y)
        val p = new Point(x, y)
        if (shape.encompass(p)) counter += 1
      }
      (counter/attempts) * GeometryUtils.boundingBoxArea(box._1, box._2)

    case _ => 0.0
   }

  private def random(min: Double, max: Double): Double = {
    scala.util.Random.nextDouble() * (min + (max - min))
  }

}
