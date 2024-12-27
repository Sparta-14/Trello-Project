package com.spata14.trelloproject.workspace.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spata14.trelloproject.user.QUser.user;
import static com.spata14.trelloproject.workspace.QWorkspace.workspace;
import static com.spata14.trelloproject.workspace.QWorkspaceUser.workspaceUser;


@Repository
@RequiredArgsConstructor
public class WorkspaceUserCustomRepositoryImpl implements WorkspaceUserCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean isMember(Long workspaceId, String userEmail) {

        BooleanExpression condition = workspaceUser.workspace.id.eq(workspaceId)
                .and(workspaceUser.user.email.eq(userEmail));

        return jpaQueryFactory
                .selectOne()
                .from(workspaceUser)
                .where(condition)
                .fetchFirst() != null; //결과가 있으면 true, 없으면 false
    }


    @Override
    public List<WorkspaceResponseDto> findAllWorkspaceByUser(String userEmail) {
        BooleanExpression condition = user.email.eq(userEmail);

        return jpaQueryFactory
                .select(Projections.constructor(
                        WorkspaceResponseDto.class,
                        workspace.id,
                        workspace.name,
                        workspace.description,
                        workspace.createdAt,
                        workspace.modifiedAt
                ))
                .from(workspaceUser)
                .join(workspaceUser.user, user)
                .join(workspaceUser.workspace, workspace)
                .where(condition)
                .fetch();
    }
}
