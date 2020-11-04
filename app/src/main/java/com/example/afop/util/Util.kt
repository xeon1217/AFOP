package com.example.afop.util

import java.text.DecimalFormat

class Util {
    companion object {
        fun money(money: String): String {
            val subFormat = DecimalFormat(",####")
            val format_kor = DecimalFormat("#,###")
            val radix = listOf<String>("", "만", "억", "조")
            var result: String = ""

            if(money.isNotEmpty()) {
                money.apply {
                    replace(",", "").apply {
                        subFormat.format(this.toLong()).split(",").let {
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
                                if (it.size >= 1 && it[0].toInt() != 0) {
                                    result += "${format_kor.format(it[0].toInt())}${radix[0]}"
                                    print("${format_kor.format(it[0].toInt())}${radix[0]}")
                                }
                                //result += "원"
                                println("원")
                            }
                        }
                    }
                }
            }
            return result
        }
    }
}