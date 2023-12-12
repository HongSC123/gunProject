package com.ict.gun.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 29767324L;

    public static final QChat chat = new QChat("chat");

    public final StringPath chat_content = createString("chat_content");

    public final DateTimePath<java.time.LocalDateTime> chat_date = createDateTime("chat_date", java.time.LocalDateTime.class);

    public final StringPath chat_fix = createString("chat_fix");

    public final NumberPath<Integer> chat_num = createNumber("chat_num", Integer.class);

    public final StringPath chat_title = createString("chat_title");

    public final StringPath mem_email = createString("mem_email");

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}

