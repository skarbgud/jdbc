package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositroyV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositroyV1 memberRepositroy;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
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
