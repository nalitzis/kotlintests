import java.lang.IllegalArgumentException

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

data class Person(val name: String, val age: Int) {
    //custom accessor
    val isOld : Boolean
        get() {
            return age > 59
        }
}

enum class Shapes(private val sides: Int) {
    SQUARE(4), CIRCLE(0), RECTANGLE(4), ELLIPSE(0), TRIANGLE(3);

    fun hasSides(): Boolean = sides > 0
}

interface Expr
class Num(val n: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
fun eval(expr: Expr) : Int =
    when(expr) {
        is Num -> expr.n
        is Sum -> eval(expr.left) + eval(expr.right)
        else -> throw IllegalArgumentException("wrong type")
    }
fun printExpr(expr: Expr) : String =
        when(expr) {
            is Num -> expr.n.toString()
            is Sum -> "(${printExpr(expr.left)} + ${printExpr(expr.right)})"
            else -> throw IllegalArgumentException("wrong type")
        }

fun main(args: Array<String>) {
    println("hello, world")
    val a = 2
    val b = 3
    println("max of $a, $b is ${max(a,b)}")
    val p = Person("John McAfee", 73)
    println("${p.name} is ${p.age} years old!, is old? ${p.isOld}")
    val circle = Shapes.CIRCLE
    val square = Shapes.SQUARE
    println("$circle has sides? ${circle.hasSides()}")
    println("$square has sides? ${square.hasSides()}")

    val expr = Sum(Num(3), Sum(Num(9), Num(4)))
    println("expr is ${eval(expr)}")
    println(printExpr(expr))
}