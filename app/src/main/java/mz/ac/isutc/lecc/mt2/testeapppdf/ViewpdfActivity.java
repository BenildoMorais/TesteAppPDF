package mz.ac.isutc.lecc.mt2.testeapppdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import mz.ac.isutc.lecc.mt2.testeapppdf.databinding.ActivityViewpdfBinding;

public class ViewpdfActivity extends AppCompatActivity {

    private ActivityViewpdfBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewpdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewpdf.getSettings().setJavaScriptEnabled(true); // ...um parametro true
        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Abrindo...");

        binding.viewpdf.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                try {
                    pd.show();
                }catch (Exception e){
                    Toast.makeText(ViewpdfActivity.this, "Verifique a sua Internet", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";
        try {
            url = URLEncoder.encode(fileurl, "UTF-8");
        }catch (UnsupportedEncodingException e){
            Toast.makeText(this, "URLEncoder", Toast.LENGTH_SHORT).show();
        }
        binding.viewpdf.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

    }

}