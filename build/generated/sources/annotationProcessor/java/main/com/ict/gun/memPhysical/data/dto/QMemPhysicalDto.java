package com.ict.gun.memPhysical.data.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemPhysicalDto is a Querydsl query type for MemPhysicalDto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemPhysicalDto extends EntityPathBase<MemPhysicalDto> {

    private static final long serialVersionUID = -1524493003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemPhysicalDto memPhysicalDto = new QMemPhysicalDto("memPhysicalDto");

    public final NumberPath<Long> memCount = createNumber("memCount", Long.class);

    public final DatePath<java.time.LocalDate> memDeleteDate = createDate("memDeleteDate", java.time.LocalDate.class);

    public final com.ict.gun.member.entity.QMember memEmail;

    public final DatePath<java.time.LocalDate> memInputDate = createDate("memInputDate", java.time.LocalDate.class);

    public final StringPath memLocation = createString("memLocation");

    public final NumberPath<Long> memPhysical = createNumber("memPhysical", Long.class);

    public final StringPath memPhysicalAct = createString("memPhysicalAct");

    public final NumberPath<Long> memPhysicalSet = createNumber("memPhysicalSet", Long.class);

    public final StringPath memPoint = createString("memPoint");

    public QMemPhysicalDto(String variable) {
        this(MemPhysicalDto.class, forVariable(variable), INITS);
    }

    public QMemPhysicalDto(Path<? extends MemPhysicalDto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemPhysicalDto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemPhysicalDto(PathMetadata metadata, PathInits inits) {
        this(MemPhysicalDto.class, metadata, inits);
    }

    public QMemPhysicalDto(Class<? extends MemPhysicalDto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memEmail = inits.isInitialized("memEmail") ? new com.ict.gun.member.entity.QMember(forProperty("memEmail")) : null;
    }

}

