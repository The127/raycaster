package gfx

import physics.Point2d
import physics.Ray2d

class RayHit(val ray: Ray2d, val point: Point2d, val distance: Double, val wall: Wall)