package com.example.team6project.tab_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;

import com.example.team6project.R;
import com.example.team6project.databinding.FragNewsMainBinding;
import androidx.annotation.RequiresApi;

public class NewsFragment extends Fragment {
    private String getScript =
            "(function() {" +
                    "var h = document.getElementsByClassName('top container')[0];" +
                    "document.body.innerHTML = '';" +
                    "document.body.appendChild(h);" +
                    "})()";
    private FragNewsMainBinding binding;
    private WebView webview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub



        return inflater.inflate(R.layout.frag_news_main, container,false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        webview = (WebView) view.findViewById(R.id.webView);
        binding = FragNewsMainBinding.inflate(getLayoutInflater());
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);

        webview.setVisibility(View.GONE);
        //打开的网址
        webview.loadUrl("https://coronaboard.kr/");
        webview.setWebViewClient(new WebViewClient(){




            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.setVisibility(View.VISIBLE);
                view.evaluateJavascript(getScript, null);
                super.onPageFinished(view, url);
                view.loadUrl("javascript:function setTop(){document.querySelector('.banner').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('.logo').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('.navbar .navbar-expand .navbar-light').style.display=\"none\";}setTop();");
                view.loadUrl("javascript:function setTop(){document.querySelector('#language-selector').style.display=\"none\";}setTop();");
            }
        });

    }
}
