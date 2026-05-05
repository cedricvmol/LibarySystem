package service;

import domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    private MemberService memberService;

    @BeforeEach
    void setup(){
        memberService = new MemberService();


    }

    @Test
    void registerMemberSuccessfully(){
        Member member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        memberService.registerMember(member);

        assertEquals(1,memberService.getAllMembers().size());
    }

    @Test
    void registerMemberDuplicateId(){
        Member member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        Member member2 = new Member("US01", "admin", "Bob Smith", "456 Oak Avenue, Shelbyville", "bob.smith@email.com", "+1-555-876-5432");
        memberService.registerMember(member);

        assertThrows(IllegalArgumentException.class,() ->{
            memberService.registerMember(member2);
        });
        Optional<Member> result = memberService.getMember("US01");

        assertTrue(result.isPresent());
        assertEquals("Alice Johnson",result.get().getName());
    }

    @Test
    void getMemberFound(){
        Member member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        memberService.registerMember(member);

        Optional<Member> result = memberService.getMember("US01");

        assertTrue(result.isPresent());
        assertEquals("Alice Johnson",result.get().getName());
    }

    @Test
    void getMemberNotFound(){

        Optional<Member> result = memberService.getMember("US01");
        assertTrue(result.isEmpty());

    }

    @Test
    void getAllMembers(){

        Member member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        Member member1 = new Member("US02", "admin", "Bob Smith", "456 Oak Avenue, Shelbyville", "bob.smith@email.com", "+1-555-876-5432");
        Member member2 = new Member("US03", "admin", "Carlos Rivera", "789 Pine Road, Capital City", "carlos.rivera@email.com", "+34-612-345-678");

        memberService.registerMember(member);
        memberService.registerMember(member1);
        memberService.registerMember(member2);

        assertEquals(3,memberService.getAllMembers().size());
        assertTrue(memberService.getAllMembers().contains(member));
        assertTrue(memberService.getAllMembers().contains(member1));
    }

    @Test
    void removeMemberSuccessfully(){
        Member member = new Member("US01", "admin", "Alice Johnson", "123 Maple Street, Springfield", "alice.johnson@email.com", "+1-555-234-5678");
        memberService.registerMember(member);

        memberService.removeMember("US01");

        Optional<Member> result = memberService.getMember("US01");

        assertTrue(result.isEmpty());
        assertEquals(0,memberService.getAllMembers().size());
    }

    @Test
    void removeMemberIdNotFound(){

        assertThrows(IllegalArgumentException.class,() ->{
            memberService.removeMember("US01");
        });

    }

    @Test
    void getAllMembersEmptyService(){
        assertEquals(0,memberService.getAllMembers().size());
    }

}
