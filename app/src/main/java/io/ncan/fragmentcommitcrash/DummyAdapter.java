package io.ncan.fragmentcommitcrash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DummyAdapter extends ArrayAdapter<String> {
  private final List<String> mItems;
  private LayoutInflater mInflater;

  public DummyAdapter(Context context, List<String> items) {
    super(context, R.layout.view_item_dummy, R.id.dummy_text, items);
    mItems = items;
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.view_item_dummy, parent, false);
    }
    TextView textView = (TextView) convertView.findViewById(R.id.dummy_text);
    textView.setText(mItems.get(position));
    return convertView;
  }
}
