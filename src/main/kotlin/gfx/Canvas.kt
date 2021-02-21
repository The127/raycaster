package gfx

import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt

class Canvas : Texture {

    constructor(canvas: BufferedImage)
            : super((canvas.raster.dataBuffer as DataBufferInt).data, canvas.width)
}