import kotlin.random.Random

// ONLY 2 MODIFICATIONS YOU SHOULD MAKE TO THIS PROGRAM
// THE "val program", where you write ur program (its in the middle of the file somewhere)
// The "val tests" which is how many randomized tests to run (its in the bottom of the file somewhere)

// The test cases are 2 randomly generated 16 bit numbers

var bigCycles = 0

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
        return Random.nextInt(0, 65536)
    }

    val program: List<() -> Unit> = listOf(
        // WRITE PROGRAM HERE
        
        // EXAMPLE PROGRAM
        // add(0, 1, 2), <- IMPORTANT COMMA SINCE ITS A LIST
        // mul(3, 4, 1)
        // ETC...
        
        // STORE QUOTIENT IN REGISTER 2 AS IN SPEC
        // STORE REMAINDER IN REGISTER 3 AS IN SPEC
    )

    val dividend = randomU16()
    val divisor = randomU16()

    r[0] = dividend
    r[1] = divisor

    while (pc in program.indices) {
        program[pc]() 

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
    
    bigCycles += cycles
}

fun main() {
    val tests = 500

    repeat (tests) {
        test()
    }
    println("Average cycles: ${bigCycles/tests}")
}
