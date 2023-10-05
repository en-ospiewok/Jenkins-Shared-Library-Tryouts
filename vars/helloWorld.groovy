void call() {
    echo 'Hello World'
}

void call(Map args) {
    echo 'Hello World with arguments'
    for (arg in args) {
        println "${arg.key} : ${arg.value}"
    }
}
