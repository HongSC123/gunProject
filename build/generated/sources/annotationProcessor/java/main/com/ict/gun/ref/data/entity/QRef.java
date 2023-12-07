package com.ict.gun.ref.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRef is a Querydsl query type for Ref
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRef extends EntityPathBase<Ref> {

    private static final long serialVersionUID = -416343710L;

    public static final QRef ref = new QRef("ref");

    public final StringPath REF_CODE = createString("REF_CODE");

    public final DateTimePath<java.util.Date> REF_END_DATE = createDateTime("REF_END_DATE", java.util.Date.class);

    public final StringPath REF_EXCODE = createString("REF_EXCODE");

    public final NumberPath<Integer> REF_QUAN = createNumber("REF_QUAN", Integer.class);

    public QRef(String variable) {
        super(Ref.class, forVariable(variable));
    }

    public QRef(Path<? extends Ref> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRef(PathMetadata metadata) {
        super(Ref.class, metadata);
    }

}

