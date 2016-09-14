package io.ncan.fragmentcommitcrash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setupBackStackListener();
    addFragment(new MainFragment(), "Main Fragment");
  }

  private void setupBackStackListener() {
    getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
      @Override
      public void onBackStackChanged() {
        final FragmentManager fm = getSupportFragmentManager();
        if (fm != null && fm.getBackStackEntryCount() > 0) {
          FragmentManager.BackStackEntry topEntry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
          if (topEntry != null) {
            setTitle(topEntry.getName());
          }
        }
      }
    });
  }

  @Override
  public boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
        finish();
        return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void addFragment(final Fragment fragment, final String tag) {
    if (isFinishing() || isDestroyed()) {
      Log.w(TAG, "Skipping call to addFragment because current activity is finishing or destroyed");
      return;
    }

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content_frame, fragment, tag)
        .addToBackStack(tag)
        .commit();
  }
}
