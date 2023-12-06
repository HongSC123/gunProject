package com.ict.gun.ref.data.repository;

import com.ict.gun.ref.data.dto.RefDto;
import com.ict.gun.ref.data.entity.QRef;
import com.ict.gun.ref.data.entity.QRefEx;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RefRepositoryCustomImpl implements RefRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<RefDto> findByMyRefList(String email) {
        QRef ref = QRef.ref;
        QRefEx refEx = QRefEx.refEx;

        JPAQuery<Tuple> query = queryFactory
                .select(ref.REF_CODE,
                        ref.REF_QUAN,
                        ref.REF_SAVE_DATE,
                        ref.MEM_EMAIL,
                        refEx.REF_EXCODE,
                        refEx.REF_EXNAME,
                        refEx.REF_EXDATE)
                .from(ref)
                .join(refEx).on(ref.REF_EXCODE.eq(refEx.REF_EXCODE))
                .where(ref.MEM_EMAIL.eq(email))
                .orderBy(ref.REF_SAVE_DATE.desc());

        return query.fetchJoin().fetch().stream().map(tupple -> RefDto.builder()
                .REF_CODE(tupple.get(ref.REF_CODE))
                .REF_QUAN(tupple.get(ref.REF_QUAN))
                .REF_SAVE_DATE(tupple.get(ref.REF_SAVE_DATE))
                .MEM_EMAIL(tupple.get(ref.MEM_EMAIL))
                .REF_EXCODE(tupple.get(refEx.REF_EXCODE))
                .REF_EXNAME(tupple.get(refEx.REF_EXNAME))
                .REF_EXDATE(tupple.get(refEx.REF_EXDATE))
        .build()).toList();
    }
}
