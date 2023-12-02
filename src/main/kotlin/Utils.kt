import java.io.File

object Utils

fun readFile(name: String): File {
    return File(Utils::class.java.getResource(name)!!.file)
}