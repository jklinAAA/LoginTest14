package com.example.logintest14.InitDataBase;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RenameTable;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;

import com.example.logintest14.Dao.NoteDao;
import com.example.logintest14.Entity.EntityNote;

@Database(
        version = 2,
        entities = {EntityNote.class},autoMigrations = {
        @AutoMigration(from = 1, to = 2),

}
)
public abstract class InitDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
    @RenameTable(fromTableName = "User", toTableName = "AppUser")
    static class MyAutoMigration implements AutoMigrationSpec { }
}