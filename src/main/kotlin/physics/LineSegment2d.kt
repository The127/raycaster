package physics

class LineSegment2d {
    val a: Point2d
    val b: Point2d

    constructor(a: Point2d, b: Point2d){
        this.a = a
        this.b = b
    }

    constructor(a1: Double, a2: Double, b1: Double, b2: Double)
            : this(Point2d(a1, a2), Point2d(b1, b2))

    fun point(): Point2d = this.a
    fun vector(): Vector2d = this.a.vectorTo(this.b)

    override fun toString() = "LineSegment2d(${this.a} - ${this.b})"

    fun length() = this.a.distance(this.b)
}

