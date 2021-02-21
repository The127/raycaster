package physics

import kotlin.math.*

class Vector2d{
    val x: Double
    val y: Double

    constructor(x: Double, y: Double){
        this.x = x
        this.y = y
    }

    constructor(degrees: Double){
        x = cos(Math.toRadians(degrees))
        y = sin(Math.toRadians(degrees))
    }

    fun radians() = atan2(this.y, this.x)

    inline infix fun x(other: Vector2d) = (this.x * other.y) - (this.y * other.x)

    fun point(): Point2d = Point2d(this.x, this.y)

    operator fun div(scalar: Double) = Vector2d(this.x / scalar, this.y / scalar)
    operator fun times(scalar: Double) = Vector2d(this.x * scalar, this.y * scalar)

    override fun toString() = "Vector2d(${this.x}, ${this.y})"

    inline infix fun dot(other: Vector2d) = (this.x*other.x) + (this.y*other.y)

    fun length() = sqrt(x*x + y*y)

    fun normalized(): Vector2d {
        val length = this.length()
        return Vector2d(this.x / length, this.y / length)
    }

    fun rotate(degrees: Double): Vector2d {
        val rad = Math.toRadians(degrees)
        val cos = cos(rad)
        val sin = sin(rad)
        return Vector2d(this.x * cos - this.y * sin, this.x * sin + this.y * cos)
    }

    companion object{
        fun cartesian(magnitude: Double, angle: Double) = Vector2d(magnitude * cos(Math.toRadians(angle)), magnitude * sin(Math.toRadians(angle)))
    }
}