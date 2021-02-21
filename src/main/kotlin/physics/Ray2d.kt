package physics

import kotlin.math.abs
import kotlin.math.absoluteValue

class Ray2d(val point: Point2d, val vector: Vector2d){
    inline infix fun intersect(lineSegment: LineSegment2d): Point2d? {
        //see: https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect/565282#565282

        val p = this.point
        val r = this.vector

        val q = lineSegment.point()
        val s = lineSegment.vector()

        var rxs = r x s

        if(rxs.absoluteValue < 0.00001){
            // simplification for collinear lines => no intersection needed for camera
            return null
        }else {
            var qmp = q - p
            val t = qmp x s / rxs
            val u = qmp x r / rxs

//            if (t in 0.0..1.0 && u in 0.0..1.0) {
            // check if hit point is on line segment
            if (u in 0.0..1.0) {
                var hitPoint = p + r * t

                // check if hit point is in fron of camera
                var pointVector = hitPoint - this.point
                if(pointVector dot this.vector > 0)
                    return hitPoint
            }
        }
        return null
    }

    override fun toString() = "Ray2d(${this.point}, ${this.vector})"
}