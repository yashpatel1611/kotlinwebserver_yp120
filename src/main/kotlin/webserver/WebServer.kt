package webserver

fun main() {
    var urls = listOf("http://www.imperial.ac.uk/?q=xxx", "http://www.imperial.ac.uk/?q=xxx&rr=zzz", "http://www.imperial.ac.uk/")
    for (i in urls) {
        println(queryParams(i))
    }
}

// write your web framework code here:

fun scheme(url: String): String = url.substringBefore(':')

fun host(url: String): String = url.split('/')[2]

fun path(url: String): String = url.substringAfter(host(url)).substringBefore("?")

fun queryParams(url: String): List<Pair<String, String>> {

    val params = url.substringAfter("?").split('&')
    var pairList = mutableListOf<Pair<String, String>>()

    if (params[0] != url) {
        for (param in params) {
            val keyValues = param.split('=')
            pairList.add(Pair(keyValues[0], keyValues[1]))
        }
    }
    return pairList
}

fun helloHandler(request: Request): Response {
    var responseBody: String = "Hello, World!"
    val params = queryParams(request.url)
    for ((paramKey, paramValue) in params) {
        if (paramKey == "name") {
            responseBody = "Hello, $paramValue!"

        }
        if (paramKey == "style") {
            if (paramValue == "shouting") {
                responseBody = responseBody.toUpperCase()
            }
        }

    }
    return Response(Status.OK, responseBody)
}

fun rootHandler(request: Request): Response {
    return Response(Status.OK, "This is Imperial")
}

fun compHandler(request: Request): Response{
    return Response(Status.OK, "This is DoC")
}

var mappings = listOf(Pair("/say-hello", ::helloHandler), Pair("/", ::rootHandler), Pair("/computing", ::compHandler))
fun route(request: Request): Response {
    var pagePath = path(request.url)
    for((page, funct) in mappings){
        if(page == pagePath){
            funct
        }
    }
    return Response(Status.NOT_FOUND, "")
}

// http handlers for a particular website...

fun homePageHandler(request: Request): Response = Response(Status.OK, "This is Imperial.")
