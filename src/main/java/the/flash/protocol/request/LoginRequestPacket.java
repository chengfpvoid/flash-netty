package the.flash.protocol.request;

import lombok.Data;
import the.flash.protocol.Packet;

import static the.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * 登陆包
 * 通信协议包括
 * 4byte魔数  1byte 版本号  序列化算法 1 byte  指令 1byte 数据长度 4byte  数据N字节
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
