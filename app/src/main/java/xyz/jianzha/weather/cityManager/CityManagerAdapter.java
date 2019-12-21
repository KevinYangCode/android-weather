package xyz.jianzha.weather.cityManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import java.util.List;

import xyz.jianzha.weather.MainActivity;
import xyz.jianzha.weather.R;
import xyz.jianzha.weather.bean.WeatherBean;
import xyz.jianzha.weather.db.DatabaseBean;

/**
 * 城市列表适配器
 */
public class CityManagerAdapter extends BaseAdapter {
    private Context context;
    private List<DatabaseBean> mDatas;

    CityManagerAdapter(Context context, List<DatabaseBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DatabaseBean bean = mDatas.get(position);
        holder.cityTv.setText(bean.getCity());
        WeatherBean weatherBean = new Gson().fromJson(bean.getContent(), WeatherBean.class);
        // 获取今日天气情况
        WeatherBean.ResultBean.FutureBean dataBean = weatherBean.getResult().getFuture().get(0);
        holder.conTv.setText("天气:" + dataBean.getWeather() + "℃");
        holder.currentTempTv.setText(weatherBean.getResult().getRealtime().getTemperature());
        holder.windTv.setText(dataBean.getDirect());
        holder.tempRangeTv.setText(dataBean.getTemperature());
        final String city = bean.getCity();
        holder.itemCityCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("city", city);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView cityTv, conTv, currentTempTv, windTv, tempRangeTv;
        CardView itemCityCv;

        ViewHolder(View itemView) {
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            currentTempTv = itemView.findViewById(R.id.item_city_tv_temp);
            windTv = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);
            itemCityCv = itemView.findViewById(R.id.item_city_cv);
        }
    }
}
