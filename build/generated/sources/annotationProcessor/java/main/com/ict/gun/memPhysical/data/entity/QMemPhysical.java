package com.ict.gun.memPhysical.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemPhysical is a Querydsl query type for MemPhysical
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemPhysical extends EntityPathBase<MemPhysical> {

    private static final long serialVersionUID = -125142828L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemPhysical memPhysical1 = new QMemPhysical("memPhysical1");

    public final NumberPath<Long> memCount = createNumber("memCount", Long.class);

    public final DatePath<java.time.LocalDate> memDeleteDate = createDate("memDeleteDate", java.time.LocalDate.class);

    public final com.ict.gun.member.entity.QMember memEmail;

    public final DatePath<java.time.LocalDate> memInputDate = createDate("memInputDate", java.time.LocalDate.class);

    public final StringPath memLocation = createString("memLocation");

    public final NumberPath<Long> memPhysical = createNumber("memPhysical", Long.class);

    public final StringPath memPhysicalAct = createString("memPhysicalAct");

    public final NumberPath<Long> memPhysicalSet = createNumber("memPhysicalSet", Long.class);

    public final StringPath memPoint = createString("memPoint");

    public QMemPhysical(String variable) {
        this(MemPhysical.class, forVariable(variable), INITS);
    }

    public QMemPhysical(Path<? extends MemPhysical> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemPhysical(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemPhysical(PathMetadata metadata, PathInits inits) {
        this(MemPhysical.class, metadata, inits);
    }

    public QMemPhysical(Class<? extends MemPhysical> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memEmail = inits.isInitialized("memEmail") ? new com.ict.gun.member.entity.QMember(forProperty("memEmail")) : null;
    }

}

