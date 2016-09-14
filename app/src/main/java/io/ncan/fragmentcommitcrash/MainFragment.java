package io.ncan.fragmentcommitcrash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.Collections;

/**
 * Created by ncantelmo on 9/14/16.
 */
public class MainFragment extends Fragment {
  private static final int HANDLE_FRAGMENT_LAUNCH = 311;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);

    ListView dummyList = (ListView) view.findViewById(R.id.dummy_list);
    dummyList.setAdapter(new DummyAdapter(getActivity(), Collections.singletonList("Click me")));
    dummyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        launchEmailFragment();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        launchEmailFragment();
      }
    });
    return view;
  }

  private void launchEmailFragment() {
    EmailFragment emailFragment = new EmailFragment();
    FragmentLaunchHandler handler = new FragmentLaunchHandler(this, emailFragment, "Email Fragment");
    handler.sendEmptyMessage(HANDLE_FRAGMENT_LAUNCH);
  }

  private static class FragmentLaunchHandler extends Handler {
    private final WeakReference<Fragment> mParentFragmentRef;
    private final Fragment mFragmentToLaunch;
    private final String mTag;

    FragmentLaunchHandler(Fragment parentFragment, Fragment fragmentToLaunch, String tag) {
      mParentFragmentRef = new WeakReference<>(parentFragment);
      mFragmentToLaunch = fragmentToLaunch;
      mTag = tag;
    }

    @Override
    public void handleMessage(Message msg) {
      Fragment parentFragment = mParentFragmentRef.get();
      if (parentFragment != null && msg.what == HANDLE_FRAGMENT_LAUNCH) {
        Activity activity = parentFragment.getActivity();
        if (activity != null && activity instanceof MainActivity) {
          ((MainActivity) activity).addFragment(mFragmentToLaunch, mTag);
        }
      }
    }
  }
}

