// ONLY 4 MODIFICATIONS YOU SHOULD MAKE TO THIS PROGRAM
// THE "val program", where you write ur program (its in the middle of the file somewhere)
// And these ones
var detailedTests = true // Set true for detailed individual tests, and false if you are running a ton of tests
var ultraDetailedTests = true // Tracer. Prints values every line. Probably only run one test
val numberTests = 1 // Max is about 10 million if your cycle count is low.

// The test cases are 2 randomly generated 16 bit numbers

var bigCycles = 0
var success = true
var bestCase = 1999999
var worstCase = 0
var fails = 0

fun test() {
    val r = MutableList(16) { -9999 }
    var cycles = 0
    var pc = 0

    fun add(d: Int, a: Int, b: Int): () -> Unit = {
            if (ultraDetailedTests) {
                println(" ${pc+1}: r[$d] = r[$a] + r[$b] = ${r[a]} + ${r[b]} = ${r[a] + r[b]}")
            }
            r[d] = r[a] + r[b]
            cycles++
            pc++
        }

    fun sub(d: Int, a: Int, b: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$a] - r[$b] = ${r[a]} - ${r[b]} = ${r[a] - r[b]}")
        }
        r[d] = r[a] - r[b]
        cycles++
        pc++
    }

    fun mul(d: Int, a: Int, b: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$a] * r[$b] = ${r[a]} * ${r[b]} = ${r[a] * r[b]}")
        }
        r[d] = r[a] * r[b]
        cycles+=3
        pc++
    }

    fun shl(d: Int, a: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$d] shl r[$a] = ${r[d]} shl ${r[a]} = ${r[d] shl r[a]}")
        }
        r[d] = r[d] shl r[a]
        cycles++
        pc++
    }

    fun shr(d: Int, a: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$d] shr r[$a] = ${r[d]} shr ${r[a]} = ${r[d] shr r[a]}")
        }
        r[d] = r[d] shr r[a]
        cycles++
        pc++
    }

    fun bor(d: Int, a: Int, b: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$a] or r[$b] = ${r[a]} or ${r[b]} = ${r[a] or r[b]}")
        }
        r[d] = r[a] or r[b]
        cycles++
        pc++
    }

    fun band(d: Int, a: Int, b: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = r[$a] and r[$b] = ${r[a]} and ${r[b]} = ${r[a] and r[b]}")
        }
        r[d] = r[a] and r[b]
        cycles++
        pc++
    }

    fun li(d: Int, i: Int): () -> Unit = {
        if (ultraDetailedTests) {
            println(" ${pc+1}: r[$d] = $i")
        }
        r[d] = i
        cycles++
        pc++
    }

    fun jz(a: Int, offset: Int): () -> Unit = {
        if (ultraDetailedTests) {
            val willJump = r[a] == 0
            val nextLine = if (willJump) pc + offset + 1 else pc + 2
            println(" ${pc+1}: jz r[$a], $offset -> r[$a] == 0 is $willJump, next line will be $nextLine")
        }
        if (r[a] == 0) pc += offset else pc++
        cycles+=3
    }
    
    fun jp(a: Int, offset: Int): () -> Unit = {
        if (ultraDetailedTests) {
            val willJump = r[a] > 0
            val nextLine = if (willJump) pc + offset + 1 else pc + 2
            println(" ${pc+1}: jp r[$a], $offset -> r[$a] > 0 is $willJump, next line will be $nextLine")
        }
        if (r[a] > 0) pc += offset else pc++
        cycles+=3
    }

    fun randomU16(): Int {
        return Random.nextInt(0, 65536)
    }

    val program: List<() -> Unit> = listOf(
        // WRITE PROGRAM HERE

        // Example program, in function form with COMMAS at end     
        //li(15, 0), 
        //li(2, 0), 
        //sub(14, 1, 0), 
        //jp(14, 16), 
        
        // STORE QUOTIENT IN REGISTER 2 AS IN SPEC
        // STORE REMAINDER IN REGISTER 3 AS IN SPEC
    )

    val dividend = randomU16()
    var divisor = randomU16()
    while (divisor == 0) {
        divisor = randomU16()
    }

    r[0] = 60000
    r[1] = 14000

    while (pc in program.indices) {
        program[pc]() 

        if (cycles > 1000) {
            println("⚠️ Infinite Loop Detected! PC: $pc, dividend: $dividend, divisor: $divisor")
            break
        }
    }
    
    bigCycles += cycles
    worstCase = maxOf(worstCase, cycles)
    bestCase = minOf(bestCase, cycles)
    
    if (detailedTests) {
        println("Quotient = ${r[2]}")
        println("Actual Quotient = ${dividend / divisor}")
        println("Remainder = ${r[3]}")
        println("Actual Remainder = ${dividend % divisor}")
        println("Cycles = ${cycles}")
        println()
    }
	
    if (r[2] != (dividend / divisor) || r[3] != (dividend % divisor)) {
        success = false
        fails += 1
        println("Failed for: dividend = $dividend, divisor = $divisor")
    }
}

fun main() {
    val tests = numberTests

    repeat (tests) {
        test()
    }
    println("Average cycles: ${bigCycles/tests}")
    println("All $tests tests passed = $success")
    println("Worst case = $worstCase")
    println("Best case = $bestCase")
    println("Fails = $fails")
}
