import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*
import java.util.regex.Pattern


class ApiTracker {

    companion object {
        var responseTimeThreshold = 1000
        val regex = Pattern.compile("<-- \\d{3}  (https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|] \\((\\d+)ms\\)")
        var destnationDir: String? = "./"
        var outputDirPath: String = "$destnationDir/warningDocs"
        var shouldLog = false
        var logInOneFile = false

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isNotEmpty()) {
                configure(args)
            }
            val dirFile = File(destnationDir)
            val outputDir = File(outputDirPath)
            if (!outputDir.exists()) outputDir.mkdir()

            var fileOutputStream: FileOutputStream? = null
            dirFile.listFiles({ file -> !file.isDirectory && file.extension == "txt" }).forEach { logcatTxt ->
                val fileName = logcatTxt.name
                if (shouldLog) System.out.println("File: $fileName analyst:")
                val warnings = LinkedList<String>()
                logcatTxt.readLines().forEach { s ->
                    //Example <-- 200  https://alpha.bc3ts.com/sticker/owned_set_list (438ms)
                    val matcher = regex.matcher(s)
                    if (matcher.find()) {
                        val responseTimeInMs = matcher.group(2)
                        if (responseTimeInMs.toLong() > responseTimeThreshold) {
                            val result = s.replace(regex = Pattern.compile(".+/OkHttp\\(\\d+\\): <--").toRegex(), replacement = "")
                            if (shouldLog) System.out.println(result)
                            warnings.add(result)
                        }
                    }
                }
                val outputFile =
                        if (logInOneFile) File("$outputDirPath/warnings.txt")
                        else File("$outputDirPath/$fileName")
                fileOutputStream = FileOutputStream(outputFile, true)
                OutputStreamWriter(fileOutputStream).run {
                    warnings.forEach {
                        write("$it\n")
                    }
                    flush()
                    close()
                    warnings.clear()
                }
            }
        }


        private fun configure(args: Array<String>) {
            args.forEachIndexed { index, raws ->
                val s = raws.toLowerCase()
                if (index == 0 && !s.startsWith("-")) {
                    destnationDir = s
                }
                if (s.startsWith("-")) {
                    if (s == "-t") {
                        try {
                            responseTimeThreshold = args[index + 1].toInt()
                        } catch (e: Exception) {
                            System.out.print("wrong argument: -t " + args[index + 1])
                        }
                    }
                    if (s == "-output") {
                        outputDirPath = args[index + 1]
                    }
                    if (s == "-verbose") {
                        shouldLog = true
                    }
                    if (s == "-h" || s == "-help") {
                        System.out.println("Welcome to Simple OkHttp Response Time Analyst\n")
                        System.out.println("usage: sorta [logcat folder] [-t response time threshold] [-output output folder] [-verbose]")
                    }
                    if (s == "-onefile") {
                        logInOneFile = true
                    }
                }
            }
        }
    }
}


