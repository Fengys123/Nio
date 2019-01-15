package com.dlut;

public class ConcurrentTest
{
    /**
     * volatitle ��֤�˿ɼ���
     * ��ֹ����ָ��������
     *
     * volatile�ؼ��ֽ�ָֹ����������������˼��
     * 1��������ִ�е�volatile�����Ķ���������д����ʱ������ǰ��Ĳ����ĸ��Ŀ϶�ȫ���Ѿ����У��ҽ���Ѿ��Ժ���Ĳ����ɼ����������Ĳ����϶���û�н��У�
     * 2���ڽ���ָ���Ż�ʱ�����ܽ��ڶ�volatile�������ʵ������������ִ�У�Ҳ���ܰ�volatile������������ŵ���ǰ��ִ�С�
     * ʵ���ϣ���Щ�������������Ա�д�� volatile ��������Щ��Чֵ�������κγ����״̬�����������ĵ�ǰ״̬��
     */
    public volatile int count = 0;

    public synchronized void inc() {


        // �����ӳ�1���룬ʹ�ý������
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        count++;
        System.out.println("cout: " + count);
    }

    public static void main(String[] args) {

        // ͬʱ����1000���̣߳�ȥ����i++���㣬����ʵ�ʽ��
        final ConcurrentTest counter = new ConcurrentTest();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }

        //��֤ǰ����̶߳�ִ����
        while(Thread.activeCount()>2)
        {
            //System.out.println(Thread.activeCount());
            //System.out.println("123");
            //Thread.yield();
        }

        // ����ÿ�����е�ֵ���п��ܲ�ͬ,����Ϊ1000
        System.out.println("���н��:Counter.count=" + counter.count);
    }
}
