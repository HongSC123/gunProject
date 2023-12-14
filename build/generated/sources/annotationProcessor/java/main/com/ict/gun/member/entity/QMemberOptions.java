package com.ict.gun.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberOptions is a Querydsl query type for MemberOptions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberOptions extends EntityPathBase<MemberOptions> {

    private static final long serialVersionUID = 1268453790L;

    public static final QMemberOptions memberOptions = new QMemberOptions("memberOptions");

    public final NumberPath<Float> memActLevel = createNumber("memActLevel", Float.class);

    public final DatePath<java.sql.Date> memBir = createDate("memBir", java.sql.Date.class);

    public final StringPath memEmail = createString("memEmail");

    public final StringPath memGen = createString("memGen");

    public final NumberPath<Float> memHeight = createNumber("memHeight", Float.class);

    public final NumberPath<Float> memWeight = createNumber("memWeight", Float.class);

    public QMemberOptions(String variable) {
        super(MemberOptions.class, forVariable(variable));
    }

    public QMemberOptions(Path<? extends MemberOptions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberOptions(PathMetadata metadata) {
        super(MemberOptions.class, metadata);
    }

}

