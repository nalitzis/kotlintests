import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    //ex01()
    //ex02()
    ex03()
}

private fun ex02() = runBlocking {
    val time = measureTimeMillis {
        coroutineScope {
            println("The answer is ${concurrentSum()}")
        }
    }
    println("Completed in $time ms")
}

private suspend fun concurrentSum() = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

private fun ex03() = runBlocking {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("caught aritmetic exception")
    }
}

private suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally { println("first operation done") }
    }
    val two = async<Int> {
        println("will throw an exception for second operation")
        throw ArithmeticException()
    }
    one.await() + two.await()
}


private fun ex01() = runBlocking {
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 29
}
