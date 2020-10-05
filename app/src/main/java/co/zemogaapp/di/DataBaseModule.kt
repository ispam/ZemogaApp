package co.zemogaapp.di

import android.app.Application
import androidx.room.Room
import co.zemogaapp.persistency.ZemogaDB
import co.zemogaapp.persistency.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [AppModule::class])
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    private const val DB_NAME = "ZemogaDB.db"

    @Provides
    fun provideDataBase(application: Application): ZemogaDB {
        return Room.databaseBuilder(
            application,
            ZemogaDB::class.java,
            DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePostDao(db: ZemogaDB): PostDao = db.postDao()

}