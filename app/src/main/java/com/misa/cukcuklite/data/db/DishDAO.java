package com.misa.cukcuklite.data.db;

import com.misa.cukcuklite.data.db.model.Dish;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DishDAO {

    @Query("SELECT * FROM dishes")
    List<Dish> getAllDish();

    @Insert
    void insertDish(Dish dish);

    @Delete
    void deleteDish(Dish dish);

    @Update
    void updateDish(Dish dish);

}