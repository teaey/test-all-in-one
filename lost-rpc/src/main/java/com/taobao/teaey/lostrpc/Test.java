package com.taobao.teaey.lostrpc;

import com.google.protobuf.InvalidProtocolBufferException;


public class Test {

    public static void main(String[] args) throws InvalidProtocolBufferException, InterruptedException {
        System.out.println("来自服务器的响应:");
//        LostProto.Login_C2S.Builder b = LostProto.Login_C2S.newBuilder();
//        b.setTimestamp(System.nanoTime());
//        System.out.println(b.build());
//        LostProto.MsgFields msgFields = LostProto.MsgFields.parseFrom(b.build().toByteArray());
//        System.out.println(msgFields);

        final LostRpcCallback<LostProto.Login_S2C> cb = new LostRpcCallback<LostProto.Login_S2C>(null) {
            @Override
            public void process(LostProto.Login_S2C parameter) {
                System.out.println("process");
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cb.done();
            }
        }).start();


        System.out.println(cb.await());
        System.out.println("after await");

        System.out.println(cb.get());
    }
}
