package xyz.jianzha.weather.base;

import xyz.jianzha.weather.R;

/**
 * 工具类
 */
public class Tools {

    // 温度处理   因为返回的温度只有数字
    public static String update(String temprange) {
        if (temprange.length() == 5) {
            temprange = "0" + temprange;
        } else if (temprange.length() == 4) {
            temprange = "0" + temprange.replace("/", "/0");
        }
        temprange = temprange.replace("/", "℃/");
        return temprange;
    }

    /**
     * 据天气返回对应的图标
     *
     * @param weather 当前的天气
     * @return 图标
     */
    public static int mainIcon(String weather) {
        switch (weather) {
            case "晴":
                return R.mipmap.i00;
            case "多云":
                return R.mipmap.i01;
            case "阴":
                return R.mipmap.i02;
            case "阵雨":
                return R.mipmap.i03;
            case "雷阵雨":
                return R.mipmap.i04;
            case "雷阵雨伴有冰雹":
                return R.mipmap.i05;
            case "雨夹雪":
                return R.mipmap.i06;
            case "小雨":
                return R.mipmap.i07;
            case "中雨":
                return R.mipmap.i08;
            case "大雨":
                return R.mipmap.i09;
            case "暴雨":
                return R.mipmap.i10;
            case "大暴雨":
                return R.mipmap.i11;
            case "特大暴雨":
                return R.mipmap.i12;
            case "阵雪":
                return R.mipmap.i13;
            case "小雪":
                return R.mipmap.i14;
            case "中雪":
                return R.mipmap.i15;
            case "大雪":
                return R.mipmap.i16;
            case "暴雪":
                return R.mipmap.i17;
            case "雾":
                return R.mipmap.i18;
            case "冻雨":
                return R.mipmap.i19;
            case "沙尘暴":
                return R.mipmap.i20;
            case "小到中雨":
                return R.mipmap.i21;
            case "中到大雨":
                return R.mipmap.i22;
            case "大到暴雨":
                return R.mipmap.i23;
            case "暴雨到大暴雨":
                return R.mipmap.i24;
            case "大暴雨到特大暴雨":
                return R.mipmap.i25;
            case "小到中雪":
                return R.mipmap.i26;
            case "中到大雪":
                return R.mipmap.i27;
            case "大到暴雪":
                return R.mipmap.i28;
            case "浮尘":
                return R.mipmap.i29;
            case "扬沙":
                return R.mipmap.i30;
            case "强沙尘暴":
                return R.mipmap.i31;
            case "霾":
                return R.mipmap.i53;
        }
        return R.mipmap.i00;
    }

    /**
     * 根据代码返回对应的图标
     *
     * @param wid 未来5天的天气情况的代码
     * @return 图标
     */
    public static int listIcons(String wid) {
        switch (wid) {
            case "00":
                return R.mipmap.i00;
            case "01":
                return R.mipmap.i01;
            case "02":
                return R.mipmap.i02;
            case "03":
                return R.mipmap.i03;
            case "04":
                return R.mipmap.i04;
            case "05":
                return R.mipmap.i05;
            case "06":
                return R.mipmap.i06;
            case "07":
                return R.mipmap.i07;
            case "08":
                return R.mipmap.i08;
            case "09":
                return R.mipmap.i09;
            case "10":
                return R.mipmap.i10;
            case "11":
                return R.mipmap.i11;
            case "12":
                return R.mipmap.i12;
            case "13":
                return R.mipmap.i13;
            case "14":
                return R.mipmap.i14;
            case "15":
                return R.mipmap.i15;
            case "16":
                return R.mipmap.i16;
            case "17":
                return R.mipmap.i17;
            case "18":
                return R.mipmap.i18;
            case "19":
                return R.mipmap.i19;
            case "20":
                return R.mipmap.i20;
            case "21":
                return R.mipmap.i21;
            case "22":
                return R.mipmap.i22;
            case "23":
                return R.mipmap.i23;
            case "24":
                return R.mipmap.i24;
            case "25":
                return R.mipmap.i25;
            case "26":
                return R.mipmap.i26;
            case "27":
                return R.mipmap.i27;
            case "28":
                return R.mipmap.i28;
            case "29":
                return R.mipmap.i29;
            case "30":
                return R.mipmap.i30;
            case "31":
                return R.mipmap.i31;
            case "53":
                return R.mipmap.i53;
        }
        return R.mipmap.i00;
    }
}
