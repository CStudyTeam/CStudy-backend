package com.CStudy.domain.member.entity;

import com.CStudy.domain.competition.entity.MemberCompetition;
import com.CStudy.domain.question.entity.MemberQuestion;
import com.CStudy.domain.request.entity.Request;
import com.CStudy.domain.role.entity.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email")
    private String email;

    private String password;

    @Column(name = "member_name")
    private String name;

    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    Set<MemberQuestion> questions = new HashSet<>();

    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<MemberCompetition>memberCompetitions = new ArrayList<>();


    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<Request> requests = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



    public void changeRole(Role role) {
        roles.add(role);
    }

    @Builder
    public Member(String email, String password, String name, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public void addRequest(Request request){
        this.requests.add(request);
    }
}
