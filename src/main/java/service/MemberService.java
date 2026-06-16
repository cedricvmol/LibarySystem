package service;

import domain.Member;
import storage.MemberStorage;

import java.util.*;

public class MemberService {

    private HashMap<String, Member> members;
    private MemberStorage memberStorage;


    public MemberService(MemberStorage memberStorage) {
        this.members = new HashMap<>();
        this.memberStorage = memberStorage;
    }

    public void registerMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            throw new IllegalArgumentException("Member with id: " + member.getMemberId() + " already exists.");
        }
        members.put(member.getMemberId(), member);
    }

    public Optional<Member> getMember(String memberId) {
        if (members.containsKey(memberId)) {
            return Optional.of(members.get(memberId));
        }
        return Optional.empty();
    }

    public Collection<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public void removeMember(String memberId) {
        if (!members.containsKey(memberId)) {
            throw new IllegalArgumentException("Member with id: " + memberId + " does not exist.");
        }
        members.remove(memberId);
    }
}
