package edu.luc.cs.pl.montecarlo
package shapes

/**
 * Created by sauloaguiar on 2/20/15.
 */
object Fixtures {

  val lineYequals1 = new LineSegment(new Point(1,1), new Point(2,1))

  val lineYequalsMinus1 = new LineSegment(new Point(1,-1), new Point(2,-1))

  val lineXequals1 = new LineSegment(new Point(1,1), new Point(1,2))

  val lineXequalsMinus1 = new LineSegment(new Point(-1,1), new Point(-1, 0))

  val lineXequalsY = new LineSegment(new Point(1,1), new Point(2,2))

  val lineXequalsMinusY = new LineSegment(new Point(-1,1), new Point(0,0))
}
