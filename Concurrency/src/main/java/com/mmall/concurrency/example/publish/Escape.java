package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annoations.NotRecommend;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
@NotRecommend
/**
 * 对象逸出
 */
public class Escape {

    private int thisCanBeEscape = 0;

    /**
     * 对象的构造方式还没执行完毕  就调用了内部类的构造方法,内部类的构造方法里使用了thisCanBeEscape,造成了thisCanBeEscape逸出
     * 对象创建期间,this引用逸出
     */
    public Escape () {
        new InnerClass();
    }

    private class InnerClass {

        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
