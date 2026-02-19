import kotlin.random.Random

fun test() {
    val r = MutableList(16) { 0 }

    var cycles = 0

    fun add(d: Int, a: Int, b: Int) {
        r[d] = r[a] + r[b]
        cycles++
    }

    fun sub(d: Int, a: Int, b: Int) {
        r[d] = r[a] - r[b]
        cycles++
    }

    fun mul(d: Int, a: Int, b: Int) {
        r[d] = r[a] * r[b]
        cycles+=3
    }

    fun shl(d: Int, a: Int) {
        r[d] = r[d] shl r[a]
        cycles++
    }

    fun shr(d: Int, a: Int) {
        r[d] = r[d] shr r[a]
        cycles++
    }

    fun bor(d: Int, a: Int, b: Int) {
        r[d] = r[a] or r[b]
        cycles++
    }

    fun band(d: Int, a: Int, b: Int) {
        r[d] = r[a] and r[b]
        cycles++
    }

    fun li(d: Int, i: Int) {
        r[d] = i
        cycles++
    }

    fun divide(dividend: Int, divisor: Int) {
        // Initialize registers
        li(0, dividend) // r[0] = Remainder / Current Dividend
        li(1, divisor)  // r[1] = Divisor
        li(2, 0)        // r[2] = Quotient
        li(5, 1)        // r[5] = Constant 1 (used for incrementing/shifting)

        while (true) {
            // Check if remainder < divisor (r[0] < r[1])
            sub(3, 0, 1)  // r[3] = r[0] - r[1]
            if (r[3] < 0) break

            li(3, r[1])   // r[3] = divisor (temp copy for shifting)
            li(4, 0)      // r[4] = shift count

            // Inner loop: Find largest shift such that (divisor << shift) <= remainder
            while (true) {
                shl(3, 5)    // r[3] = r[3] << 1 (Doubling the temp divisor)
                add(4, 4, 5) // r[4]++ (Increment shift count)

                // FIX #1: Pass indices 3 and 0, not values r[3] and r[0]
                sub(6, 3, 0)

                if (r[6] > 0) {
                    // If shifted divisor > remainder, we went too far. Backtrack.
                    shr(3, 5)    // Undo last shift
                    sub(4, 4, 5) // Undo last increment
                    break
                }
            }

            // Subtract the shifted divisor from the remainder
            sub(0, 0, 3)

            // Add 1 << shift to the quotient
            li(3, 1)
            shl(3, 4)     // r[3] = 1 << shift count
            add(2, 2, 3)  // quotient += r[3]
        }
    }

    fun randomU16(): Int {
        // 65536 is the exclusive upper bound, so this returns 0..65535
        return Random.nextInt(0, 65536)
    }

    val n1 = randomU16()
    val n2 = randomU16()

    divide(n1, n2)

    println("Quotient = ${r[2]}")
    println("Actual Quotient = ${n1 / n2}")
    println("Remainder = ${r[0]}")
    println("Actual Remainder = ${n1 % n2}")
    println("Cycles = ${cycles}")
    println()
}

fun main() {
    val tests = 10

    repeat (tests) {
        test()
    }
}
