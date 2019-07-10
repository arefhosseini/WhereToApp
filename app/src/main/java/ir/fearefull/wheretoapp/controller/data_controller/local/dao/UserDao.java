package ir.fearefull.wheretoapp.controller.data_controller.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ir.fearefull.wheretoapp.model.db.User;

@Dao
public interface UserDao {
    @Insert
    public void insert(User... users);

    @Update
    public void update(User... users);

    @Delete
    public void delete(User user);

    @Query("SELECT * FROM user")
    public List<User> getUsers();

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    public User getLastUser();

    @Query("SELECT * FROM user WHERE id = :id")
    public User getUserById(Long id);

    @Query("SELECT * FROM user WHERE phone_number = :phoneNumber")
    public User getUserByPhoneNumber(String phoneNumber);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Query("DELETE FROM user")
    public void resetTable();
}
