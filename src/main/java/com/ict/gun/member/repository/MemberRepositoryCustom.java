package com.ict.gun.member.repository;

import com.ict.gun.member.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<MemberCommand> getMembers() {
        QMember qMember = QMember.member;
        QMemberOptions qMemberOptions = QMemberOptions.memberOptions;
        BooleanExpression condition = qMember.memType.ne("ADMIN");

        List<Tuple> tuples = queryFactory
                .select(
                        qMember.memEmail,
                        qMember.memPw,
                        qMember.memType,
                        qMember.memPhoto,
                        qMember.memEn,
                        qMember.memMod,
                        qMember.memQuit,
                        qMember.memAct,
                        qMember.memAka,
                        qMember.role,
                        qMemberOptions.memGen,
                        qMemberOptions.memWeight,
                        qMemberOptions.memHeight,
                        qMemberOptions.memBir,
                        qMemberOptions.memActLevel,
                        qMemberOptions.mem_reco_daily_calories
                )
                .from(qMember)
                .leftJoin(qMemberOptions).on(qMember.memEmail.eq(qMemberOptions.memEmail))
                .where(condition)
                .fetch();

        return tuples.stream().map(tuple -> {
            MemberCommand memberCommand = new MemberCommand();
            memberCommand.setMemEmail(tuple.get(qMember.memEmail));
            memberCommand.setMemPw(tuple.get(qMember.memPw));
            memberCommand.setMemType(tuple.get(qMember.memType));
            memberCommand.setMemPhoto(tuple.get(qMember.memPhoto));
            memberCommand.setMemEn(tuple.get(qMember.memEn));
            memberCommand.setMemMod(tuple.get(qMember.memMod));
            memberCommand.setMemQuit(tuple.get(qMember.memQuit));
            memberCommand.setMemAct(tuple.get(qMember.memAct));
            memberCommand.setMemAka(tuple.get(qMember.memAka));
            memberCommand.setRole(tuple.get(qMember.role));
            memberCommand.setMemGen(tuple.get(qMemberOptions.memGen) != null ? tuple.get(qMemberOptions.memGen) : "0");  // null 체크 및 기본값 설정
            memberCommand.setMemWeight(tuple.get(qMemberOptions.memWeight) != null ? tuple.get(qMemberOptions.memWeight).floatValue() : 0.0f);  // null 체크 및 기본값 설정
            memberCommand.setMemHeight(tuple.get(qMemberOptions.memHeight) != null ? tuple.get(qMemberOptions.memHeight).floatValue() : 0.0f);  // null 체크 및 기본값 설정
            memberCommand.setMemBir(tuple.get(qMemberOptions.memBir));
            memberCommand.setMemActLevel(tuple.get(qMemberOptions.memActLevel) != null ? tuple.get(qMemberOptions.memActLevel).floatValue() : 0.0f);  // null 체크 및 기본값 설정
            memberCommand.setMem_reco_daily_calories(tuple.get(qMemberOptions.mem_reco_daily_calories) != null ? tuple.get(qMemberOptions.mem_reco_daily_calories) : 0);  // null 체크 및 기본값 설정

            return memberCommand;
        }).collect(Collectors.toList());
    }
}
