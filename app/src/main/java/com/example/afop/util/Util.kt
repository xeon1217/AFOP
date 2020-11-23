package com.example.afop.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.File
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
                                        //print("${format_kor.format(it[3].toInt())}${radix[3]} ")
                                    }
                                    if (it.size >= 3 && it[2].toInt() != 0) {
                                        result += "${format_kor.format(it[2].toInt())}${radix[2]} "
                                        //print("${format_kor.format(it[2].toInt())}${radix[2]} ")
                                    }
                                    if (it.size >= 2 && it[1].toInt() != 0) {
                                        result += "${format_kor.format(it[1].toInt())}${radix[1]} "
                                        //print("${format_kor.format(it[1].toInt())}${radix[1]} ")
                                    }
                                    if (it.isNotEmpty() && it[0].toInt() != 0) {
                                        result += "${format_kor.format(it[0].toInt())}${radix[0]}"
                                        //print("${format_kor.format(it[0].toInt())}${radix[0]}")
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

        fun getRealPathFromURI(context: Context, uri: Uri): String? {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    return if ("primary".equals(type, ignoreCase = true)) {
                        (Environment.getExternalStorageDirectory().toString() + "/"
                                + split[1])
                    } else {
                        val SDcardpath = getRemovableSDCardPath(context).split("/Android".toRegex())
                            .toTypedArray()[0]
                        SDcardpath + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(
                        context, contentUri, selection,
                        selectionArgs
                    )
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                    context,
                    uri,
                    null,
                    null
                )
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        fun getRemovableSDCardPath(context: Context?): String {
            val storages: Array<File?> = ContextCompat.getExternalFilesDirs(context!!, null)
            return if (storages.size > 1 && storages[0] != null && storages[1] != null) storages[1].toString() else ""
        }

        fun getDataColumn(
            context: Context, uri: Uri?,
            selection: String?, selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(
                    uri!!, projection,
                    selection, selectionArgs, null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index: Int = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null) cursor.close()
            }
            return null
        }

        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri
                .authority
        }

        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri
                .authority
        }

        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri
                .authority
        }

        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri
                .authority
        }
    }
}