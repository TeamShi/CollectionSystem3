package com.teamshi.collectionsystem3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;

    private Button nextRigButton;

    private Button prevRigButton;

    public static List<String> getUrls() {
        return urls;
    }

    public static void setUrls(List<String> urls) {
        urls = urls;
    }

    private static List<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_htmls);

        webView = (WebView) findViewById(R.id.table_preview_web_view);
        assert webView != null;
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        List<String> urls = IOManager.previewProject();
        PreviewActivity.setUrls(urls);
        String indexURL = urls.get(0);
        webView.loadUrl(indexURL);

        nextRigButton = (Button) findViewById(R.id.button_next_rig);
        prevRigButton = (Button) findViewById(R.id.button_previous_rig);
        nextRigButton.setEnabled(true);
        prevRigButton.setEnabled(true);
        nextRigButton.setOnClickListener(this);
        prevRigButton.setOnClickListener(this);

        if (urls.size() <= 1) {
            //invisible paging buttons
            nextRigButton.setEnabled(false);
            prevRigButton.setEnabled(false);
        }

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String currentUrl = view.getUrl();
                if (currentUrl.indexOf("project_") > 0) {
                    nextRigButton.setEnabled(false);
                    prevRigButton.setEnabled(false);
                }else{
                    nextRigButton.setEnabled(true);
                    prevRigButton.setEnabled(true);
                }

                webView.loadUrl(url);
                return false;
            }
        });

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_previous_rig:
                String url = webView.getUrl();
                int index = urls.indexOf(url);
                if (index == 0) {
                    Toast.makeText(getApplicationContext(), "已经是第一个页面！", Toast.LENGTH_SHORT).show();
                    return;
                }
                url = urls.get(--index);
                webView.loadUrl(url);
                break;
            case R.id.button_next_rig:
                url = webView.getUrl();
                int max = urls.size() - 1;
                index = urls.indexOf(url);
                if (index == max) {
                    Toast.makeText(getApplicationContext(), "已经是最后一个页面！", Toast.LENGTH_SHORT).show();
                    return;
                }
                url = urls.get(++index);
                webView.loadUrl(url);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
