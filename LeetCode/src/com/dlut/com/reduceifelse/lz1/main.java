package com.dlut.com.reduceifelse.lz1;

import com.dlut.com.reduceifelse.common.ShareItem;
import com.dlut.com.reduceifelse.common.ShareListener;
import com.dlut.com.reduceifelse.common.TypeConstant;

public class main
{
    /**
     * �����˴�����if else
     * ��֧�϶��ԭ�����:
     * 1.��ֵ�ж�
     * 2.ҵ���ж�
     * 3.״̬�ж�
     */
    public void share(ShareItem item, ShareListener listener)
    {
        if(item != null)
        {
            if(item.type == TypeConstant.TYPE_LINK)
            {
                //�������� true����Ϊ�յĳ���
                if(true)
                {
                    //������
                }
                else
                {
                    if(listener != null)
                    {
                        listener.onCallback(ShareListener.STATE_FAIL,"������Ϣ������");
                    }
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
}
