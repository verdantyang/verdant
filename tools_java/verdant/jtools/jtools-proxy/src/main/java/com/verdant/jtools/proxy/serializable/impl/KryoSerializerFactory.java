package com.verdant.jtools.proxy.serializable.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import de.javakaffee.kryoserializers.*;
import de.javakaffee.kryoserializers.cglib.CGLibProxySerializer;
import de.javakaffee.kryoserializers.guava.ImmutableListSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableMultimapSerializer;
import de.javakaffee.kryoserializers.guava.ImmutableSetSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.reflect.InvocationHandler;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class KryoSerializerFactory {
    public static final int MAX_DEPTH = 1024;
    private static KryoPool pool;

    static {
        initialize();
    }

    public static void initialize() {
        pool = new KryoPool.Builder(new KryoFactory() {
            @Override
            public Kryo create() {
                return createKryo(MAX_DEPTH);
            }
        }).softReferences().build();
    }

    public static Kryo createKryo() {
        return createKryo(MAX_DEPTH);
    }

    public static Kryo createKryo(int maxDepth) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setAsmEnabled(true);
        kryo.setMaxDepth(maxDepth);
        kryo.setAutoReset(true);
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
        kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
        kryo.register(InvocationHandler.class, new JdkProxySerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);

        // custom serializers for non-jdk libs
        // register CGLibProxySerializer, works in combination with the appropriate action in handleUnregisteredClass (see below)
        kryo.register(CGLibProxySerializer.CGLibProxyMarker.class, new CGLibProxySerializer());
        // joda DateTime, LocalDate and LocalDateTime
//        kryo.register(DateTime.class, new JodaDateTimeSerializer());
//        kryo.register(LocalDate.class, new JodaLocalDateSerializer());
//        kryo.register(LocalDateTime.class, new JodaLocalDateTimeSerializer());
        // protobuf
//        kryo.register(SampleProtoA.class, new ProtobufSerializer()); // or override Kryo.getDefaultSerializer as shown below
        // wicket
//        kryo.register(MiniMap.class, new MiniMapSerializer());
        // guava ImmutableList, ImmutableSet, ImmutableMap, ImmutableMultimap
        ImmutableListSerializer.registerSerializers(kryo);
        ImmutableSetSerializer.registerSerializers(kryo);
        ImmutableMapSerializer.registerSerializers(kryo);
        ImmutableMultimapSerializer.registerSerializers(kryo);
        return kryo;
    }

    public static KryoPool getDefaultPool() {
        return pool;
    }
}