package xyz.jianzha.weather.cityManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xyz.jianzha.weather.R;
import xyz.jianzha.weather.base.UtilsStyle;
import xyz.jianzha.weather.db.DBManager;

/**
 * 城市管理操作
 */
public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView errorIv, rightIv;
    ListView deleteLv;
    List<String> mDatas;   //listview的数据源
    List<String> deleteCitys;  //表示存储了删除的城市信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        UtilsStyle.setTranslateStatusBar(this);
        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);
        mDatas = DBManager.queryAllCityName();
        deleteCitys = new ArrayList<>();
        // 设置点击监听事件
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);
        // 适配器的设置
        DeleteCityAdapter adapter = new DeleteCityAdapter(this, mDatas, deleteCitys);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改么？")
                        .setPositiveButton("舍弃更改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();   //关闭当前的activity
                            }
                        });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for (int i = 0; i < deleteCitys.size(); i++) {
                    String city = deleteCitys.get(i);
                    // 调用删除城市的函数
                    DBManager.deleteInfoByCity(city);
                }
                // 删除成功返回上一级页面
                finish();
                break;
        }
    }
}
