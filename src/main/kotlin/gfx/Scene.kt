package gfx

class Scene(val camera: Camera, val walls: List<Wall>, val floorTexture: Texture, val capabilities: SceneCapabilities = SceneCapabilities.defaults())