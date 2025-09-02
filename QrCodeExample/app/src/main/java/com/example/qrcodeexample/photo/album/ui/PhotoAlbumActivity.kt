package com.example.qrcodeexample.photo.album.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodeexample.R
import com.example.qrcodeexample.manager.PermissionManager
import com.example.qrcodeexample.photo.album.adapter.PhotoAdapter
import com.example.qrcodeexample.photo.model.Photo

class PhotoAlbumActivity : ComponentActivity() {

    companion object {
        val imageBaseUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
    private lateinit var permissionManager: PermissionManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_album)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_photo_album)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        permissionManager = PermissionManager(this)

        // 根据安卓版本动态选择权限
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        permissionManager.request(permissionToRequest) {
            isGranted ->
            if (isGranted) {
                loadPhotoFromGallery()
            }
            else {
                Toast.makeText(this, R.string.photo_permission_rejected_tip, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadPhotoFromGallery() {
        recyclerView.adapter = PhotoAdapter(createListOfPhoto())
    }

    private fun createListOfPhoto() : List<Photo> {
        val photos = mutableListOf<Photo>()


        // 注意，每张图片的地址(Uri)是将图片的_ID(行号，是个Long值)拼接到基地址后面得到的
        // 下面的projection可以看成是用sql查询数据库的时候给出的属性值
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_MODIFIED
        )

        val resolver = this.contentResolver
        // 这个是返回数据的排序，参考学数据库的时候的写法(DESC降序)
        // 使用“修改日期”这个属性来排序
        val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        // 使用cursor的use方法是为了确保cursor的自动关闭
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

            // 注意，cursor的初始位置是在第0行之前的(-1行，无数据行)
            // 所以在这里必须一上来就使用moveToNext()
            while (it.moveToNext()) {
                val id  = it.getLong(idColumn)
                val uri = Uri.withAppendedPath(imageBaseUri, id.toString())
                val name = it.getString(nameColumn)
                val date = it.getLong(dateColumn)

                photos.add(Photo(id = id, uri = uri, name = name, date = date))
            }
        }

        return photos
    }
}