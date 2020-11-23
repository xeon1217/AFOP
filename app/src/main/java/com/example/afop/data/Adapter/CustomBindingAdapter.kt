package com.example.afop.data.Adapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.afop.data.dataSource.DataSource
import com.example.afop.data.dataSource.RetrofitClient

/*
@BindingAdapter("bind_image")
fun bindImage(view: ImageView, res: Int?) {
    Glide.with(view.context)
        .load(res)
        .into(view)
}

@BindingAdapter("bind_image")
fun bindImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("bind_image_server")
fun bindImageServer(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load("${view.context.cacheDir.absoluteFile}/${url}")
        .into(view)
}

@BindingAdapter("bind_image", "bind_image_error")
fun bindImage(view: ImageView, res: Int, error: Drawable) {
    val options = RequestOptions()
        .error(error)

    Glide.with(view.context)
        .load(res)
        .thumbnail(0.3f)
        .apply(options)
        .into(view)
}


 */
/*
@BindingAdapter("bind_image", "bind_image_error")
fun bindImage(view: ImageView, url: String?, error: Drawable) {
    val options = RequestOptions()
        .error(error)

    Glide.with(view.context)
        .load(url)
        .thumbnail(0.3f)
        .apply(options)
        .into(view)
}
 */

@BindingAdapter("bind_image", "bind_image_error")
fun bindImage(view: ImageView, url: String?, error: Drawable) {
    Glide.with(view.context)
        .load("$url") // 스토리지 내부의 uri에 접근
        .error(
            Glide.with(view.context)
                .load("${view.context.cacheDir}/$url") // 스토리지 내부의 캐시 디렉토리에 접근
                .error(
                    Glide.with(view.context)
                        .load("${RetrofitClient.IMAGE_URL}$url") // 네트워크 주소로 접근
                        .error(
                            Glide.with(view.context)
                                .load(error) // 에러 이미지 출력
                        )
                )
        )
        .thumbnail(0.3f)
        .into(view)
}