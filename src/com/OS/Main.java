package com.OS;

import java.util.ArrayList;

public class Main {



    public static void main(String[] args) throws InterruptedException
    {

        final PC pc = new PC();


        /* THREAD 1 */
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });




        /* THREAD 2 */
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        producer.start();
        consumer.start();


        producer.join();
        consumer.join();


    }



    /* PRODUCE CONSUMER CLASS DEFINITION */

    private static class PC
    {

        //this class will have the definitions for the produce method and the consume method


        //container with 5 slots open
        ArrayList<Integer> container = new ArrayList<>(2);
        int capacity = 2;
        int value = 0;


        /* PRODUCE METHOD */
        public void produce() throws InterruptedException {
            while (true)
            {
                synchronized (this)
                {
                 /*  synchronized link to the container  */

                 /* check if the container is full */
                 while (!container.isEmpty())
                     wait();


                    System.out.println();
                 /* if the container is not full */
                    while (!(container.size() == capacity)) {
                        System.out.println("Produced: " + value);
                        container.add(value++);
                    }


                    System.out.println();
                    /* lets notify next thread to wake up */
                    notify();

                    /* sleeep */
                    Thread.sleep(1000);




                }
            }

        }//end of produce

        /* CONSUME METHOD */
        public void consume() throws InterruptedException
        {

            while (true)
            {

                synchronized (this)
                {
                    /* check if the container is FULL */
                    while (container.isEmpty())
                        wait();


                    /* if its full, consume */
                    //System.out.println(container.size());
                    //System.out.println(container.get(0));

                    int consumed = container.remove(0);
                    System.out.println("Consumed: " + consumed);


                    notify();

                    Thread.sleep(1000);


                }
            }

        }

    }
}
