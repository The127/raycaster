package gui

import java.awt.Canvas
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage

class GameScene : Canvas {

    private val displayMode: DisplayMode
    val buffer: BufferedImage

    constructor(displayMode: DisplayMode){
        this.displayMode = displayMode

        preferredSize = Dimension(displayMode.wdith, displayMode.height)

        buffer = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .defaultScreenDevice
            .defaultConfiguration
            .createCompatibleImage(displayMode.resX, displayMode.resY, Transparency.OPAQUE)
    }

    fun refresh() {
        var bs = bufferStrategy
        var g = bs.drawGraphics

        g.drawImage(buffer, 0, 0, displayMode.wdith, displayMode.height, null)

        g.dispose()
        bs.show()
    }
}