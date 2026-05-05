package service;

import domain.Member;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class MemberService {

    private HashMap<String, Member> members;


    public void registerMember(Member member){
        if(members.containsKey(member.getMemberId())){
            throw new IllegalArgumentException("Member with id: " + member.getMemberId() + " already exists.");
        }
        members.put(member.getMemberId(),member);
    }

    public Optional<Member> getMember(String memberId){
        if(members.containsKey(memberId)){
            return Optional.of(members.get(memberId));
        }
        return Optional.empty();
    }
}
