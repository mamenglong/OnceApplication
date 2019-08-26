package com.mml.onceapplication.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-23 上午11:12
 * Description: This is TestDatabase
 * Package: com.example.test.db
 * Project: Test
 */
@Database(entities = [User::class, WorkInfo::class,Wife::class], version = 4, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workInfoDao(): WorkInfoDao
    abstract fun wifeDao(): WifeDao

    companion object {
        val lock = Any()
        private var INSTANCE: TestDatabase? = null
        fun getInstance(context: Context): TestDatabase = INSTANCE ?: synchronized(lock) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                TestDatabase::class.java, "testDatabase"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    //第一次创建数据库时调用，但是在创建所有表之后调用的
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.i("TestDatabase", "onCreate")
                    }

                    //当数据库被打开时调用
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.i("TestDatabase", "onOpen")
                    }
                })
                .allowMainThreadQueries()//允许在主线程查询数据
                .addMigrations()//迁移数据库使用，下面会单独拿出来讲
                .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                .build().also { INSTANCE = it }
        }
        val CHEESE_DATA = arrayListOf(
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
            "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
            "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
            "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
            "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
            "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
            "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
            "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
            "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
            "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
            "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)"
        )

    }


    internal val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE CustomWallpaperConfig " + " ADD COLUMN introductionImg TEXT")

        }
    }

}