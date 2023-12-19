package com.ict.gun.ref.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefPhoto is a Querydsl query type for RefPhoto
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefPhoto extends EntityPathBase<RefPhoto> {

    private static final long serialVersionUID = -1825848848L;

    public static final QRefPhoto refPhoto = new QRefPhoto("refPhoto");

    public final StringPath MEM_EMAIL = createString("MEM_EMAIL");

    public final NumberPath<Long> REF_CODE = createNumber("REF_CODE", Long.class);

    public final StringPath REF_PHOTO = createString("REF_PHOTO");

    public final DateTimePath<java.util.Date> REF_SAVE_DATE = createDateTime("REF_SAVE_DATE", java.util.Date.class);

    public QRefPhoto(String variable) {
        super(RefPhoto.class, forVariable(variable));
    }

    public QRefPhoto(Path<? extends RefPhoto> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefPhoto(PathMetadata metadata) {
        super(RefPhoto.class, metadata);
    }

}

