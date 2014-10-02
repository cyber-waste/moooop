/**
 * 3. Сложить два многочлена заданной степени, если коэффициенты многочленов хранятся в объекте HashMap.
 * @author yaroslav.yermilov
 */
BufferedReader.metaClass.readPolynom = {
    String polynomAsString = delegate.readLine()
    def polynomAsHashMap = [:]
    polynomAsString.split('\\+').each {
        def k = Integer.parseInt(it.split('\\*x\\^')[0])
        def n = Integer.parseInt(it.split('\\*x\\^')[1])

        polynomAsHashMap[n] = k
    }
    polynomAsHashMap
}
def console = new BufferedReader(new InputStreamReader(System.in))

def p1 = console.readPolynom()
def p2 = console.readPolynom()

def p3 = [:] as HashMap
p3.putAll(p1)
p2.each {
    if (p3.containsKey(it.key)) {
        p3[it.key] = p3[it.key] + it.value
    } else {
        p3[it.key] = it.value
    }
}

def p3AsString = p3.collect { "${it.value}*x^${it.key}" }.join('+')

println p3AsString
