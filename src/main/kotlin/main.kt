import gfx.*
import gui.BufferMode
import gui.DisplayMode
import gui.Window
import physics.LineSegment2d
import physics.Point2d
import physics.Ray2d
import physics.Vector2d
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val l = LineSegment2d(Point2d(0.0, 1.0, ), Point2d(1.0, 0.0))
    val r = Ray2d(Point2d(-1.0, 2.0), Vector2d(1.1, -1.1))

    val dm = DisplayMode(800, 600, 800, 600, BufferMode.Single)
    val window = Window("Test", dm)
    window.isVisible = true

    val cam = Camera(Point2d.origin, 72.0, 270.0)

    val tex = Texture.read("./src/main/resources/textures/wall_tile_1_brick.png")

    val roomScale = 3

    var walls = listOf(
        Wall(LineSegment2d(Point2d( 1.0 * roomScale,  1.0 * roomScale), Point2d(-1.0 * roomScale,  1.0 * roomScale)), tex),
        Wall(LineSegment2d(Point2d(-1.0 * roomScale,  1.0 + roomScale), Point2d(-1.0 * roomScale, -1.0 * roomScale)), Texture.read("./src/main/resources/textures/wall_tile_1_brick_banner.png")),
        Wall(LineSegment2d(Point2d(-1.0 * roomScale, -1.0 * roomScale), Point2d( 1.0 * roomScale, -1.0 * roomScale)), Texture.read("./src/main/resources/textures/wall_tile_1_brick_grown.png")),
        Wall(LineSegment2d(Point2d( 1.0 * roomScale, -1.0 * roomScale), Point2d( 1.0 * roomScale,  1.0 * roomScale)), Texture.read("./src/main/resources/textures/img.png")),
    )

    val scene = Scene(cam, walls, tex)

    val raycaster = Raycaster(window)



    while(true){
        cam.rotationDegrees += .05

        raycaster.render(scene)
        window.refresh()
    }
}