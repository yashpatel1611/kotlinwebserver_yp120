package webserver

// write your web framework code here:

// Uses the string function 'substringBefore' to get everything before
// the first ':' character
fun scheme(url: String): String = url.substringBefore(':')

// Uses the spring split function to separate all the parts between '/'s
// The second index of the set of separations is the host url
fun host(url: String): String = url.split('/')[2]

// Uses the substringAfter function on the host url to get the substring before the parameters
fun path(url: String): String = url.substringAfter(host(url)).substringBefore("?")

// Function to return the list of pair parameters in a url
// The function first gets all the parameters and splits them into each pair
// The pairs are looped through using a for loop and are added to the mutable list
fun queryParams(url: String): List<Pair<String, String>> {

    val params = url.substringAfter("?").split('&')
    val pairList = mutableListOf<Pair<String, String>>()

    if (params[0] != url) {
        for (param in params) {
            val keyValues = param.split('=')
            pairList.add(Pair(keyValues[0], keyValues[1]))
        }
    }
    return pairList
}

// List of pair values with the path and its corresponding handler
var mappings = listOf(
    Pair("/say-hello", ::helloHandler), Pair("/", ::homePageHandler),
    Pair("/computing", ::compPageHandler)
)

fun route(request: Request): Response {
    val pagePath = path(request.url)
    for ((page, func) in mappings) {
        if (page == pagePath) {
            return func(request)
        }
    }
    return Response(Status.NOT_FOUND, "")
}

// http handlers for a particular website...

// Handler for main homepage website
fun homePageHandler(request: Request): Response = Response(Status.OK, "This is Imperial.")

// Handler for Department of Computing website
fun compPageHandler(request: Request): Response = Response(Status.OK, "This is DoC.")

// Handler for say-hello page
// When called will return a greeting based on the url parameters
// Uses a for loop to loop through all the parameters of the url and
// adjusts the response body accordingly
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
