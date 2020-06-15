package com.verdant.demo.common.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

/**
 * <p>文件名称：ActorUsingPipeTo.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-04-02</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class ActorUsingPipeTo extends AbstractActor {
    ActorRef target;
    Duration timeout;

    ActorUsingPipeTo(ActorRef target) {
        this.target = target;
        this.timeout = Duration.ofSeconds(5);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        String.class,
                        msg -> {
                            CompletableFuture<Object> fut =
                                    ask(target, "some message", timeout).toCompletableFuture();

                            // the pipe pattern
                            pipe(fut, getContext().dispatcher()).to(getSender());
                        })
                .build();
    }
}
