package com.onurcicek.eticaret.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.onurcicek.eticaret.R;
import com.onurcicek.eticaret.app.AppController;
import com.onurcicek.eticaret.model.Urun;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by plox on 03.01.2017.
 */

public class UrunAdapter extends BaseAdapter {
    private Fragment fragment;
    private LayoutInflater inflater;
    private List<Urun> urunItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public UrunAdapter(Fragment fragment, List<Urun> urunItems) {
        this.fragment = fragment;
        this.urunItems = urunItems;
    }

    @Override
    public int getCount() {
        return urunItems.size();
    }

    @Override
    public Object getItem(int location) {
        return urunItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_urunler, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView soru_fotograf = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.title);
        TextView urunaciklama = (TextView) convertView.findViewById(R.id.rating);
        TextView urunfiyat = (TextView) convertView.findViewById(R.id.releaseYear);

        Urun s = urunItems.get(position);

        soru_fotograf.setImageUrl(s.getUrun_fotograf(), imageLoader);

        name.setText(s.getUrun_name());

        urunaciklama.setText(String.valueOf(s.getUrun_metin()));

        urunfiyat.setText(s.getUrun_fiyat()+" TL");

        return convertView;
    }

}
