package eightseconds.domain.myfile.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import eightseconds.domain.myfile.entity.MyFile;

import javax.persistence.EntityManager;
import java.util.Optional;

import static eightseconds.domain.myfile.entity.QMyFile.myFile;


public class MyFileRepositoryImpl implements MyFileRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MyFileRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<MyFile> findOneNotDeletedByFileId(Long fileId) {
        return Optional.ofNullable(queryFactory.selectFrom(myFile)
                .from(myFile)
                .where(myFileIdEq(fileId),
                 isDeletedCheck())
                .fetchFirst());
    }

    private BooleanExpression isDeletedCheck() {
        return myFile.isDeleted.eq(false);
    }

    private BooleanExpression myFileIdEq(Long fileId) {
        return fileId != null ? myFile.myFileId.eq(fileId) : null;
    }
}
