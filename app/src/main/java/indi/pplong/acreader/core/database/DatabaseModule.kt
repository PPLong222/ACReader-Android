package indi.pplong.acreader.core.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import indi.pplong.acreader.feature.shelf.model.EBookEntryDao
import javax.inject.Singleton

/**
 * Description:
 * @author PPLong
 * @date 3/6/25 8:50 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCommonDatabase(@ApplicationContext context: Context): CommonDatabase {
        return CommonDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideEBookEntryDao(database: CommonDatabase): EBookEntryDao {
        return database.ebookEntryDao()
    }
}