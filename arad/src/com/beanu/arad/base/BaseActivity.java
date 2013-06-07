package com.beanu.arad.base;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class BaseActivity extends SherlockFragmentActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// ActionBar左方的返回按钮，需要覆写
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
