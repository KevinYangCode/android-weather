package xyz.jianzha.weather.base;

import androidx.appcompat.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * xutils加载网络数据的步骤
 * 1. 声明整个模块
 * 2. 执行网络请求操作
 */
public class BaseFragment extends AppCompatActivity implements Callback.CommonCallback<String> {
    String url1 = "http://apis.juhe.cn/simpleWeather/query?city=";
    //    3ac465d9ee821448071a7050f230b750
    String url2 = "&key=3ac465d9ee821448071a7050f230b750";
    String url;

    public void loadData(String city) {
        url = url1 + city + url2;
        RequestParams params = new RequestParams(url);
        x.http().get(params, this);
    }

    /**
     * 获取数据成功时，会回调的接口
     *
     * @param result 获取到的数据
     */
    @Override
    public void onSuccess(String result) {

    }

    /**
     * 获取数据失败时，会回调的接口
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    /**
     * 取消请求时，会回调的接口
     */
    @Override
    public void onCancelled(CancelledException cex) {

    }

    /**
     * 请求完成时，会回调的接口
     */
    @Override
    public void onFinished() {

    }
}
