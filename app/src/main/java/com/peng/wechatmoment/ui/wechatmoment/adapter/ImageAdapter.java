package com.peng.wechatmoment.ui.wechatmoment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.peng.wechatmoment.R;
import com.peng.wechatmoment.bean.WeChatMomentsItemBean;
import com.peng.wechatmoment.ui.imageloader.ImageLoader;

import java.util.List;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/29 23:23
 * desc   : 九宫格适配器
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<WeChatMomentsItemBean.ImagesBean> list;

    public ImageAdapter(Context context, List<WeChatMomentsItemBean.ImagesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_image, null);
            holder.image = convertView.findViewById(R.id.iv);

            if (list != null && list.size() == 1) {
                // 当只有一张图片时，重新设置图片的显示尺寸
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.image.getLayoutParams();
                params.height = ConvertUtils.dp2px(200);
                params.width = ConvertUtils.dp2px(200);
                holder.image.setLayoutParams(params);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list != null) {
            ImageLoader.getInstance()
                    .defaultDisplay(R.mipmap.ic_launcher)
                    .displayImage("http://e.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d10b510fdea233fb80e7aec9021.jpg", holder.image);
        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView image; // 九宫格图片
    }
}

