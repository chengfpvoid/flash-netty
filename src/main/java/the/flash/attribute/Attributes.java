package the.flash.attribute;

import io.netty.util.AttributeKey;

public interface Attributes {
    /**
     * 给客户端登录一个标志位 标识是否登录成功
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
