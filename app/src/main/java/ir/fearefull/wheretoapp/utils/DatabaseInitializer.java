package ir.fearefull.wheretoapp.utils;

import ir.fearefull.wheretoapp.data.local.AppDatabase;
import ir.fearefull.wheretoapp.data.model.db.User;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void addUser(final AppDatabase appDatabase, User user) {
        appDatabase.getUserDao().insert(user);
    }

    public static void updateUser(final AppDatabase appDatabase, User user) {
        appDatabase.getUserDao().update(user);
    }

    public static int countUsers(final AppDatabase appDatabase) {
        return appDatabase.getUserDao().countUsers();
    }

    public static void resetUsers(final AppDatabase appDatabase) {
        appDatabase.getUserDao().resetTable();
    }

    public static User getUser(final AppDatabase appDatabase) {
        return appDatabase.getUserDao().getLastUser();
    }
}