package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.QRef;
import com.ict.gun.ref.data.entity.QRefEx;
import com.ict.gun.ref.data.entity.QRefPhoto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class RefRepositoryCustomImpl implements RefRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QRef ref = QRef.ref;
    QRefEx refEx = QRefEx.refEx;
    QRefPhoto refPhoto = QRefPhoto.refPhoto;
    @Override
    public List<RefDto> findByMyRefList(String email) {

        JPAQuery<Tuple> query = queryFactory
                .select(
                        ref.REF_CODE,
                        refPhoto.REF_PHOTO,
                        refPhoto.REF_SAVE_DATE,
                        refPhoto.MEM_EMAIL,
                        ref.REF_EXCODE,
                        refEx.REF_EXNAME,
                        refEx.REF_EXDATE,
                        ref.REF_QUAN,
                        ref.REF_END_DATE
                )
                .from(ref)
                .join(refEx).on(ref.REF_EXCODE.eq(refEx.REF_EXCODE))
                .join(refPhoto).on(ref.REF_CODE.eq(refPhoto.REF_CODE))
                .where(refPhoto.MEM_EMAIL.eq(email))
                .orderBy(refPhoto.REF_SAVE_DATE.desc());

        return query.fetchJoin().fetch().stream().map(tupple -> RefDto.builder()
                .REF_CODE(tupple.get(ref.REF_CODE))
                .REF_PHOTO(tupple.get(refPhoto.REF_PHOTO))
                .REF_SAVE_DATE(tupple.get(refPhoto.REF_SAVE_DATE))
                .MEM_EMAIL(tupple.get(refPhoto.MEM_EMAIL))
                .REF_EXCODE(tupple.get(ref.REF_EXCODE))
                .REF_EXNAME(tupple.get(refEx.REF_EXNAME))
                .REF_EXDATE(tupple.get(refEx.REF_EXDATE))
                .REF_QUAN(tupple.get(ref.REF_QUAN))
                .REF_END_DATE(tupple.get(ref.REF_END_DATE))
                .build()).toList();
    }


    @Override
    public int findByRefExCode(String refExCode) {

        Integer query = queryFactory
                .select(refEx.REF_EXDATE)
                .from(refEx)
                .where(refEx.REF_EXCODE.eq(refExCode))
                .fetchOne();

        if(query == null) {
            return 0;
        } else {
            return query;
        }
    }

    @Override
    public String findRefCode(){
        String refCode = queryFactory
                .select(refPhoto.REF_CODE)
                .from(refPhoto)
                .orderBy(refPhoto.REF_SAVE_DATE.desc())
                .fetchFirst();
        return refCode;
    }
    @Override
    @Transactional
    public void deleteAllByREF_CODE(String REF_CODE){
        queryFactory
                .delete(ref)
                .where(ref.REF_CODE.eq(REF_CODE))
                .execute();
    }

    @Override
    public long findByRefCode(String refCode) {
        return queryFactory
                .selectFrom(ref)
                .where(ref.REF_CODE.eq(refCode))
                .fetch().size();
    }

    @Override
    public long findPhotoByRefCode(String refCode) {
        return queryFactory
                .selectFrom(refPhoto)
                .where(refPhoto.REF_CODE.eq(refCode))
                .fetch().size();
    }
}
