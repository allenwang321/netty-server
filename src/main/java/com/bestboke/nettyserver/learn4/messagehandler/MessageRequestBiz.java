package com.bestboke.nettyserver.learn4.messagehandler;

import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.MessageRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.MessageResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import com.betboke.utils.TimeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class MessageRequestBiz implements ChannelRead {
    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {

        MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;

        System.out.println(TimeUtils.getNow() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复[" + messageRequestPacket.getMessage() + "]");
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);

    }
}
