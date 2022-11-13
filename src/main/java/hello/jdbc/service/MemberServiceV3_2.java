
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositroyV1;
import hello.jdbc.repository.MemberRepositroyV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {

//    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate txTemplate;
    private final MemberRepositroyV3 memberRepositroy;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositroyV3 memberRepositroy) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepositroy = memberRepositroy;
    }

    /**
     * 트랜잭션 템플릿 덕분에 트랜잭션이 시작하고, 커밋하거나 롤백하는 코드가 모두 제거되었다.
     * 트랜잭션 템플릿의 기본 동작은 다음과 같다
     *  * 비즈니스 로직이 정상 수행 되면 커밋한다.
     *  * 언체크 예외가 발생하면 롤백한다. 그외의 경우 커밋한다.(체크 예외의 경우에는 커밋하는데, 이 부분은 뒤에서 설명한다.)
     *  코드에서 예외를 넘기기 위해 Try~catch가 들어갔는데, 'bizLogic()' 메서드를 호출하면 SQLException 체크 예외를 넘겨준다.
     *  해당 람다에서 체크 예외를 밖으로 던질 수 없기 때문에 언체크 예외로 바꾸어 던지도록 예외를 전환했다.
     */

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        txTemplate.executeWithoutResult((status) -> {
            // 비즈니스 로직
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
    }


    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositroy.findById(fromId);
        Member toMember = memberRepositroy.findById(toId);

        memberRepositroy.update(fromId, fromMember.getMoney() - money);

        validation (toMember);

        memberRepositroy.update(toId, fromMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
