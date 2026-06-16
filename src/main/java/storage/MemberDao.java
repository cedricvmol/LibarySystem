package storage;

import domain.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {

    private Connection connection;


    public MemberDao(Connection connection){
        this.connection = connection;
    }


    public void saveAll(List<Member> members) throws SQLException{
        connection.setAutoCommit(false);

        try {

            try (Statement statement = connection.createStatement();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT OR REPLACE INTO members (" +
                                                        "memberId,name,address,email,phone,password) VALUES (?,?,?,?,?,?)"))
            {
                statement.execute("DELETE FROM members");
                for (Member member : members){
                    preparedStatement.setString(1,member.getMemberId());
                    preparedStatement.setString(2,member.getName());
                    preparedStatement.setString(3,member.getAddress());
                    preparedStatement.setString(4,member.getEmail());
                    preparedStatement.setString(5,member.getPhone());
                    preparedStatement.setString(6,member.getPassword());
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Member> loadAll() throws SQLException{
        List<Member> members = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM members");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()){
                String memberId = rs.getString("memberId");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String password = rs.getString("password");

                Member member = new Member(memberId,password,name,address,email,phone);
                members.add(member);
            }
        }
        return members;
    }
}
