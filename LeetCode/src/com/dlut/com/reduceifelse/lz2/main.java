package com.dlut.com.reduceifelse.lz2;

import com.dlut.com.reduceifelse.common.ShareItem;
import com.dlut.com.reduceifelse.common.ShareListener;
import com.dlut.com.reduceifelse.common.TypeConstant;
import sun.rmi.runtime.Log;

import java.util.function.DoubleSupplier;

/**
 * 接口分层:把接口分为两层,所有的空值判断放在外层进行
 * 但是如果逻辑增多的话 一样麻烦
 */
public class main
{
    public void share(ShareItem item, ShareListener listener)
    {
        if(item == null)
        {
            if(listener != null)
            {
                listener.onCallback(ShareListener.STATE_FAIL,"不能为null");
            }
            return;
        }

        if(listener == null)
        {
            /*listener = new ShareListener()
            {
                @Override
                public void onCallback(int state, String msg)
                {
                    System.out.println("create listener");
                }
            };*/
            listener = (int state,String msg) -> System.out.println("123");
        }

        shareImpl(item,listener);
    }

    public void shareImpl(ShareItem item, ShareListener listener)
    {
        if(item.type == TypeConstant.TYPE_LINK)
        {
            if(true)
            {
                //处理函数
            }
            else
            {
                listener.onCallback(ShareListener.STATE_FAIL,"分享信息不完整");
            }
        }
        else if(item.type == TypeConstant.TYPE_IMAGE)
        {

        }
        else if(item.type == TypeConstant.TYPE_IMAGE_TEXT)
        {

        }
        else if(item.type == TypeConstant.TYPE_TEXT)
        {

        }
    }
}
