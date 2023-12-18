package com.ict.gun.calorie.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIngestDiet is a Querydsl query type for IngestDiet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIngestDiet extends EntityPathBase<IngestDiet> {

    private static final long serialVersionUID = 799726991L;

    public static final QIngestDiet ingestDiet = new QIngestDiet("ingestDiet");

    public final NumberPath<Long> diet_num = createNumber("diet_num", Long.class);

    public final StringPath food_image = createString("food_image");

    public final NumberPath<Integer> food_quan = createNumber("food_quan", Integer.class);

    public final NumberPath<Long> ingest_calorie = createNumber("ingest_calorie", Long.class);

    public final StringPath mem_email = createString("mem_email");

    public final StringPath nutrition_num = createString("nutrition_num");

    public final StringPath registration_date_hm = createString("registration_date_hm");

    public final StringPath registration_date_ymd = createString("registration_date_ymd");

    public QIngestDiet(String variable) {
        super(IngestDiet.class, forVariable(variable));
    }

    public QIngestDiet(Path<? extends IngestDiet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIngestDiet(PathMetadata metadata) {
        super(IngestDiet.class, metadata);
    }

}

