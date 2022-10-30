package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositroyV0Test {

    MemberRepositroyV0 repositroy = new MemberRepositroyV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV6", 10000);
        repositroy.save(member);

        //findById
        Member findMember = repositroy.findById(member.getMemberId());
        Assertions.assertThat(findMember).isEqualTo(member);

        //update: money: 10000 -> 20000
        repositroy.update(member.getMemberId(), 20000);
        Member updateMember = repositroy.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repositroy.delete(member.getMemberId());

        Assertions.assertThatThrownBy(() -> repositroy.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}