package org.sayandev.stickynote.core.database.mysql

import com.google.common.util.concurrent.ThreadFactoryBuilder
import org.sayandev.stickynote.core.database.Query
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ThreadFactory

class MySQLDatabase(credentials: MySQLCredentials, poolingSize: Int, verifyCertificate: Boolean, val driverClass: String?, keepaliveTime: Long?, connectionTimeout: Long?, minimumIdle: Int?, maxLifeTime: Long?, allowPublicKeyRetrieval: Boolean) : MySQLExecutor(credentials, poolingSize, THREAD_FACTORY, verifyCertificate, keepaliveTime, connectionTimeout, minimumIdle, maxLifeTime, allowPublicKeyRetrieval) {
    constructor(credentials: MySQLCredentials, poolingSize: Int, verifyCertificate: Boolean, driverClass: String?, keepaliveTime: Long?, connectionTimeout: Long?, minimumIdle: Int?) : this(credentials, poolingSize, verifyCertificate, driverClass, keepaliveTime, connectionTimeout, minimumIdle, null, false)
    constructor(credentials: MySQLCredentials, poolingSize: Int, verifyCertificate: Boolean, driverClass: String?, keepaliveTime: Long?, connectionTimeout: Long?) : this(credentials, poolingSize, verifyCertificate, driverClass, keepaliveTime, connectionTimeout, null, null, false)
    constructor(credentials: MySQLCredentials, poolingSize: Int, verifyCertificate: Boolean): this(credentials, poolingSize, verifyCertificate, "com.mysql.cj.jdbc.Driver", null, null)
    constructor(credentials: MySQLCredentials, poolingSize: Int, driverClass: String): this(credentials, poolingSize, true, driverClass, null, null)
    constructor(credentials: MySQLCredentials, poolingSize: Int): this(credentials, poolingSize, true, "com.mysql.cj.jdbc.Driver", null, null)
    constructor(credentials: MySQLCredentials, poolingSize: Int,  keepaliveTime: Long?, connectionTimeout: Long?): this(credentials, poolingSize, true, "com.mysql.cj.jdbc.Driver", keepaliveTime, connectionTimeout)

    override fun connect() {
        super.connect(driverClass)
        startQueue()
    }

    override fun scheduleShutdown(): CompletableFuture<Void> {
        val future = CompletableFuture<Void>()
        Thread {
            while (!isQueueEmpty) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            shutdown()
            future.complete(null)
        }.start()
        return future
    }

    override fun shutdown() {
        queue.clear()
        hikari.close()
    }

    private fun startQueue() {
        Thread {
            while (!isQueueEmpty) {
                if (poolingUsed <= poolingSize) {
                    tick()
                    try {
                        Thread.sleep(1)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

    override fun onQueryFail(query: Query) {
        //ignored
    }

    override fun onQueryRemoveDueToFail(query: Query) {
        //ignored
    }

    companion object {
        private val THREAD_FACTORY: ThreadFactory = ThreadFactoryBuilder().setNameFormat("mysql-thread-%d").build()
    }
}
