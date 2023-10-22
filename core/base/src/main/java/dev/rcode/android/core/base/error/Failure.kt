package dev.rcode.android.core.base.error

sealed class Failure: Throwable() {

    /**
     * External - Network Request Failure due to, 500, 400, 403 etc.
     */
    class NetworkRequestFailure(m: String): Failure()

    /**
     *  Internal - Error in the code
     *  Can be implemented for specific failures. like database, cache etc.
     */
    abstract class FeatureFailure(): Failure()
}
