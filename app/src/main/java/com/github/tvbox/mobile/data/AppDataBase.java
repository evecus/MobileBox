package com.github.tvbox.mobile.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.tvbox.mobile.cache.Cache;
import com.github.tvbox.mobile.cache.CacheDao;
import com.github.tvbox.mobile.cache.SearchDao;
import com.github.tvbox.mobile.cache.SearchHistory;
import com.github.tvbox.mobile.cache.StorageDrive;
import com.github.tvbox.mobile.cache.StorageDriveDao;
import com.github.tvbox.mobile.cache.VodCollect;
import com.github.tvbox.mobile.cache.VodCollectDao;
import com.github.tvbox.mobile.cache.VodRecord;
import com.github.tvbox.mobile.cache.VodRecordDao;


/**
 * 类描述:
 *
 * @author pj567
 * @since 2020/5/15
 */
@Database(entities = {Cache.class, VodRecord.class, VodCollect.class, StorageDrive.class, SearchHistory.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CacheDao getCacheDao();

    public abstract VodRecordDao getVodRecordDao();

    public abstract VodCollectDao getVodCollectDao();

    public abstract StorageDriveDao getStorageDriveDao();

    public abstract SearchDao getSearchDao();
}
