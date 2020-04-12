package com.menlhk.akps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.menlhk.akps.database.kabupaten.KabupatenDB
import com.menlhk.akps.database.kabupaten.KabupatenDao
import com.menlhk.akps.database.kawasan.KawasanDB
import com.menlhk.akps.database.kawasan.KawasanDao
import com.menlhk.akps.database.provinsi.ProvinsiDB
import com.menlhk.akps.database.provinsi.ProvinsiDao
import com.menlhk.akps.database.skema.SkemaDB
import com.menlhk.akps.database.skema.SkemaDao
import com.menlhk.akps.database.usulan.UsulanDB
import com.menlhk.akps.database.usulan.UsulanDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created By naufa on 10/09/2019
 */

@Database(
    version = 3, exportSchema = false,
    entities = [
        KabupatenDB::class,
        KawasanDB::class,
        ProvinsiDB::class,
        SkemaDB::class,
        UsulanDB::class
    ]
)
abstract class AkpsRoomDatabase : RoomDatabase() {

    abstract fun kabupatenDao() : KabupatenDao
    abstract fun kawasanDao() : KawasanDao
    abstract fun provinsiDao() : ProvinsiDao
    abstract fun skemaDao() : SkemaDao
    abstract fun usulanDao() : UsulanDao

    companion object {
        @Volatile
        internal var INSTANCE: AkpsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AkpsRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AkpsRoomDatabase::class.java,
                    "akps_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AkpsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }


        private class AkpsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                    }
                }
            }
        }

    }
}