package com.dlut.com.reduceifelse.lz2;

import com.dlut.com.reduceifelse.common.ShareItem;
import com.dlut.com.reduceifelse.common.ShareListener;
import com.dlut.com.reduceifelse.common.TypeConstant;
import sun.rmi.runtime.Log;

import java.util.function.DoubleSupplier;

/**
 * �ӿڷֲ�:�ѽӿڷ�Ϊ����,���еĿ�ֵ�жϷ���������
 * ��������߼�����Ļ� һ���鷳
 */
public class main
{
    public void share(ShareItem item, ShareListener listener)
    {
        if(item == null)
        {
            if(listener != null)
            {
                listener.onCallback(ShareListener.STATE_FAIL,"����Ϊnull");
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
                //������
            }
            else
            {
                listener.onCallback(ShareListener.STATE_FAIL,"������Ϣ������");
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
