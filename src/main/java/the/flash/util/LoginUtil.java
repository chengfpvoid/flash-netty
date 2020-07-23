package the.flash.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import the.flash.attribute.Attributes;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 是否登录过了，有标志位 就表示登录过
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
