package gui

import gfx.Canvas
import java.awt.Frame
import java.awt.GraphicsConfiguration

class Window : Frame {

    val displayMode: DisplayMode
    private val gameScene: GameScene

    constructor(title: String, displayMode: DisplayMode){
        this.displayMode = displayMode

        gameScene = GameScene(displayMode)

        add(gameScene)
        isResizable = false
        pack()

        this.title = title
        setLocationRelativeTo(null)

        gameScene.createBufferStrategy(displayMode.bufferMode.toBufferSize())
    }

    fun refresh(): Unit = gameScene.refresh()

    fun createCanvas(): Canvas = Canvas(gameScene.buffer)
}