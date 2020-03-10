package com.nanotechnology.covid_19statistic.vo

import androidx.room.ColumnInfo
import androidx.room.Entity




data class Statistic(
   val cases:Long,
   val deaths:Long,
   val recovered:Long,
   val updated:Long
)


//@Entity(primaryKeys = ["cases"])
//data class Statistic(
//   @ColumnInfo(name = "cases")
//   val cases:Long,
//
//   @ColumnInfo(name = "deaths")
//   val deaths:Long,
//
//   @ColumnInfo(name = "recovered")
//   val recovered:Long,
//
//   @ColumnInfo(name = "updated")
//   val updated:Long
//)