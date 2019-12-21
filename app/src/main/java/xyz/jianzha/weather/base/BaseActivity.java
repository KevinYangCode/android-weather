package xyz.jianzha.weather.base;

import androidx.appcompat.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseActivity extends AppCompatActivity implements Callback.CommonCallback<String> {
    String url1 = "http://apis.juhe.cn/simpleWeather/query?city=";
    //    3ac465d9ee821448071a7050f230b750
    String url2 = "&key=3ac465d9ee821448071a7050f230b750";
    String url;

    public void loadData(String city) {
        url = url1 + city + url2;
        RequestParams params = new RequestParams(url);
        x.http().get(params, this);
    }

    @Override
    public void onSuccess(String result) {
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
    }

    @Override
    public void onCancelled(CancelledException cex) {
    }

    @Override
    public void onFinished() {
    }
}
