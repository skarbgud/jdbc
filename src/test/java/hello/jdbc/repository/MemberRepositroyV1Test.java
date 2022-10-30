package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

class MemberRepositroyV1Test {

    MemberRepositroyV1 repositroy;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션을 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 폴링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPoolName(PASSWORD);

        repositroy = new MemberRepositroyV1(dataSource);
    }

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


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}