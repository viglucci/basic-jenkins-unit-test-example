
def call(Map params = [:]) {
    echo params.text
}

/* Convenience overload */
def call(String text) {
    return call(text: text)
}
