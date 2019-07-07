import kotlinx.coroutines.*

fun main() {
    ex01()
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

private fun ex01() = runBlocking {
    val a = async (Dispatchers.Default + CoroutineName("A")){
        log("i am computing the answer for a")
        log("my job is ${coroutineContext[Job]}")
        6
    }
    val b = async (CoroutineName("B")){
        log("i am computing the answer for b")
        log("my job is ${coroutineContext[Job]}")
        7
    }
    log("the answer is ${a.await() * b.await()}")
    log("my job is ${coroutineContext[Job]}")
}