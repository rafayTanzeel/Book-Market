package com.bookmarketer.nw.bookmarket.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookmarketer.nw.bookmarket.BookInfo;
import com.bookmarketer.nw.bookmarket.R;

/**
 * Created by Yoonsung on 2016-02-28.
 */
public class SearchAdapter extends BaseAdapter {
    private Context mcontext;
    private String mTokenId;
    private int misbn;
    private String mtitle;
    private String mAuthor;
    private String mPublisher;
    private String mBookURL;
    private float mprice;
    private BookInfo[] mBooks = {
            new BookInfo("dddd","ffff","eeee","qqqq",5),
            new BookInfo("dfdddd","fsf","eeqwee","qqqq",5),
            new BookInfo("dddd","ffff","erte","ttwqq",24),
            new BookInfo("dddd","ffff","eeee","qqqq",5),
            new BookInfo("rrwdd","qwff","rwee","qqqq",5),

    };


    public SearchAdapter(Context context,BookInfo[] books){
        mcontext = context;
        mBooks = books;
    }
    public SearchAdapter(Context context){
        mcontext = context;
    }
    @Override
    public int getCount() {
        return mBooks.length;
    }

    @Override
    public Object getItem(int position) {
        return mBooks[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.d("Tjidsohfds", "Kill me");
        if (convertView == null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.search_list_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.Title = (TextView)convertView.findViewById(R.id.feedBookTitleTextView);
            holder.Seller = (TextView) convertView.findViewById(R.id.feedSellerNameTextView);
            holder.Author = (TextView)convertView.findViewById(R.id.feedAuthorTextView);
            holder.Price = (TextView)convertView.findViewById(R.id.feedPriceTextView);
            holder.Publisher = (TextView)convertView.findViewById(R.id.feedPublisherTextView);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        BookInfo book = mBooks[position];
       holder.Title.setText(book.getTitle());
        holder.Author.setText("ddddddddd");
        holder.Seller.setText("dddddd");
        holder.Price.setText("10");
        holder.Publisher.setText("dslkjfldskjf");
        return convertView;

    }
    private static class ViewHolder{
        ImageView thumbnail;
        TextView Seller;
        TextView Title;
        TextView Price;
        TextView Author;
        TextView Publisher;
    }
}
