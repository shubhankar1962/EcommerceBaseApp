package com.example.ecommerceapp.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductModelEntity::class], version = 1)
abstract class ProductDatabase:RoomDatabase() {

    abstract fun productDao():ProductDao

    companion object{
        private var dbinstance : ProductDatabase? = null
        private val DATABASE_NAME = "ecommerceDb"

        @Synchronized
        fun getDbInstance(context: Context):ProductDatabase{

            if(dbinstance == null){
                dbinstance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return dbinstance!!
        }

    }
}