package com.bestboke.nettyserver.learn4;


import com.bestboke.nettycommons.nettypacket.Command;
import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.bestboke.nettyserver.learn4.messagehandler.ChannelRead;
import com.bestboke.nettyserver.learn4.messagehandler.LoginRequestBiz;
import com.bestboke.nettyserver.learn4.messagehandler.MessageRequestBiz;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static Map<Byte, Class<? extends ChannelRead>> packetTypeMap;

    static {
        packetTypeMap = new ConcurrentHashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestBiz.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestBiz.class);
    }


    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        Class<? extends ChannelRead> channelRead = getRequestType(packet.getCommand());
        try {
            channelRead.newInstance().doChannelRead(ctx, packet);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    private Class<? extends ChannelRead> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }


}
