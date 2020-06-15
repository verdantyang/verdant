package com.verdant.demo.common.akka.future;

import akka.actor.AbstractActor;

/**
 * <p>文件名称：UserDataActor.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-04-02</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class UserDataActor extends AbstractActor {
    UserData internalData;

    UserDataActor() {
        this.internalData = new UserData("initial data");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(GetFromUserDataActor.class, msg -> sender().tell(internalData, self()))
                .build();
    }

    public class GetFromUserDataActor {
    }
}
