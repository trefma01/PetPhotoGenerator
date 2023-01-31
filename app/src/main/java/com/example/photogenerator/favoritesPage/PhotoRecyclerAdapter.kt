package com.example.photogenerator.favoritesPage

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogenerator.R
import java.io.File

class PhotoRecyclerAdapter(private val context: Context, private val photos: MutableList<String>)
    :   RecyclerView.Adapter<PhotoRecyclerAdapter.MyPhotosViewHolder>()
     {
         private var myPhotos = photos;

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPhotosViewHolder {
             return MyPhotosViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false))
         }

         override fun onBindViewHolder(holder: MyPhotosViewHolder, position: Int) {
             val photo = myPhotos[position]
             holder.bind(photo)
             holder.addDeleteFun(position)
             holder.addDownloadPhoto(photo)
         }

         override fun getItemCount(): Int {
             return myPhotos.size
         }

         inner class MyPhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
             private val photoView: ImageView = itemView.findViewById(R.id.dogImage);
             private val buttonDelete : ImageButton = itemView.findViewById(R.id.itemDelete);
             private val buttonDonwload : ImageButton = itemView.findViewById(R.id.itemSave);
             var msg: String? = ""
             var lastMsg = ""
             fun bind(photo: String){
                 Glide.with(context).load(photo).into(photoView)
             }
             fun addDeleteFun(position: Int){
                 buttonDelete.setOnClickListener {
                     myPhotos.removeAt(position);
                     notifyItemRemoved(position);
                     notifyItemRangeChanged(position, itemCount)
                     myPhotos = myPhotos
                     context?.openFileOutput("myfile", Context.MODE_PRIVATE).use {
                         if (it != null) {
                                 for (photo in myPhotos){
                                     val fileContents = photo+"\n"
                                     it.write(fileContents.toByteArray())
                                 }

                         }
                     }
                 }
             }
             fun addDownloadPhoto(photo: String){
                 buttonDonwload.setOnClickListener {
                     downloadImage(photo);
                 }
             }

             private fun downloadImage(url: String) {
                 val directory = File(Environment.DIRECTORY_PICTURES)
                 if (!directory.exists()) {
                     directory.mkdirs()
                 }
                 val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                 val downloadUri = Uri.parse(url)
                 val request = DownloadManager.Request(downloadUri).apply {
                     setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                         .setAllowedOverRoaming(false)
                         .setTitle(url.substring(url.lastIndexOf("/") + 1))
                         .setDescription("")
                         .setDestinationInExternalPublicDir(
                             directory.toString(),
                             url.substring(url.lastIndexOf("/") + 1)
                         )
                 }
                 //Implemented from : https://johncodeos.com/how-to-download-image-from-the-web-in-android-using-kotlin/
                 val downloadId = downloadManager.enqueue(request)
                 val query = DownloadManager.Query().setFilterById(downloadId)
                 var downloading = true
                 while (downloading) {
                     val cursor: Cursor = downloadManager.query(query)
                     cursor.moveToFirst()
                     if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                         downloading = false
                     }
                     val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                     msg = statusMessage(url, directory, status)
                     if (msg != lastMsg) {
                             Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                         lastMsg = msg ?: ""
                     }
                     cursor.close()
                 }
             }

             private fun statusMessage(url: String, directory: File, status: Int): String? {
                 var msg = ""
                 msg = when (status) {
                     DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
                     DownloadManager.STATUS_PAUSED -> "Paused"
                     DownloadManager.STATUS_PENDING -> "Pending"
                     DownloadManager.STATUS_RUNNING -> "Downloading..."
                     DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully in $directory" + File.separator + url.substring(
                         url.lastIndexOf("/") + 1
                     )
                     else -> "There's nothing to download"
                 }
                 return msg
             }


         }

     }