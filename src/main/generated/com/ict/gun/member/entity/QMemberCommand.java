package com.ict.gun.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberCommand is a Querydsl query type for MemberCommand
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberCommand extends EntityPathBase<MemberCommand> {

    private static final long serialVersionUID = -826643893L;

    public static final QMemberCommand memberCommand = new QMemberCommand("memberCommand");

    public final NumberPath<Integer> mem_reco_daily_calories = createNumber("mem_reco_daily_calories", Integer.class);

    public final StringPath memAct = createString("memAct");

    public final NumberPath<Float> memActLevel = createNumber("memActLevel", Float.class);

    public final StringPath memAka = createString("memAka");

    public final DatePath<java.sql.Date> memBir = createDate("memBir", java.sql.Date.class);

    public final StringPath memEmail = createString("memEmail");

    public final DatePath<java.sql.Date> memEn = createDate("memEn", java.sql.Date.class);

    public final StringPath memGen = createString("memGen");

    public final NumberPath<Float> memHeight = createNumber("memHeight", Float.class);

    public final DatePath<java.sql.Date> memMod = createDate("memMod", java.sql.Date.class);

    public final StringPath memPhoto = createString("memPhoto");

    public final StringPath memPw = createString("memPw");

    public final DatePath<java.sql.Date> memQuit = createDate("memQuit", java.sql.Date.class);

    public final StringPath memType = createString("memType");

    public final NumberPath<Float> memWeight = createNumber("memWeight", Float.class);

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public QMemberCommand(String variable) {
        super(MemberCommand.class, forVariable(variable));
    }

    public QMemberCommand(Path<? extends MemberCommand> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberCommand(PathMetadata metadata) {
        super(MemberCommand.class, metadata);
    }

}

