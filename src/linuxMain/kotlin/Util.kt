import kotlinx.coroutines.runBlocking

actual fun keepAlive() = runBlocking {
    exporter.await()
}
