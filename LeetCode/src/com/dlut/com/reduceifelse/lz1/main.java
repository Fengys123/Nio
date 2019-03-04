package com.dlut.com.reduceifelse.lz1;

public class main
{
    /**
     * 产生了大量的if else
     * 分支较多的原因分析:
     * 1.空值判断
     * 2.业务判断
     * 3.状态判断
     */
    public void share(ShareItem item,ShareListener listener)
    {
        if(item != null)
        {
            if(item.type == TypeConstant.TYPE_LINK)
            {
                //分享链接 true代表不为空的场景
                if(true)
                {
                    //处理函数
                }
                else
                {
                    if(listener != null)
                    {
                        listener.onCallback(ShareListener.STATE_FAIL,"分享信息不完整");
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
