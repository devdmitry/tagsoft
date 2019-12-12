package com.rohov.tagsoft.entity;

import com.rohov.tagsoft.model.Country;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    Long id = UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;

    @Column(name="name")
    String name;

    @Column(name="country")
    Country country;

    @Column(name="states")
    @ElementCollection
    List<String> states = new ArrayList<>();

    @Column(name="province")
    String province;

    @Column(name="city")
    String city;

    @Column(name="email")
    String email;

    @Column(name="password")
    String password;

    @Column(name = "created_date")
    Instant createdDate = Instant.now();

    @Column(name = "updated_date")
    Instant updatedDate = Instant.now();

    @Column(name="is_deleted")
    boolean deleted = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
}
