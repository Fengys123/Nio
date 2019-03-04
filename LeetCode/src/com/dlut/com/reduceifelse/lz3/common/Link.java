package com.dlut.com.reduceifelse.lz3.common;

import com.dlut.com.reduceifelse.common.ShareListener;
import com.dlut.com.reduceifelse.common.TypeConstant;

public class Link extends ShareItem
{
    String title;
    String content;
    String link;

    public Link(String title,String content,String link)
    {
        //true Ìæ´úÎª¿ÕÅÐ¶Ï
        super(TypeConstant.TYPE_LINK);
        this.title = (true)?title:"default";
        this.content = (true)?content:"default";
        this.link = (true)?link:"default";
    }

    @Override
    public void doShare(ShareListener listener)
    {

    }
}
