package physics

import kotlin.math.sqrt

class Point2d(val x: Double, val y: Double){
    fun distance(other: Point2d): Double {
        val a = (x - other.x)
        val b = (y - other.y)
        return sqrt(a*a + b*b)
    }

    companion object {
        val origin = Point2d(0.0, 0.0)
    }

    operator fun unaryMinus(): Point2d = Point2d(-this.x, -this.y)

    operator fun plus(vector: Vector2d) = Point2d(this.x + vector.x, this.y + vector.y)
    operator fun minus(vector: Vector2d) = Point2d(this.x - vector.x, this.y - vector.y)

    operator fun minus(point: Point2d) = Vector2d(this.x - point.x, this.y - point.y)

    fun vector() = Vector2d(this.x, this.y)

    fun vectorTo(terminal: Point2d) = terminal - this

    override fun toString() = "Point2d(${this.x}, ${this.y})"
}