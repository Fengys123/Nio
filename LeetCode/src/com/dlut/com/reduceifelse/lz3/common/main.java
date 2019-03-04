package com.dlut.com.reduceifelse.lz3.common;


import com.dlut.com.reduceifelse.common.ShareListener;

/**
 * 接口分层:把接口分为两层,所有的空值判断放在外层进行
 * 但是如果逻辑增多的话 一样麻烦
 *
 * 迪米特原则:就是一个类对自己依赖的类知道的越少越好。
 *            也就是说，对于被依赖的类来说，无论逻辑多么复杂，
 *            都尽量地的将逻辑封装在类的内部，对外除了提供的public方法，不对外泄漏任何信息。
 *
 * 可以在外层在分装一层Util类
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

    private void shareImpl(ShareItem item, ShareListener listener)
    {
        item.doShare(listener);
    }
}
