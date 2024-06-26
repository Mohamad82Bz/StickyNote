package org.sayandev.stickynote.core.database.mysql

class MySQLCredentials private constructor(val url: String, val username: String, val password: String) {
    companion object {
        @JvmStatic
        fun mySQLCredentials(
            address: String,
            port: Int,
            database: String,
            useSSL: Boolean,
            username: String,
            password: String
        ): MySQLCredentials {
            return MySQLCredentials(
                String.format("jdbc:mysql://%s:%s/%s?useSSL=%s", address, port, database, useSSL),
                username,
                password
            )
        }

        @JvmStatic
        fun mySQLCredentials(url: String, username: String, password: String): MySQLCredentials {
            return MySQLCredentials(url, username, password)
        }
    }
}
