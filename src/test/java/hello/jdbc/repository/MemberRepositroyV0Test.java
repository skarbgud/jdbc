package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositroyV0Test {

    MemberRepositroyV0 repositroy = new MemberRepositroyV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repositroy.save(member);
    }
}