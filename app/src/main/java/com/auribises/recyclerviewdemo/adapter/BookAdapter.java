package com.auribises.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auribises.recyclerviewdemo.R;
import com.auribises.recyclerviewdemo.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishantkumar on 09/03/18.
 */

/*public class BookAdapter extends ArrayAdapter<Book>{

    Context context;
    int resource;
    ArrayList<Book> objects;

    public BookAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }
}*/

public class BookAdapter extends RecyclerView.Adapter{

    Context context;
    int resource;
    ArrayList<Book> objects;

    public BookAdapter(Context context, int resource, ArrayList<Book> objects) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtPrice;
        TextView txtName;
        TextView txtAuthor;

        public ViewHolder(View itemView) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.textViewPrice);
            txtName = itemView.findViewById(R.id.textViewName);
            txtAuthor = itemView.findViewById(R.id.textViewAuthor);
        }
    }

}
