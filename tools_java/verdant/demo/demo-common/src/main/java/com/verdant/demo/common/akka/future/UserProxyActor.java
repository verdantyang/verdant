package com.verdant.demo.common.akka.future;

/**
 * <p>文件名称：UserProxyActor.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-04-02</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

public class UserProxyActor extends AbstractActor {
    ActorRef userActor;
    ActorRef userActivityActor;
    Duration timeout = Duration.ofSeconds(5);

    UserProxyActor(ActorRef userActor, ActorRef userActivityActor) {
        this.userActor = userActor;
        this.userActivityActor = userActivityActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        GetUserData.class,
                        msg -> {
                            CompletableFuture<Object> fut =
                                    ask(userActor, new GetUserData(), timeout)
                                            .toCompletableFuture();

                            pipe(fut, getContext().dispatcher());
                        })
                .match(
                        GetUserActivities.class,
                        msg -> {
                            CompletableFuture<Object> fut =
                                    ask(userActivityActor, new UserActivityActor.GetFromUserActivityActor(), timeout)
                                            .toCompletableFuture();

                            pipe(fut, getContext().dispatcher()).to(sender());
                        })
                .build();
    }
}
