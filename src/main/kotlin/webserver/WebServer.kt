package webserver

// write your web framework code here:

fun scheme(url: String): String = TODO()

fun host(url: String): String = TODO()

fun path(url: String): String = TODO()

fun queryParams(url: String): List<Pair<String, String>> = TODO()

// http handlers for a particular website...

fun homePageHandler(request: Request): Response = Response(Status.OK, "This is Imperial.")
