package com.dlut.com.reduceifelse.lz3.common;

import com.dlut.com.reduceifelse.common.ShareListener;

public abstract class ShareItem
{
    int type;

    public ShareItem(int type)
    {
        this.type = type;
    }

    public abstract void doShare(ShareListener listener);
}
