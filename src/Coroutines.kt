import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("${System.currentTimeMillis()} Task from runBlocking")
    }

    coroutineScope { // Creates a coroutine scope
        launch {
            println("hello, ")
            delay(500L)
            println("${System.currentTimeMillis()} Task from nested launch")
        }

        launch {
            printHello()
        }

        delay(100L)
        println("${System.currentTimeMillis()} Task from coroutine scope") // This line will be printed before the nested launch
    }

    println("${System.currentTimeMillis()} Coroutine scope is over") // This line is not printed until the nested launch completes
}

suspend fun printHello() {
    delay(1000)
    println("world!")
}
