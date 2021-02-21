package gfx

data class SceneCapabilities(
    val noHitRgb: Int,
    val maxHitDistance: Double,
    val focalLength: Double,
    val sensorWidth: Double,
    val sensorHeight: Double,
){

    companion object {
        fun defaults(): SceneCapabilities = SceneCapabilities(
            noHitRgb = 0,
            maxHitDistance = 5.0,
            focalLength = 21.0,
            sensorWidth = 20.0,
            sensorHeight = 15.0,
        )
    }
}