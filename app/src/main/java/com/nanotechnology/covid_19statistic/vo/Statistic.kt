package com.nanotechnology.covid_19statistic.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Statistic(
   @PrimaryKey
   @ColumnInfo(name = "cases")
   val cases:Long,

   @ColumnInfo(name = "deaths")
   val deaths:Long,

   @ColumnInfo(name = "recovered")
   val recovered:Long,

   @ColumnInfo(name = "active")
   val active:Long,

   @ColumnInfo(name = "updated")
   val updated:Long
)


