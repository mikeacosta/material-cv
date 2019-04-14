package net.acosta.mike.resume.data.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migration1to2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE content ADD COLUMN playStore TEXT")
    }
}

val migration2to3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE content ADD COLUMN lastUpdate TEXT")
        database.execSQL("ALTER TABLE profile ADD COLUMN lastUpdate TEXT")
        database.execSQL("ALTER TABLE job ADD COLUMN lastUpdate TEXT")
    }
}

val migration3to4: Migration = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE content ADD COLUMN msCertMain TEXT")
        database.execSQL("ALTER TABLE content ADD COLUMN msCertAction TEXT")
        database.execSQL("ALTER TABLE content ADD COLUMN msCert TEXT")
    }
}

val migration4to5: Migration = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DELETE FROM content")
        database.execSQL("ALTER TABLE content ADD COLUMN api TEXT")
    }
}