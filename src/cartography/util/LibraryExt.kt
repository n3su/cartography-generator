/**
 * LibraryExt
 * Extension functions for RuneMate dependency.
 */
package cartography.util

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.location.Coordinate
import kotlin.math.abs
import kotlin.math.atan2

/* ------------------------------ ------------------------------ */
// Collision related flags.
/* ------------------------------ ------------------------------ */

/**
 * Directional movement blocking flags.
 * https://github.com/runelite/runelite/blob/3a6af379598652672ee5ce39d0e433a1ad7eb37e/runelite-api/src/main/java/net/runelite/api/CollisionDataFlag.java#L35-L42
 */
const val BLOCK_MOVEMENT_NORTH_WEST = 0x1
const val BLOCK_MOVEMENT_NORTH = 0x2
const val BLOCK_MOVEMENT_NORTH_EAST = 0x4
const val BLOCK_MOVEMENT_EAST = 0x8
const val BLOCK_MOVEMENT_SOUTH_EAST = 0x10
const val BLOCK_MOVEMENT_SOUTH = 0x20
const val BLOCK_MOVEMENT_SOUTH_WEST = 0x40
const val BLOCK_MOVEMENT_WEST = 0x80

/**
* Movement blocking tiles.
* https://github.com/runelite/runelite/blob/3a6af379598652672ee5ce39d0e433a1ad7eb37e/runelite-api/src/main/java/net/runelite/api/CollisionDataFlag.java#L47-L53
*/
const val BLOCK_MOVEMENT_OBJECT = 0x100
const val BLOCK_MOVEMENT_FLOOR_DECORATION = 0x40000
const val BLOCK_MOVEMENT_FLOOR = 0x200000 // Eg. water
const val BLOCK_MOVEMENT_FULL = BLOCK_MOVEMENT_OBJECT or BLOCK_MOVEMENT_FLOOR_DECORATION or BLOCK_MOVEMENT_FLOOR

object Direction
{
    const val NORTH = 0
    const val NORTH_EAST = 1
    const val EAST = 2
    const val SOUTH_EAST = 3
    const val SOUTH = 4
    const val SOUTH_WEST = 5
    const val WEST = 6
    const val NORTH_WEST = 7

    val MAPPED_DIRECTIONS = hashMapOf<Int, Int>(
            NORTH to BLOCK_MOVEMENT_NORTH,
            NORTH_EAST to (BLOCK_MOVEMENT_NORTH_EAST or BLOCK_MOVEMENT_NORTH or BLOCK_MOVEMENT_EAST),
            EAST to BLOCK_MOVEMENT_EAST,
            SOUTH_EAST to (BLOCK_MOVEMENT_SOUTH_EAST or BLOCK_MOVEMENT_SOUTH or BLOCK_MOVEMENT_EAST),
            SOUTH to BLOCK_MOVEMENT_SOUTH,
            SOUTH_WEST to (BLOCK_MOVEMENT_SOUTH_WEST or BLOCK_MOVEMENT_SOUTH or BLOCK_MOVEMENT_WEST),
            WEST to BLOCK_MOVEMENT_WEST,
            NORTH_WEST to (BLOCK_MOVEMENT_NORTH_WEST or BLOCK_MOVEMENT_NORTH or BLOCK_MOVEMENT_WEST)
    )

    /**
     * Angle map by direction provides middle angle of area.
     */
    val MAPPED_ANGLES = hashMapOf<Int, Double>(
            NORTH to 90.0,
            NORTH_EAST to 45.0,
            EAST to 0.0,
            SOUTH_EAST to 315.0,
            SOUTH to 270.0,
            SOUTH_WEST to 225.0,
            WEST to 180.0,
            NORTH_WEST to 135.0
    )
}

/* ------------------------------ ------------------------------ */
// RuneMate logging functions.
/* ------------------------------ ------------------------------ */

@Suppress("FunctionName")
fun DEBUG(message: String): Unit = Environment.getLogger().run { debug(message) }

@Suppress("FunctionName")
fun ERROR(message: String): Unit = Environment.getLogger().run { severe(message) }

@Suppress("FunctionName")
fun EXIT(message: String = "Bot terminated."): Unit = Environment.getBot().run { stop(message) }

/* ------------------------------ ------------------------------ */
// Area and coordinate extensions.
/* ------------------------------ ------------------------------ */

/**
 * Unique identifier for [Coordinate] object.
 */
val Coordinate.id: Int
    get() = hashCode()

/**
 * Getter for neighbors of specific [Coordinate] object.
 */
val Coordinate.surroundings: List<Coordinate>
    get() = area.surroundingCoordinates

/**
 * Get angle between two points.
 */
fun Coordinate.angle(point: Coordinate): Double {
    val angle = Math.toDegrees(atan2((point.y - y).toDouble(), (point.x - x).toDouble()))
    return if (angle < 0) 360.0 + angle else angle
}

/**
 * Get directions based on angle of two points.
 */
fun Coordinate.direction(point: Coordinate) = Direction.MAPPED_ANGLES.filterValues {
    abs(this.angle(point) - it) <= 22.5 }.keys.first()