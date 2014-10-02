/**
 * 3. Написать регулярное выражение, определяющее является ли заданная строка правильным MAC-адресом.
 *    Пример правильных выражений: aE:dC:cA:56:76:54.
 *    Пример неправильных выражений: 01:23:45:67:89:Az.
 * @author yaroslav.yermilov
 */
String.metaClass.isMac = {
    return delegate.toUpperCase() ==~ /^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$/
}
def console = new BufferedReader(new InputStreamReader(System.in))

def testString = console.readLine()

println testString.isMac()