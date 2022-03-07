package com.example.progexam

import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterEntity")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    fun getById(id: Long?): CharacterEntity

    @Insert
    fun insert(character: CharacterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(characters: List<CharacterEntity>)

    @Update
    fun update(character: CharacterEntity)

    @Delete
    fun delete(character: CharacterEntity)
}