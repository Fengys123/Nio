package com.dlut.daili.dongtaidaili;

public class student implements person
{
    private String name;

    public student(String name)
    {
        this.name = name;
    }

    @Override
    public void givenMoney()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(name + "上交了50元班费!!!");
    }
}
