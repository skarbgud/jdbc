package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositroyV0Test {

    MemberRepositroyV0 repositroy = new MemberRepositroyV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV2", 10000);
        repositroy.save(member);

        //findById
        Member findMember = repositroy.findById(member.getMemberId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}