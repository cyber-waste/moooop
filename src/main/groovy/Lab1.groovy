/**
 * 3. Ввести n строк с консоли. Вывести на консоль те строки, длина которых меньше средней, также их длины.
 * @author yaroslav.yermilov
 */
BufferedReader.metaClass.readInt = {
    Integer.parseInt(delegate.readLine())
}
def console = new BufferedReader(new InputStreamReader(System.in))

def numberOfLines = console.readInt()

def lines = (1..numberOfLines).collect {
    sysin.readLine()
}

def averageLength = lines.collect { line -> line.length() } .sum() / lines.size()

def linesShorterThanAverage = lines.grep { line -> line.length() < averageLength }.collect { line -> "'${line}' of length ${line.size()}"}

println linesShorterThanAverage