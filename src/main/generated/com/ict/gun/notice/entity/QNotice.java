package com.ict.gun.notice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -554031908L;

    public static final QNotice notice = new QNotice("notice");

    public final StringPath adminId = createString("adminId");

    public final StringPath noticeCon = createString("noticeCon");

    public final NumberPath<Integer> noticeCount = createNumber("noticeCount", Integer.class);

    public final DatePath<java.sql.Date> noticeDelDate = createDate("noticeDelDate", java.sql.Date.class);

    public final StringPath noticeFile = createString("noticeFile");

    public final DatePath<java.sql.Date> noticeInputDate = createDate("noticeInputDate", java.sql.Date.class);

    public final DatePath<java.sql.Date> noticeModDate = createDate("noticeModDate", java.sql.Date.class);

    public final NumberPath<Long> noticeNum = createNumber("noticeNum", Long.class);

    public final StringPath noticeTitle = createString("noticeTitle");

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

