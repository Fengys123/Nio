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
         * �����Ǵ����ʾ��
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
         * Ϊ��������Щʾ���е�Bug,�������ⲿѭ������������ӱ������Ա���
         * ����ԭ����ʲô,��̫�˽�,֮���ٿ�??????????????????
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
         * �Ƽ�ʹ�õķ���
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
