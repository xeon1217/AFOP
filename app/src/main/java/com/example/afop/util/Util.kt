package com.example.afop.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.text.DecimalFormat

class Util {
    companion object {
        enum class TimeValue(val value: Int, val maximum: Int, val msg: String) {
            SEC(60, 60, "분 전"),
            MIN(60, 24, "시간 전"),
            HOUR(24, 30, "일 전"),
            DAY(30, 12, "달 전"),
            MONTH(12, Int.MAX_VALUE, "년 전")
        }

        fun timeDiff(_timeStamp: Long): String {
            val curTime = System.currentTimeMillis()
            var diffTime = (curTime - _timeStamp) / 1000
            var msg: String = ""
            if (diffTime < TimeValue.SEC.value)
                msg = "방금 전"
            else {
                for (i in TimeValue.values()) {
                    diffTime /= i.value
                    if (diffTime < i.maximum) {
                        msg = "${diffTime}${i.msg}"
                        break
                    }
                }
            }
            return msg
        }

        fun money(_money: String): String {
            try {
                val subFormat = DecimalFormat(",####")
                val format_kor = DecimalFormat("#,###")
                val radix = listOf("", "만", "억", "조")
                var result = ""

                if (_money.isNotEmpty()) {
                    _money.apply {
                        replace(",", "").apply {
                            subFormat.format(this.toLong()).split(",").let { it ->
                                it.reversed().let {
                                    if (it.size >= 4 && it[3].toInt() != 0) {
                                        result += "${format_kor.format(it[3].toInt())}${radix[3]} "
                                        print("${format_kor.format(it[3].toInt())}${radix[3]} ")
                                    }
                                    if (it.size >= 3 && it[2].toInt() != 0) {
                                        result += "${format_kor.format(it[2].toInt())}${radix[2]} "
                                        print("${format_kor.format(it[2].toInt())}${radix[2]} ")
                                    }
                                    if (it.size >= 2 && it[1].toInt() != 0) {
                                        result += "${format_kor.format(it[1].toInt())}${radix[1]} "
                                        print("${format_kor.format(it[1].toInt())}${radix[1]} ")
                                    }
                                    if (it.isNotEmpty() && it[0].toInt() != 0) {
                                        result += "${format_kor.format(it[0].toInt())}${radix[0]}"
                                        print("${format_kor.format(it[0].toInt())}${radix[0]}")
                                    }
                                    result += "원"
                                }
                            }
                        }
                    }
                }
                return result
            } catch (e: Exception) {
                return "ERROR"
            }
        }

        fun money_comma(_money: String): String {
            try {
                val format_kor = DecimalFormat("#,###")
                var result = format_kor.format(_money.toLong())
                result += "원"
                return result
            } catch (e: Exception) {
                return "ERROR"
            }
        }
    }
}