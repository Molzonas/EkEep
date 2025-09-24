# EkEep
*README todo*

## Project initialization
* Clone this project
* Open it using an IDE (IntelliJ IDEA recommended)
* Reload the full maven project
* Create the `local.properties` file at root (in the same folder as this README)
* Do Maven lifecycle operations from root (`ekeep`), and not individual parts (`ekeep-api` or `ekeep-core`)

## `local.properties` example file

```properties
# Database driver for jOOQ
# Supported values:
# org.mariadb.jdbc.Driver
# com.mysql.jdbc.Driver
# org.postgresql.Driver
# org.sqlite.JDBC
local.database.driver=[driver]

# Database URL for jOOQ
local.database.url=jdbc:[dialect]://[ip]:[port]/[database]?useSSL=false&serverTimezone=UTC

# Database username for jOOQ
local.database.user=[username]

# Database password for jOOQ
local.database.password=[password]

# Database dialect for jOOQ
#   Natively supported values are:
# org.jooq.meta.ase.ASEDatabase
# org.jooq.meta.auroramysql.AuroraMySQLDatabase
# org.jooq.meta.aurorapostgres.AuroraPostgresDatabase
# org.jooq.meta.clickhouse.ClickHouseDatabase
# org.jooq.meta.cockroachdb.CockroachDBDatabase
# org.jooq.meta.databricks.DatabricksDatabase
# org.jooq.meta.db2.DB2Database
# org.jooq.meta.derby.DerbyDatabase
# org.jooq.meta.firebird.FirebirdDatabase
# org.jooq.meta.h2.H2Database
# org.jooq.meta.hana.HANADatabase
# org.jooq.meta.hsqldb.HSQLDBDatabase
# org.jooq.meta.ignite.IgniteDatabase
# org.jooq.meta.informix.InformixDatabase
# org.jooq.meta.ingres.IngresDatabase
# org.jooq.meta.mariadb.MariaDBDatabase
# org.jooq.meta.mysql.MySQLDatabase
# org.jooq.meta.oracle.OracleDatabase
# org.jooq.meta.postgres.PostgresDatabase
# org.jooq.meta.redshift.RedshiftDatabase
# org.jooq.meta.snowflake.SnowflakeDatabase
# org.jooq.meta.sqldatawarehouse.SQLDataWarehouseDatabase
# org.jooq.meta.sqlite.SQLiteDatabase
# org.jooq.meta.sqlserver.SQLServerDatabase
# org.jooq.meta.sybase.SybaseDatabase
# org.jooq.meta.teradata.TeradataDatabase
# org.jooq.meta.trino.TrinoDatabase
# org.jooq.meta.vertica.VerticaDatabase
local.database.dialect=[dialect]

# Database schema (database name) for jOOQ
local.database.schema=[database]
```