package com.example.dogapp.ui.adapter

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.data.DogEntity
import com.example.dogapp.databinding.ItemDogBinding
import com.example.dogapp.viewmodel.DogViewModel

class DogAdapter(
    private var images: List<String>,
    private val viewModel: DogViewModel,
    private val navController: NavController? = null
) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    fun updateData(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    inner class DogViewHolder(private val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mediaPlayer: MediaPlayer = MediaPlayer.create(binding.root.context, R.raw.favorite_sound)

        fun bind(imageUrl: String) {
            Glide.with(binding.imageViewItemDog.context).load(imageUrl).into(binding.imageViewItemDog)

            // Clic rápido para navegar a DetailsFragment con la imagen
            itemView.setOnClickListener {
                navController?.navigate(R.id.action_dogListFragment_to_detailsFragment, Bundle().apply {
                    putString("imageUrl", imageUrl)
                })
            }

            // Clic prolongado para agregar a favoritos
            itemView.setOnLongClickListener {
                viewModel.addFavorite(DogEntity(imageUrl))
                mediaPlayer.start()  // Reproduce el sonido
                Toast.makeText(binding.root.context, "Si, es un lindo Perrito!", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }
}
