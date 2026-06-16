package storage;

import domain.Member;

import java.sql.SQLException;
import java.util.List;

public class MemberStorage {

    private MemberDao memberDao;

    public MemberStorage(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    public void saveAll(List<Member> members) throws SQLException {
        memberDao.saveAll(members);
    }

    public List<Member> loadAll() throws SQLException {
        return memberDao.loadAll();
    }
}
