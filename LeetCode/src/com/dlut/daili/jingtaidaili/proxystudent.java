package com.dlut.daili.jingtaidaili;

public class proxystudent implements person
{
    student student;

    proxystudent(person student)
    {
        if(student.getClass() == student.class)
        {
            this.student = (student)student;
        }
    }

    @Override
    public void giveMoney()
    {
        System.out.println("静态代理小王");
        System.out.println("小王given Money!!!");
    }
}
