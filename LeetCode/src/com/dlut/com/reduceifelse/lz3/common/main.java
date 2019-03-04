package com.dlut.com.reduceifelse.lz3.common;


import com.dlut.com.reduceifelse.common.ShareListener;

/**
 * �ӿڷֲ�:�ѽӿڷ�Ϊ����,���еĿ�ֵ�жϷ���������
 * ��������߼�����Ļ� һ���鷳
 *
 * ������ԭ��:����һ������Լ���������֪����Խ��Խ�á�
 *            Ҳ����˵�����ڱ�����������˵�������߼���ô���ӣ�
 *            �������صĽ��߼���װ������ڲ�����������ṩ��public������������й©�κ���Ϣ��
 *
 * ����������ڷ�װһ��Util��
 * createLinkShareItem().....
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

    private void shareImpl(ShareItem item, ShareListener listener)
    {
        item.doShare(listener);
    }
}
