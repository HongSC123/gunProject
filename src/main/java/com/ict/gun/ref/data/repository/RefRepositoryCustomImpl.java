package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.QRef;
import com.ict.gun.ref.data.entity.QRefPhoto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RefRepositoryCustomImpl implements RefRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QRef ref = QRef.ref;
    QRefPhoto refPhoto = QRefPhoto.refPhoto;
    @Override
    public List<RefDto> findByMyRefList(String email) {

        JPAQuery<Tuple> query = queryFactory
                .select(
                        ref.REF_CODE,
                        refPhoto.REF_PHOTO,
                        refPhoto.REF_SAVE_DATE,
                        refPhoto.MEM_EMAIL,
                        ref.REF_NAME,
                        ref.REF_QUAN,
                        ref.REF_END_DATE,
                        ref.REF_NUM
                )
                .from(ref)
                .join(refPhoto).on(ref.REF_CODE.eq(refPhoto.REF_CODE))
                .where(refPhoto.MEM_EMAIL.eq(email))
                .orderBy(refPhoto.REF_SAVE_DATE.desc());

        return query.fetchJoin().fetch().stream().map(tupple -> RefDto.builder()
                .REF_CODE(tupple.get(ref.REF_CODE))
                .REF_PHOTO(tupple.get(refPhoto.REF_PHOTO))
                .REF_NAME(tupple.get(ref.REF_NAME))
                .REF_SAVE_DATE(tupple.get(refPhoto.REF_SAVE_DATE))
                .MEM_EMAIL(tupple.get(refPhoto.MEM_EMAIL))
                .REF_QUAN(tupple.get(ref.REF_QUAN))
                .REF_END_DATE(tupple.get(ref.REF_END_DATE))
                .REF_NUM(tupple.get(ref.REF_NUM))
                .build()).toList();
    }


    @Override
    public Long findRefCode(){
        Long refCode = Long.valueOf(queryFactory
                .select(refPhoto.REF_CODE)
                .from(refPhoto)
                .orderBy(refPhoto.REF_SAVE_DATE.desc())
                .fetchFirst());
        return refCode;
    }
    @Override
    @Transactional
    public void deleteAllByREF_CODE(Long REF_CODE){
        queryFactory
                .delete(ref)
                .where(ref.REF_CODE.eq(REF_CODE))
                .execute();
    }

    @Override
    public long findByRefCode(Long refCode) {
        return queryFactory
                .selectFrom(ref)
                .where(ref.REF_CODE.eq(refCode))
                .fetch().size();
    }

    @Override
    public long findPhotoByRefCode(Long refCode) {
        return queryFactory
                .selectFrom(refPhoto)
                .where(refPhoto.REF_CODE.eq(refCode))
                .fetch().size();
    }

    @Override
    public RefDto findBydetail(Long refNum) {
        JPAQuery<Tuple> query = queryFactory
                .select(
                        ref.REF_CODE,
                        refPhoto.REF_PHOTO,
                        refPhoto.REF_SAVE_DATE,
                        refPhoto.MEM_EMAIL,
                        ref.REF_NAME,
                        ref.REF_QUAN,
                        ref.REF_END_DATE,
                        ref.REF_NUM
                )
                .from(ref)
                .join(refPhoto).on(ref.REF_CODE.eq(refPhoto.REF_CODE))
                .where(ref.REF_NUM.eq(refNum));

        return query.fetchJoin().fetch().stream().map(tupple -> RefDto.builder()
                .REF_CODE(tupple.get(ref.REF_CODE))
                .REF_PHOTO(tupple.get(refPhoto.REF_PHOTO))
                .REF_NAME(tupple.get(ref.REF_NAME))
                .REF_SAVE_DATE(tupple.get(refPhoto.REF_SAVE_DATE))
                .MEM_EMAIL(tupple.get(refPhoto.MEM_EMAIL))
                .REF_QUAN(tupple.get(ref.REF_QUAN))
                .REF_END_DATE(tupple.get(ref.REF_END_DATE))
                .REF_NUM(tupple.get(ref.REF_NUM))
                .build()).toList().get(0);
    }

    @Override
    public Long findByRefNum(Long refNum) {
        return queryFactory
                .select(ref.REF_CODE)
                .from(ref)
                .where(ref.REF_NUM.eq(refNum))
                .fetchOne();
    }
}
