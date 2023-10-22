package dev.rcode.android.core.base.error

class NoDataException(override val message: String = "No data available in datastore"): Throwable()

class DataStoreException(override val message: String = "Nothing"): Throwable()

class DataBaseException(override val message: String = "Some error occurred in database"): Throwable()