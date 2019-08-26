package com.mml.onceapplication.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-23 下午4:19
 * Description: This is Wife
 * Package: com.example.test.db
 * Project: Test
 */
@Entity
public class Wife{
    @PrimaryKey(autoGenerate = true)
    long id;
    String name;

    public Wife setName(String name) {
        this.name = name;
        return this;
    }
 

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "[Wife(id="+id+",name="+name+")]";
    }
}