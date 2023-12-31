package utils
/**
 * A rectangular region defined by coordinate ranges.
 *
 * @property width  The width of the region.
 * @property height The height of the region.
 * @property xRange The value range for the x coordinate of all points within the region.
 * @property yRange The value range for the y coordinate of all points within the region.
 */
data class Area(val xRange: LongRange, val yRange: LongRange) : Iterable<Point2d> {

    /** Alternate constructor that calculates the region from the [bottomLeft] and [topRight] points of a rectangle. */
    constructor(bottomLeft: Point2d, topRight: Point2d) : this(bottomLeft.x..topRight.x, bottomLeft.y..topRight.y)

    /** Alternate constructor that converts from integer ranges, for convenience. */
    constructor(xRange: IntRange, yRange: IntRange) : this(
        xRange = xRange.first.toLong()..xRange.last.toLong(),
        yRange = yRange.first.toLong()..yRange.last.toLong(),
    )

    /** Alternate constructor that determines the bounding box of a number of [points]. */
    constructor(points: Iterable<Point2d>) : this(
        xRange = points.minOf { it.x }..points.maxOf { it.x },
        yRange = points.minOf { it.y }..points.maxOf { it.y },
    )

    val width: Long get() = xRange.last - xRange.first + 1
    val height: Long get() = yRange.last - yRange.first + 1

    operator fun contains(point: Point2d) = point.x in xRange && point.y in yRange

    override fun iterator(): Iterator<Point2d> = iterator {
        for (x in xRange) {
            for (y in yRange) {
                yield(Point2d(x, y))
            }
        }
    }
}

/** Returns a new region, removing all points that are outside the bounds of the [other] region. */
fun Area.coerceIn(other: Area) = Area(
    xRange = maxOf(xRange.first, other.xRange.first)..minOf(xRange.last, other.xRange.last),
    yRange = maxOf(yRange.first, other.yRange.first)..minOf(yRange.last, other.yRange.last),
)