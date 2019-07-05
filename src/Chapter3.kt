//extension function
fun String.lastChar(): Char = this[this.length - 1]

//extension property
val String.lastChar : Char
    get() = get(length - 1)

//ext. property (with both getter/setter)
var StringBuilder.lastChar : Char
    get() = get(length - 1)
    set(value) {
     this.setCharAt(length - 1, value)
    }

//vararg
fun <T> lastOf(vararg values : T) : T = values[values.size - 1]

//destructuring declaration
fun testDestructuring() {
 val (number, one) = 1 to "one"
 println("[$number,$one]")

 val l = listOf(1, 2, 3, 4, 5)

 for ((i, el) in l.withIndex()) {
  println("l[$i] = $el")
 }
}

fun splitPath(path : String) : Path {
 val dir = path.substringBeforeLast("/")
 val name = path.substringAfterLast("/").substringBeforeLast(".")
 val ext = path.substringAfterLast(".")
 return Path(dir, name, ext)
}

data class Path(val dir: String, val filename: String, val extension: String)

// local functions
fun extFunction(s: String): String {

 fun innerFunction(s2: String): String = s2.substring(1)

 return innerFunction(s)
}


fun main(args: Array<String>) {
 val m = hashMapOf(1 to "one", 2 to "two", 3 to "three")
 val m2 = m[2]
 println(m2)
 val str = "hello"
 println(str.lastChar())
 println(str.lastChar)
 val strBuilder = StringBuilder()
 strBuilder.append("hello world")
 strBuilder.lastChar = 'f'
 println(strBuilder)
 println(lastOf("a", "b", "c"))
 val l = arrayOf("a", "b", "c", "d")
 //'*' is spread operator: needed when "vararg" argument is required and array is available as input
 println(lastOf(*l))
 testDestructuring()

 val path = "users/bulfoni/dox.txt"
 println(splitPath(path))

 val aString = "Very good"
 println(extFunction(aString))
}