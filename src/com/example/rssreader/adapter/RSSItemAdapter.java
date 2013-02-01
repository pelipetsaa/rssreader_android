package com.example.rssreader.adapter;

import java.util.List;

import com.example.rssreader.Application;
import com.example.rssreader.R;
import com.example.rssreader.model.RSSItem;
import com.example.rssreader.utils.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RSSItemAdapter extends BaseAdapter {

	private Context context;
	private List<RSSItem> itemList;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;

	public RSSItemAdapter(Context context, List<RSSItem> itemList) {
		this.context = context;
		this.itemList = itemList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//this.imageLoader = new ImageLoader(context);
		this.imageLoader = Application.getInstance().getAppContext().getImageLoader();
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = inflater.inflate(R.layout.rss_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.item_image);
			holder.title = (TextView) view.findViewById(R.id.item_title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
			final RSSItem item = itemList.get(position);
			
			holder.title.setText(item.getTitle());
			imageLoader.displayImage(item.getImageUrl(), holder.image);

			
		return view;
	}

	private class ViewHolder {
		public ImageView image;
		public TextView title;		
	}


}
