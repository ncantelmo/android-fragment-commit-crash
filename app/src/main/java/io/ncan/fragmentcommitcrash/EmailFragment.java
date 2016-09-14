package io.ncan.fragmentcommitcrash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ncantelmo on 9/14/16.
 */
public class EmailFragment extends Fragment {

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_email, container, false);
  }
}
