
package org.bridje.sip.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import org.bridje.sip.SipResponse;

class SipResponseEncoder extends MessageToMessageEncoder<SipResponse>
{
    @Override
    protected void encode(ChannelHandlerContext ctx, SipResponse msg, List<Object> out) throws Exception
    {
        ByteBuf buff = ctx.alloc().buffer();
        buff.writeBytes(msg.getVersion().toString().getBytes(CharsetUtil.UTF_8));
        buff.writeBytes(" ".getBytes(CharsetUtil.UTF_8));
        buff.writeBytes(String.valueOf(msg.getStatusCode()).getBytes(CharsetUtil.UTF_8));
        buff.writeBytes(" ".getBytes(CharsetUtil.UTF_8));
        buff.writeBytes(msg.getMessage().getBytes(CharsetUtil.UTF_8));
        buff.writeBytes(" ".getBytes(CharsetUtil.UTF_8));
        buff.writeBytes("\n".getBytes(CharsetUtil.UTF_8));
        msg.getHeaders().forEach((k, v) ->
        {
            v.stream().forEach(val ->
            {
                buff.writeBytes(k.getBytes(CharsetUtil.UTF_8));
                buff.writeBytes(": ".getBytes(CharsetUtil.UTF_8));
                buff.writeBytes(val.getBytes(CharsetUtil.UTF_8));
                buff.writeBytes("\n".getBytes(CharsetUtil.UTF_8));
            });
        });
        buff.writeBytes("\n".getBytes(CharsetUtil.UTF_8));
        DatagramPacket packet = new DatagramPacket(buff, msg.getRecipient());
        out.add(packet);
    }
}
