package gfx

import gui.Window
import physics.Point2d
import physics.Ray2d
import physics.Vector2d
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class Raycaster(private val window: Window) {

    val canvas: Canvas = window.createCanvas()

    fun clearScreen(scene: Scene){
        for (i in canvas.buffer.indices){
            canvas.buffer[i] = scene.capabilities.noHitRgb
        }
    }

    fun render(scene: Scene){
        clearScreen(scene)
        renderFloor(scene)
        renderWalls(scene)
    }

    private fun renderWalls(scene: Scene){
        val degreesPerColumn = scene.camera.fieldOfView / window.displayMode.resX.toDouble()
        val camStartDegrees = scene.camera.rotationDegrees - (scene.camera.fieldOfView / 2.0)

        for (columnIndex in 0 until window.displayMode.resX){
            val rayDegrees = camStartDegrees + (columnIndex * degreesPerColumn)
            val ray = Ray2d(scene.camera.position, Vector2d(rayDegrees))

            val hit = scene
                .walls
                .mapNotNull { castRay(scene.camera, ray, it) }
                .minByOrNull { it.distance }

            if(hit != null){
//                println("$ray - ${intersection.first} - ${intersection.second}")

                if(hit.distance <= scene.capabilities.maxHitDistance){
                }
                renderColumn(scene, columnIndex, hit)
            }
        }
    }

    private fun castRay(camera: Camera, ray: Ray2d, wall: Wall): RayHit?{
        val hitPoint = ray intersect wall.lineSegment ?: return null
        val distance = ray.point.distance(hitPoint)
        val correctedDistance = distance * cos(Math.toRadians(camera.rotationDegrees) - ray.vector.radians())
        return RayHit(ray, hitPoint, correctedDistance, wall)
    }

    private fun renderColumn(scene: Scene, columnIndex: Int, hit: RayHit): Unit {
        val distancePercent = (100 - hit.distance / (scene.capabilities.maxHitDistance / 100.0)) / 100.0

        val scale = (scene.capabilities.focalLength / hit.distance) * scene.capabilities.sensorHeight * 2

//        val drawHeight = (window.displayMode.resY / hit.distance).roundToInt()
        val drawHeight = scale.roundToInt() // (scene.capabilities.sensorHeight * (100.0 / hit.distance)).roundToInt()

//        println("$distancePercent - $drawHeight")

        val offset = ((window.displayMode.resY - drawHeight) / 2.0).roundToInt()

        for (i in 0 until drawHeight){
            if(offset + i < 0 || offset + i > window.displayMode.resY-1)
                continue

            val textureYIndex = (hit.wall.texture.height * i / drawHeight + 0.5).toInt()

            val hitWallLength = hit.wall.lineSegment.a.distance(hit.point)
            val hitWallRepeatedLength = hitWallLength - hitWallLength.toInt()

            val textureXIndex = ((hit.wall.texture.width - 1) * hitWallRepeatedLength + 0.5).toInt()

            val textureColor = hit.wall.texture.pixelAt(textureXIndex, textureYIndex)

            val color = Color(textureColor)
            if(color.isAlpha())
                continue

            canvas.buffer[(offset + i).toInt() * window.displayMode.resX + columnIndex] = color.times(distancePercent).toInt()
        }
    }

    private fun renderFloor(scene: Scene){
        var offset = window.displayMode.resY / 2

        val verticalCameraAngle = Math.toDegrees(2 * atan(scene.capabilities.sensorHeight / (2.0 * scene.capabilities.focalLength)))
        val halfVerticalCameraAngle = verticalCameraAngle / 2.0

        val cameraAngleVerticalStepPerScanLine = halfVerticalCameraAngle / offset
        val cameraVerticalStartAngle = 90.0 - halfVerticalCameraAngle

        val horizontalCameraAngle = Math.toDegrees(2 * atan(scene.capabilities.sensorWidth / (2.0 * scene.capabilities.focalLength)))
        val halfHorizontalCameraAngle = horizontalCameraAngle / 2.0
        val leftAngle = 90.0 - halfHorizontalCameraAngle

        for(scanLine in 0 until offset){
            val verticalScanlineAngle = cameraVerticalStartAngle + cameraAngleVerticalStepPerScanLine * scanLine
            val shadingPercent =  0.9 - verticalScanlineAngle / 100.0

            val verticalFloorAngle = 90.0 - verticalScanlineAngle
            val depth = (1.0 / sin(Math.toRadians(verticalFloorAngle))) * sin(Math.toRadians(verticalScanlineAngle))

            val halfHorizontalDistance = (depth / sin(Math.toRadians(leftAngle))) * sin(Math.toRadians(halfHorizontalCameraAngle))
            val horizontalDistance = halfHorizontalDistance * 2.0

            for(x in 0 until window.displayMode.resX){

                val horizontalPercent = x.toDouble() / window.displayMode.resX
                val floorPointOffset = Vector2d(depth, -halfHorizontalDistance + horizontalDistance*horizontalPercent)
                val correctedFloorPoint = floorPointOffset.rotate(scene.camera.rotationDegrees)//Vector2d(correctedDistanceX, correctedDistanceY).rotate(scene.camera.rotationDegrees).point()


                var texX = (scene.floorTexture.width * (correctedFloorPoint.x - correctedFloorPoint.x.toInt())).toInt()
                if(texX < 0) texX += scene.floorTexture.width

                var texY = (scene.floorTexture.height * (correctedFloorPoint.y - correctedFloorPoint.y.toInt())).toInt()
                if(texY < 0) texY += scene.floorTexture.height

                val texturePixel = scene.floorTexture.pixelAt(texY, texX)

                canvas.buffer[(window.displayMode.resY -1 - scanLine) * window.displayMode.resX + x] = Color(texturePixel).times(shadingPercent).toInt()
            }
        }
    }
}