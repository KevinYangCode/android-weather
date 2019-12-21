package xyz.jianzha.weather.cityManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import xyz.jianzha.weather.MainActivity;
import xyz.jianzha.weather.R;
import xyz.jianzha.weather.base.BaseActivity;
import xyz.jianzha.weather.base.UtilsStyle;
import xyz.jianzha.weather.bean.WeatherBean;
import xyz.jianzha.weather.db.DBManager;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener {
    EditText searchEt;
    ImageView submitIv, backIv2;
    GridView searchGv;
    String[] hotCitys = {"北京", "上海", "广州", "深圳", "珠海", "佛山", "揭阳", "吴川", "厦门", "长沙", "成都", "福州",
            "杭州", "武汉", "青岛", "西安", "太原", "沈阳", "重庆", "天津", "南宁"};
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        UtilsStyle.setTranslateStatusBar(this);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        backIv2 = findViewById(R.id.city_iv_back2);
        searchGv = findViewById(R.id.search_gv);
        // 设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);
        // 添加点击监听事件
        backIv2.setOnClickListener(this);
        submitIv.setOnClickListener(this);
        setListener();
    }

    /* 设置监听事件*/
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCitys[position];
                System.out.println("city+=======>" + city);
                loadData(city);
                System.out.println("进入监听事件！");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
                System.out.println("进入提交事件！");
                city = searchEt.getText().toString();
                System.out.println("searchEtCity=======>" + city);
                if (!TextUtils.isEmpty(city)) {
                    //判断是否能够找到这个城市的天气
                    loadData(city);
                } else {
                    Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_iv_back2:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        System.out.println("进入查询成功！");
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        if (weatherBean.getError_code() == 0) {
            // 查找后，添加或更新
            int i = DBManager.updateInfoByCity(city, result);
            if (i <= 0) {
                // 更新数据库失败，说明没有这条城市信息，增加这个城市信息。
                DBManager.addCityInfo(city, result);
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city", city);
            startActivity(intent);
            System.out.println("进入查询成功结束！");
        } else {
            Toast.makeText(this, "暂不支持该城市...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        System.out.println("查询失败！");
    }
}

