package com.mml.onceapplication.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-23 上午11:13
 * Description: This is User
 * Package: com.example.test.db
 * Project: Test
 */
   @Dao
interface UserDao{
    @Query("SELECT * FROM User")
    fun getAll():LiveData<List<User>>
    @Query("SELECT * FROM User")
    fun getAllUser():DataSource.Factory<Int,User>
    @Query("SELECT * FROM User WHERE id IN (:ids)")
    fun  getAllByIds(ids:LongArray):LiveData<List<User>>

    @Insert(onConflict = REPLACE)
    fun insert(vararg entities: User)

    @Delete
    fun delete(  entity: User)
    @Update
    fun update(  entity: User)


}

@Dao
interface WorkInfoDao{
    @Query("SELECT * FROM WorkInfo")
    fun getAll():LiveData<List<WorkInfo> >

    @Query("SELECT * FROM WorkInfo WHERE id IN (:ids)")
    fun  getAllByIds(ids:LongArray):LiveData<List<WorkInfo>>

    @Insert(onConflict = REPLACE)
    fun insert(vararg entities: WorkInfo)

    @Delete
    fun delete(  entity: WorkInfo)

    @Update
    fun update(  entity: WorkInfo)

}

@Dao
interface WifeDao{
    @Query("SELECT * FROM Wife")
    fun getAll():LiveData<List<Wife> >

    @Query("SELECT * FROM Wife WHERE id IN (:ids)")
    fun  getAllByIds(ids:LongArray):LiveData<List<Wife>>

    @Insert(onConflict = REPLACE)
    fun insert(vararg entities:Wife)

    @Delete
    fun delete(  entity:Wife)

    @Update
    fun update(  entity:Wife)

}