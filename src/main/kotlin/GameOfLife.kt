import io.opentelemetry.kotlin.api.common.AttributeKey

class GameOfLife(
    private val size: Int
) {
    private val grid: Array<Array<Boolean>> = Array(size) { i -> Array(size) { j -> false } }

    fun initialize(
        action: (grid: Array<Array<Boolean>>) -> Unit
    ) = span("initialize") {
        action(grid)
    }

    fun simulate(
        steps: Int
    ) = span("simulate") {
        repeat(steps) {
            step()
        }
    }

    fun step() = span("step") { s ->
        val g: Array<Array<Boolean>> = Array(size) { i -> Array(size) { j -> false } }

        for (x in 0 until size) {
            for (y in 0 until size) {
                val alive = neighbors(x, y).alive()
                g[x][y] = when (alive) {
                    0, 1 -> false
                    2 -> grid[x][y]
                    3 -> true
                    else -> false
                }
            }
        }

        s.setAttribute(ALIVE, grid.sumOf { it.count { it } }.toString())
    }

    private fun neighbors(
        x: Int,
        y: Int
    ) = span("neighbors") {
        val array = Array(8) { false }
        var i = 0;
        for (nx in x - 1..x + 1) {
            for (ny in y - 1..y + 1) {
                if (nx == x && ny == y) continue

                array[i++] = grid[nx.mod(size)][ny.mod(size)]
            }
        }
        return@span array
    }

    private fun Array<Boolean>.alive(): Int {
        return count { it }
    }

    companion object {
        val ALIVE = AttributeKey.stringKey("alive")
    }
}
