package com.ict.gun.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1424928544L;

    public static final QMember member = new QMember("member1");

    public final StringPath memAct = createString("memAct");

    public final StringPath memAka = createString("memAka");

    public final StringPath memEmail = createString("memEmail");

    public final DatePath<java.sql.Date> memEn = createDate("memEn", java.sql.Date.class);

    public final DatePath<java.sql.Date> memMod = createDate("memMod", java.sql.Date.class);

    public final StringPath memPhoto = createString("memPhoto");

    public final StringPath memPw = createString("memPw");

    public final DatePath<java.sql.Date> memQuit = createDate("memQuit", java.sql.Date.class);

    public final StringPath memType = createString("memType");

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

