package com.hussar.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hussar.demo.utils.GetPathFromUri4kitkat;

import video.hussar.demo.R;

public class SourceSelectorActivity extends Activity {
    private String PATH = "http://120.25.226.186:32812/resources/videos/minion_01.mp4";
    private final static int FILE_SELECT_CODE = 0x10001;
    private EditText mUrlText;
    private Button mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission(); //检查权限

        setContentView(R.layout.activity_source_selector);
        mUrlText = (EditText) findViewById(R.id.url);
        mUrlText.setText(PATH);
        mUrlText.setLines(3);
        mLoader = (Button) findViewById(R.id.btLoader);
        mLoader.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showFileChooser();
            }
        });

        findViewById(R.id.btSample1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startSample(PortraitPlayerActivity.class);
            }
        });

        findViewById(R.id.btSample2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startSample(LandScapePlayerActivity.class);
            }
        });

        findViewById(R.id.btSample3).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startSample(ListPlayerActivity.class);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK && requestCode == FILE_SELECT_CODE) {
            Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过s程。
            String url = GetPathFromUri4kitkat.getPath(this, uri);
            if (url == null || url.trim().equals("")) {
                url = GetPathFromUri4kitkat.getPathBelowkitkat(this, uri);
            }
            mUrlText.setText(url);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }
    }

    private void startSample(Class className) {
        Intent intent = new Intent(this, className);
        String url = mUrlText.getText().toString();
        intent.putExtra(PortraitPlayerActivity.TAG_URL, url);
        startActivity(intent);
    }

    private final static int CODE_FOR_READ_PERMISSION = 0x10001;

    private void checkPermission() {
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { //当sdk版本大于23时
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_FOR_READ_PERMISSION);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_FOR_READ_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                mLoader.setVisibility(View.INVISIBLE);
            }
        }
    }
}
