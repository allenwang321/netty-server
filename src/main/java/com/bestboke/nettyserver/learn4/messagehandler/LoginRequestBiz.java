package com.bestboke.nettyserver.learn4.messagehandler;

import com.bestboke.nettycommons.nettypacket.Packet;
import com.bestboke.nettycommons.nettypacket.request.LoginRequestPacket;
import com.bestboke.nettycommons.nettypacket.response.LoginResponsePacket;
import com.bestboke.nettycommons.nettyserializer.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class LoginRequestBiz implements ChannelRead {

    @Override
    public void doChannelRead(ChannelHandlerContext ctx, Packet packet) {
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
        }

        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);

    }

    private Boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
