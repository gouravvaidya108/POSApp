package com.example.posapp.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posapp.R
import com.example.posapp.models.Product

class ProductAdapter(private val productList: MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product_item,
            parent,
            false
        )

        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]

        // Replace with actual image loading code
        holder.image.setImageResource(R.drawable.placeholder)

//        Glide.with(holder.image.context)
//            .load("") //Not able to get the profuct image url ever from CDN
//            .placeholder(R.drawable.placeholder)
//            .into(holder.image)

        holder.name.text = currentProduct.name.en
        holder.price.text = "$${currentProduct.price}"

        val colors = listOf(Color.LTGRAY, Color.DKGRAY, Color.BLUE) // Replace with the colors of the product
        holder.createColorCircles(colors)
    }

    override fun getItemCount() = productList.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val colorContainer: LinearLayout = itemView.findViewById(R.id.colorContainer)
        val moreColors: TextView = itemView.findViewById(R.id.more_colors)



         fun createColorCircles(colors: List<Int>) {
            colorContainer.removeAllViews() // clear old views
            for (color in colors) {
                val imageView = ImageView(colorContainer.context)
                imageView.layoutParams = LinearLayout.LayoutParams(20, 20)
                val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, imageView.resources.displayMetrics).toInt()
                (imageView.layoutParams as LinearLayout.LayoutParams).marginStart = margin
                imageView.background = createCircleDrawable(color)
                colorContainer.addView(imageView)
            }
             colorContainer.addView(moreColors)
        }

        private fun createCircleDrawable(color: Int): Drawable {
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.OVAL
            shape.setColor(color)
            return shape
        }

    }

    fun addData(newProducts: List<Product>) {
        val startPos = productList.size
        productList.addAll(newProducts)
        notifyItemRangeInserted(startPos, newProducts.size)
    }
    fun clearData() {
        productList.clear()
        notifyDataSetChanged()
    }




}

