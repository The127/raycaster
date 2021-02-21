package gfx

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

open class Texture {

    val buffer: IntArray
    val width: Int
    val height: Int

    constructor(width: Int, height: Int)
            : this(width, height, 0)

    constructor(width: Int, height: Int, rgb: Int)
            : this(IntArray(width * height){ _ -> rgb}, width)

    constructor(buffer: IntArray, width: Int){
        this.buffer = buffer
        this.width = width
        this.height = buffer.size / width
    }

    fun pixelAt(x: Int, y: Int) = buffer[x + y * this.width]
    
    companion object{
        fun read(file: String): Texture{
            var imageFile = File(file)

            var image = ImageIO.read(imageFile)
            var buffer = IntArray(image.width * image.height)
            for(x in 0 until image.width)
                for(y in 0 until image.height)
                    buffer[x + y*image.width] = image.getRGB(x, y)
            return Texture(buffer, image.width)
        }
    }
}