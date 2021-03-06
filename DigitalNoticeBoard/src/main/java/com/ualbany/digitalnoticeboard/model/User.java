package com.ualbany.digitalnoticeboard.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity(name = "User")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"email", "username"}) })
public class User extends Persistable {

	private String username;
	private String email;
	private String password;
    private VerificationToken verificationToken;
    private Boolean isActive;
    private Profile profile;
    private String role;
    private String department;
    private String levelOfDegree;
    
    List<GroupMember> membergroups = new ArrayList<GroupMember>();
	List<Notice> bookmarkednotices = new ArrayList<Notice>();
	
	@Transient//this field will not be saved in the database.
    private String passwordConfirm;
	@Transient//this field will not be saved in the database.
    private boolean resetPasswordStatus=false;

    private List<Role> roles = new ArrayList<Role>();

    @Column(nullable = false)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
    	this.roles = roles;
    }
    
    public void addRole(Role role) {
    	role.setUser(this);
    	this.roles.add(role);
    }
    
    public void removeRole(Role role) {
    	this.roles.remove(role);
    	role.setUser(null);
    }
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	@OneToMany(mappedBy = "user")
    public List<GroupMember> getMembergroups() {
		return membergroups;
	}

	public void setMembergroups(List<GroupMember> membergroups) {
		this.membergroups = membergroups;
	}

	@ManyToMany(mappedBy = "bookmarkusers")
    public List<Notice> getBookmarkednotices() {
		return bookmarkednotices;
	}

	public void setBookmarkednotices(List<Notice> bookmarkednotices) {
		this.bookmarkednotices = bookmarkednotices;
	}

	@OneToOne(mappedBy ="user")
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLevelOfDegree() {
		return levelOfDegree;
	}

	public void setLevelOfDegree(String levelOfDegree) {
		this.levelOfDegree = levelOfDegree;
	}

	public boolean isResetPasswordStatus() {
		return resetPasswordStatus;
	}

	public void setResetPasswordStatus(boolean resetPasswordStatus) {
		this.resetPasswordStatus = resetPasswordStatus;
	}

}
