package com.minq.lolmatchrecord.util

import java.sql.Timestamp
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class TimeUtil {
    fun dateTimeToEpoch(datetimeStr: String): Long {
        val formattedStr = datetimeStr.replace(" ", "+")
        val offsetDateTime = OffsetDateTime.parse(formattedStr)
        return offsetDateTime.toInstant().toEpochMilli()
    }
    fun epochToDateTime(epoch: Long): String {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.of("Asia/Seoul")).toString()
    }
    fun timezone(timestamp: Timestamp): Timestamp{
        val instant: Instant = timestamp.toInstant()
        val seoulTime: ZonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"))
        return Timestamp.from(seoulTime.toInstant())
    }
}