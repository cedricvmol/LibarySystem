package service;

import domain.Member;

import java.util.HashMap;

public class MemberService {

    private HashMap<String, Member> members;


    public void registerMember(Member member){
        if(members.containsKey(member.getMemberId())){
            throw new IllegalArgumentException("Member with id: " + member.getMemberId() + " already exists.");
        }
        members.put(member.getMemberId(),member);
    }

}
