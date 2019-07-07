import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


fun mm2() = {

}

fun main() = runBlocking {
    //ch01()
    //ch02()
    //ch03()
    //ch04()
    //ch05()
    ch06()
}

@ObsoleteCoroutinesApi
private suspend fun CoroutineScope.ch06() {
    val tickerChannel = ticker(100, 0)
    var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Initial element is available immediately: $nextElement")
    nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements has 100ms delay
    println("Next element is not ready in 50 ms: $nextElement")

    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 100 ms: $nextElement")

    // Emulate large consumption delays
    println("Consumer pauses for 150ms")
    delay(150)
    // Next element is available immediately
    nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    println("Next element is available immediately after large consumer delay: $nextElement")
    // Note that the pause between `receive` calls is taken into account and next element arrives faster
    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

    tickerChannel.cancel() // indicate that no more elements are needed
}

private suspend fun CoroutineScope.ch05() {
    var cur = numbersFrom(2)
    for (i in 1..10) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren()
}

@ExperimentalCoroutinesApi
fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) {
        println("send: $x")
        send(x++)
    } // infinite stream of integers from start
}

@ExperimentalCoroutinesApi
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) {
        println("num: $x, prime: $prime")
        if (x % prime != 0) send(x)
    }
}

@ExperimentalCoroutinesApi
private suspend fun CoroutineScope.ch04() {
    val rcvChannelNums = produceNumbers()
    val rcvChannelSquares = produceSquares2(rcvChannelNums)
    for (i in 1..5) println(rcvChannelSquares.receive())
    coroutineContext.cancelChildren()
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceSquares2(rcv: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in rcv) send(x * x)
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

private suspend fun CoroutineScope.ch03() {
    val channel = produceSquares()
    channel.consumeEach { println(it) }
    println("done")
}

private suspend fun CoroutineScope.ch01() {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) channel.send(x * x)
    }
// here we print five received integers:
    repeat(5) { println(channel.receive()) }
    println("Done!")
}

@ExperimentalCoroutinesApi
private fun CoroutineScope.produceSquares() = produce {
    for (x in 1..5) send(x * x)
}

private suspend fun CoroutineScope.ch02() {
    val channel = Channel<Int>()
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.close() // we're done sending
    }
// here we print received values using `for` loop (until the channel is closed)
    for (y in channel) println(y)
    println("Done!")
}