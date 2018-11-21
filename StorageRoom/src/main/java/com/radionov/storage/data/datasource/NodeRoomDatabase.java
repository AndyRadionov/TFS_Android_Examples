package com.radionov.storage.data.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.radionov.storage.data.entities.Node;
import com.radionov.storage.data.entities.NodeChildren;

/**
 * @author Andrey Radionov
 */
@Database(entities = {Node.class, NodeChildren.class}, version = 1)
public abstract class NodeRoomDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "node_database";
    private static NodeRoomDatabase instance;

    public static NodeRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NodeRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract NodeDao nodeDao();
}
