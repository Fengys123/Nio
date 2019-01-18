package com.dlut.eight;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class d46
{
    enum Suit
    {
        Sone,Stwo,Sthree
    }

    enum Rank
    {
        Rone,Rtwo,Rthree
    }

    public String test()
    {
        return null;
    }

    public static void main(String[] args)
    {
        /**
         * 这样是错误的示例
         */
        Collection<Suit> suits = Arrays.asList(Suit.values());
        Collection<Rank> ranks = Arrays.asList(Rank.values());

        for(Iterator<Suit> i = suits.iterator();i.hasNext();)
        {
            for(Iterator<Rank> j = ranks.iterator();j.hasNext();)
            {
                System.out.println(i.next() + "~~" + j.next());
            }
        }



        /**
         * 为了修正这些示例中的Bug,必须在外部循环作用域中添加变量加以保存
         * 但是原理是什么,不太了解,之后再看??????????????????
         */

        for(Iterator<Suit> i = suits.iterator();i.hasNext();)
        {
            Suit suitName = i.next();
            for(Iterator<Rank> j = ranks.iterator();j.hasNext();)
            {
                System.out.println(suitName + "~~" + j.next());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        /**
         * 推荐使用的方法
         */
        for(Suit suit: suits)
        {
            for(Rank rank:ranks)
            {
                System.out.println(suit + "~~" + rank);
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }



}
