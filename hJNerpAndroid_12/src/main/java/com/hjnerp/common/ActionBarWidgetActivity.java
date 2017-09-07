package com.hjnerp.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjnerp.model.LoginConfig;
import com.hjnerp.net.HttpClientManager;
import com.hjnerp.widget.WaitDialogRectangle;
import com.hjnerpandroid.R;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 2017/8/31.
 */

public class ActionBarWidgetActivity extends AppCompatActivity implements
        IActivitySupport {
    protected Context mContext;
    //弹框
    protected WaitDialogRectangle waitDialog;
    protected String JSON_VALUE = "values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 创建部分实体
     */
    private void initView() {
        mContext = this;
        waitDialog = new WaitDialogRectangle(mContext);
    }


    /**
     * 长toast
     *
     * @param content
     */
    public void toastLONG(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void toastSHORT(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短toast
     *
     * @param content
     */
    public void LogShow(String content) {
        Log.e("MZ", content);
    }


    /**
     * bundle
     *
     * @param from
     * @param to
     */
    public void intentActivity(Context from, Class to, Bundle bundle) {
        Intent intent = new Intent(from, to);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public EapApplication getEapApplication() {
        return null;
    }

    @Override
    public void stopService() {

    }

    @Override
    public void startService() {

    }

    @Override
    public boolean validateInternet() {
        return false;
    }

    @Override
    public boolean hasInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(mContext.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void isExit() {

    }

    @Override
    public boolean hasLocationGPS() {
        return false;
    }

    @Override
    public boolean hasLocationNetWork() {
        return false;
    }

    @Override
    public void checkMemoryCard() {

    }

    @Override
    public ProgressDialog getProgressDialog() {
        return null;
    }

    @Override
    public WaitDialogRectangle getWaitDialogRectangle() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void saveLoginConfig(LoginConfig loginConfig) {

    }

    @Override
    public LoginConfig getLoginConfig() {
        return null;
    }

    @Override
    public void setNotiType(int iconId, String contentTitle, String contentText, Class activity, String from) {

    }

//    //网络查询表格的方法，将来可以考虑写成公用的方法
//    private class NsyncDataHandler extends HttpClientManager.HttpResponseHandler {
//        @Override
//        public void onException(Exception e) {
//        }
//
//        @Override
//        public void onResponse(HttpResponse resp) {
//            // TODO Auto-generated method stub
//            try {
//                String contentType = resp.getHeaders("Content-Type")[0]
//                        .getValue();
//                // if ("application/octet-stream".equals(contentType) ) {
//                if (contentType.indexOf("application/octet-stream") != -1) {
//                    String contentDiscreption = resp
//                            .getHeaders("Content-Disposition")[0].getValue();
//                    String fileName = contentDiscreption
//                            .substring(contentDiscreption.indexOf("=") + 1);
//                    FileOutputStream fos = new FileOutputStream(new File(
//                            getExternalCacheDir(), fileName));
//                    resp.getEntity().writeTo(fos);
//                    fos.close();
//                    String json = processBusinessCompress(fileName);
//                    JSONObject jsonObject = new JSONObject(json);
//                    String value = jsonObject.getString(JSON_VALUE);
//
////                    Log.d("value", value);
//                    processJsonValue(value);
//                } else {
//                }
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    //解压缩下载的zip包
    public String processBusinessCompress(String fileName) {
        // TODO Auto-generated method stub
        ZipInputStream zis = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            File f = new File(getExternalCacheDir(), fileName);
            FileInputStream fis = new FileInputStream(f);
            zis = new ZipInputStream(fis);
            ZipEntry zip = zis.getNextEntry();
            int len = 0;
            while ((len = zis.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            String json = new String(baos.toByteArray(), HTTP.UTF_8);
            return json;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (zis != null) {
                    zis.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
