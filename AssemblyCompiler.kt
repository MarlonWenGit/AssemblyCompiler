import kotlin.random.Random

fun test() {
    val r = MutableList(16) { 0 }

    var cycles = 0

    var pc = 0

    fun add(d: Int, a: Int, b: Int): () -> Unit = {
        r[d] = r[a] + r[b]
        cycles++
        pc++
    }

    fun sub(d: Int, a: Int, b: Int): () -> Unit = {
        r[d] = r[a] - r[b]
        cycles++
        pc++
    }

    fun mul(d: Int, a: Int, b: Int): () -> Unit = {
        r[d] = r[a] * r[b]
        cycles+=3
        pc++
    }

    fun shl(d: Int, a: Int): () -> Unit = {
        r[d] = r[d] shl r[a]
        cycles++
        pc++
    }

    fun shr(d: Int, a: Int): () -> Unit = {
        r[d] = r[d] shr r[a]
        cycles++
        pc++
    }

    fun bor(d: Int, a: Int, b: Int): () -> Unit = {
        r[d] = r[a] or r[b]
        cycles++
        pc++
    }

    fun band(d: Int, a: Int, b: Int): () -> Unit = {
        r[d] = r[a] and r[b]
        cycles++
        pc++
    }

    fun li(d: Int, i: Int): () -> Unit = {
        r[d] = i
        cycles++
        pc++
    }

    fun jz(a: Int, offset: Int): () -> Unit = {
        if (r[a] == 0) pc += offset else pc++
        cycles+=3
    }
    fun jp(a: Int, offset: Int): () -> Unit = {
        if (r[a] > 0) pc += offset else pc++
        cycles+=3
    }

    fun randomU16(): Int {
        // 65536 is the exclusive upper bound, so this returns 0..65535
        return Random.nextInt(0, 65536)
    }

    val program: List<() -> Unit> = listOf(
        // -- write assembly here
    )

    val dividend = randomU16()
    val divisor = randomU16()

    r[0] = dividend
    r[1] = divisor

    while (pc in program.indices) {
        program[pc]() // Execute the lambda at the current program counter

        if (cycles > 100000) {
            println("⚠️ Infinite Loop Detected! PC: $pc")
            break
        }
    }

    println("Quotient = ${r[2]}")
    println("Actual Quotient = ${dividend / divisor}")
    println("Remainder = ${r[3]}")
    println("Actual Remainder = ${dividend % divisor}")
    println("Cycles = ${cycles}")
    println()
}

fun main() {
    val tests = 10

    repeat (tests) {
        test()
    }
}
