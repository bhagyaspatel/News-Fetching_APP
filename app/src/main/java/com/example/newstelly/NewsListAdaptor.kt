package com.example.newstelly

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// view holder humare views ko hold karke rakhta hai ..so phone pe dikhne wale har view ke lie 1 view
//holder hoga..so it creates a view object and also creates a holder to hold this object
// adaptor : yeh humare data ko 1 form se dusre form me dalega..viewHolder ko data 1 particular
//format me chahiye hota hai ..to uss form me data ko convert karne ka kaam adaptor ka
class NewsListAdaptor (val context: Context) : RecyclerView.Adapter<NewsListAdaptor.NewsViewHolder>() {
    // extending abstract class RecylerView
    //These below 3 are abstract method of the RecyclerView cLass which we have to implement
    //RecyclerView.Adapter<NewsViewHolder>..this is a generic type

        private val items: ArrayList<News> = ArrayList()
        //"items" are list of items/data which are to be passed to ViewHolder

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            //this method creates a viewHolder and usme views ko store karata hai
            //apni screen pe jitne views dikh rahe hai utne views ke lie yeh viewHolder call hoga
            //return type is NewsViewHolder.

            //here first we have to make a view which will be stored in viewHolder
            //for that we have to make an object of LayoutInflater class ..this class converts our xml
            //file into an java object (i.e. converts to view type)

            val inflater = LayoutInflater.from(parent.context)
            //parent is a formal parameter (see function signature)

            val myView = inflater.inflate(R.layout.item_news, parent, false) //returns a view (type)
            //inflate (source(kisko inflate karna hai i.e xml file ki id), context, attachToRoot)

            val viewHolder = NewsViewHolder(myView)

            //item ke click karne pe kya hoga woh hum isi function me handle kar sakte hai
            //par click karne pe kya ho raha hai yeh decide karne ki zimmedari  activity ka hona chahiye na ki
            //adapter ki...par activity ko pata kaise chalega ki view click hua hai..to uske liye hume
            //1 call back chahiye jo adapter se activity ko bataye ki click hua hai..so to make call back
            //we use interface
    //        myView.setOnClickListener {
    //            interfaceListener.onItemClicked(items[viewHolder.absoluteAdapterPosition])
    //        }
                return NewsViewHolder(myView) //returning object of NewsViewHolder type
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            //viewHolder ke view me data bind karne ke lie hum iss function ka use karte hai
            //aur ye data humne NewsViewHolder class me jo refarance banaya hai "titleView" wahi hai
            val currentItem = items[position]

            holder.titleView.text =
                currentItem.title //binding data to ViewHolder..yaha "title" humari API wali url ka hai
            holder.author.text = currentItem.author
            Glide.with(holder.itemView.context).load(currentItem.ImgUrl).into(holder.image)

            holder.news.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()

                customTabsIntent.launchUrl(context, Uri.parse(currentItem.url))
            }

            //we can also set colors to our items(views) here
            var color = "#CCCCCC"

            if (position % 2 == 0){
                color = "#FFFFFF"
            }
            holder.news.setBackgroundColor(Color.parseColor(color))
        }

        override fun getItemCount(): Int {
            //this method runs only for 1 time and it specifies how many items there will be in your lists
            return items.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateNews(updatedNews: ArrayList<News>) { //to update our news

            items.clear()
            items.addAll(updatedNews)

            notifyDataSetChanged()
            //iss function se upar ke 3 functions wapis se call hoge and news update ho jayegi
        }

    //we have to create items jo humare recycler view ke andar repeat hoga
//woh humne item_news.xml file (which is a new Layout Resource file) me banaya hai
        class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //**### nested class ***###//
            //uss xml file me jo puri constraint layout hai woh pura ka pura humara "itemView" hai
            //aur uss "itemView ke andar humara TextView hai jiski id hai "title"

            //    val titleView : TextView = itemView.findViewById(R.id.title)
            val titleView = itemView.findViewById<TextView>(R.id.title)  //,,,,same as above
            //storing textView ke referances so that hume bar bar yeh findViewById na karna pade

            val image = itemView.findViewById<ImageView>(R.id.image)

            val author = itemView.findViewById<TextView>(R.id.author)
            var news = itemView.findViewById<CardView>(R.id.news)
            val container = itemView.findViewById<ConstraintLayout>(R.id.container)
        }

}
//interface for call back
//interface NewsItemClicked {
//    fun onItemClicked(item : News)
//}