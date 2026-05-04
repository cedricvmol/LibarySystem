package domain;

public class Member {

    private String memberId;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String password;

    public Member(String memberId,String password, String name, String address, String email, String phone) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() { return password; }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
