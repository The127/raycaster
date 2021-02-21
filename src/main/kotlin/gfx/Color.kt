package gfx

import jdk.jfr.Percentage

class Color {

    val r: Int
    val g: Int
    val b: Int

    constructor(color: Int){
        this.r = (color shr 16) and 0xFF
        this.g = (color shr 8) and 0xFF
        this.b = color and 0xFF
    }

    constructor(r: Int, g: Int, b: Int){
        this.r = r
        this.g = g
        this.b = b
    }

    fun toInt() = (this.r shl 16) or (this.g shl 8) or this.b

    operator fun times(percentage: Double) = Color((this.r * percentage).toInt(), (this.g * percentage).toInt(), (this.b * percentage).toInt())

    fun isAlpha() = r == 255 && b == 0 && b == 255
}