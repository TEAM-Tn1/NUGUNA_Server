package io.github.tn1.server.entity.user;

import io.github.tn1.server.dto.user.response.UserInformationResponse;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.like.Like;
import io.github.tn1.server.entity.question.Question;
import io.github.tn1.server.entity.tag_notification.TagNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "tbl_user")
public class User implements UserDetails {

    @Id
    @Column(length = 20)
    private String email;

    @Column(length = 4)
    private String name;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 4)
    private String gcn;

    @Column(length = 3)
    private String roomNumber;

    @Column(length = 20)
    private String accountNumber;

    @Column(length = 4096)
    private String deviceToken;

    @Column(columnDefinition = "BIT(1) default true")
    private boolean alarm;

    private LocalDate blackDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Like> likes = new HashSet<>();

    @Builder
    public User(String email, String name, Role role,
                String gcn, String roomNumber, String accountNumber,
                String deviceToken, boolean alarm, LocalDate blackDate) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.gcn = gcn;
        this.roomNumber = roomNumber;
        this.accountNumber = accountNumber;
        this.deviceToken = deviceToken;
        this.alarm = alarm;
        this.blackDate = blackDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User changeNameAndGcn(String name, String gcn) {
        this.name = name;
        this.gcn = gcn;
        return this;
    }

    public User writeInformation(String roomNumber, String accountNumber) {
        this.roomNumber = roomNumber;
        this.accountNumber = accountNumber;
        return this;
    }

    public UserInformationResponse getInformation() {
        return new UserInformationResponse(email, name, gcn, roomNumber, accountNumber);
    }

    public boolean writeAllInformation() {
        return roomNumber != null && accountNumber != null;
    }

    public boolean haveDeviceToken() {
        return deviceToken != null;
    }

}
