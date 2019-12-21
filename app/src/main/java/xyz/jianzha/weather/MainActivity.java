package xyz.jianzha.weather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import xyz.jianzha.weather.base.BaseFragment;
import xyz.jianzha.weather.base.Tools;
import xyz.jianzha.weather.base.UtilsStyle;
import xyz.jianzha.weather.bean.WeatherBean;
import xyz.jianzha.weather.cityManager.CityManagerActivity;
import xyz.jianzha.weather.cityManager.SearchCityActivity;
import xyz.jianzha.weather.db.DBManager;

public class MainActivity extends BaseFragment implements View.OnClickListener {
    TextView tempTv, cityTv, conditionTv, tempRangeTv, dateTv;// 温度，城市，天气，最大最小温度，时间
    LinearLayout futureLayout; // 未来天气列表
    SwipeRefreshLayout swipeRefreshLayout; // 下拉刷新组件
    ImageView addCityIv;
    ImageView listIv;
    ImageView itemMainIv;
    String city = "广州";

    // 表示需要显示的城市的集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 状态栏透明设置
        UtilsStyle.setTranslateStatusBar(this);

        addCityIv = findViewById(R.id.main_iv_add);
        listIv = findViewById(R.id.main_iv_list);
        tempTv = findViewById(R.id.frag_tv_currenttemp);
        itemMainIv = findViewById(R.id.item_main_iv);
        cityTv = findViewById(R.id.frag_tv_city);
        conditionTv = findViewById(R.id.frag_tv_condition);
        tempRangeTv = findViewById(R.id.frag_tv_temprange);
        dateTv = findViewById(R.id.frag_tv_date);
        futureLayout = findViewById(R.id.frag_center_layout);
        swipeRefreshLayout = findViewById(R.id.out_layout);
        // 添加点击事件
        addCityIv.setOnClickListener(this);
        listIv.setOnClickListener(this);

        // 因为可能搜索界面点击跳转此界面，会传值，所以此处获取一下
        Intent intent = getIntent();
        String Sercity = intent.getStringExtra("city");
        try {
            if (!TextUtils.isEmpty(Sercity)) {
                city = Sercity;
            }
        } catch (Exception e) {
            Log.i("Kevin", "程序出现问题了！！");
        }
        // 调用父类获取数据的方法
        loadData(city);
        /*下拉刷新*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                futureLayout.removeAllViews();
                loadData(city);
            }
        });
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        // 数据库当中查找上一次信息显示在Fragment当中
        try {
            String s = DBManager.queryInfoByCity(city);
            if (!TextUtils.isEmpty(s)) {
                parseShowData(s);
            }
        } catch (Exception e) {
            Log.i("Kevin", "程序出现问题了！！");
        }
    }

    @Override
    public void onSuccess(String result) {
        // 解析并展示数据
        parseShowData(result);
    }

    private void parseShowData(String result) {
        // 使用gson解析数据
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.ResultBean resultBean = weatherBean.getResult(); //天气所有数据
        WeatherBean.ResultBean.RealtimeBean realtimeBean = resultBean.getRealtime();// 当前天气情况
        List<WeatherBean.ResultBean.FutureBean> futureBeanList = resultBean.getFuture();// 1-5天天气数据
        // 设置TextView
        tempTv.setText(realtimeBean.getTemperature() + "℃");// 温度，返回当前天气只有数字。
        cityTv.setText(resultBean.getCity()); //城市
        conditionTv.setText(realtimeBean.getInfo()); //天气
        String temps = Tools.update(futureBeanList.get(0).getTemperature());// 温度处理l
        tempRangeTv.setText(temps); //最大最小温度
        dateTv.setText(futureBeanList.get(0).getDate()); //时间
        // 调用工具类获取对应图标
        int mainIcon = Tools.mainIcon(realtimeBean.getInfo());
        itemMainIv.setImageResource(mainIcon);

        /* Notification通知 */
        String name = "FiveWeather1";//渠道名字
        String id = "FiveWeather1_1"; // 渠道ID
        String description = "FiveWeather_channel"; // 渠道解释说明
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        //判断是否是8.0上设备
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true); //是否在桌面icon右上角展示小红点
            notificationManager.createNotificationChannel(mChannel);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationBuilder.
                    setSmallIcon(R.drawable.weather)
                    .setContentTitle("天气消息！")
                    .setContentText(resultBean.getCity() + "今天的天气：" + realtimeBean.getInfo())
                    .setChannelId(id)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);
        } else {
            notificationBuilder.
                    setSmallIcon(R.drawable.weather)
                    .setContentTitle("天气消息！")
                    .setContentText(resultBean.getCity() + "今天的天气：" + realtimeBean.getInfo())
                    .setAutoCancel(true);
        }
        notificationManager.notify(0, notificationBuilder.build());




        /* 未来5天分析 */
        for (int i = 0; i < futureBeanList.size(); i++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_future_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);// 时间
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);//天气
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);//最大最小温度
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);// 图标
            // 获取对应的位置的天气情况
            WeatherBean.ResultBean.FutureBean dataBean = futureBeanList.get(i);
            idateTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            String temp = Tools.update(dataBean.getTemperature());
            itemprangeTv.setText(temp);
            /* 设置未来天气列表中的图标 */
            // 调用工具类获取对应图标
            int listIcon = Tools.listIcons(dataBean.getWid().getDay());
            iIv.setImageResource(listIcon);
        }
        /*下拉刷新终止*/
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /* 点击事件 */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                int cityCount = DBManager.getCityCount();
                if (cityCount < 5) {
                    intent.setClass(this, SearchCityActivity.class);
                } else {
                    Toast.makeText(this, "存储城市数量已达上限，请删除后再增加", Toast.LENGTH_SHORT).show();
                    intent.setClass(this, MainActivity.class);
                }
                break;
            case R.id.main_iv_list:
                intent.setClass(this, CityManagerActivity.class);
                break;
        }
        startActivity(intent);
    }
}
