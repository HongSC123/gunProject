package com.ict.gun.memPhysical.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemPhysical is a Querydsl query type for MemPhysical
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemPhysical extends EntityPathBase<MemPhysical> {

    private static final long serialVersionUID = -125142828L;

    public static final QMemPhysical memPhysical1 = new QMemPhysical("memPhysical1");

    public final NumberPath<Long> memCount = createNumber("memCount", Long.class);

    public final DatePath<java.sql.Date> memDeleteDate = createDate("memDeleteDate", java.sql.Date.class);

    public final StringPath memEmail = createString("memEmail");

    public final DatePath<java.sql.Date> memInputDate = createDate("memInputDate", java.sql.Date.class);

    public final StringPath memLocation = createString("memLocation");

    public final NumberPath<Long> memPhysical = createNumber("memPhysical", Long.class);

    public final StringPath memPhysicalAct = createString("memPhysicalAct");

    public final NumberPath<Long> memPhysicalSet = createNumber("memPhysicalSet", Long.class);

    public final StringPath memPoint = createString("memPoint");

    public QMemPhysical(String variable) {
        super(MemPhysical.class, forVariable(variable));
    }

    public QMemPhysical(Path<? extends MemPhysical> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemPhysical(PathMetadata metadata) {
        super(MemPhysical.class, metadata);
    }

}

