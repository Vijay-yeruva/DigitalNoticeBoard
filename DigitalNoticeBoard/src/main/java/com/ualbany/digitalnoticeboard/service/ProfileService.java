package com.ualbany.digitalnoticeboard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ualbany.digitalnoticeboard.model.Profile;
import com.ualbany.digitalnoticeboard.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository repository;

    public void save(Profile p) {
    	repository.save(p);
    }

    public Profile findById(Long id) {
    	Optional<Profile> profile= repository.findById(id);
    	if(profile.isPresent())
    		return profile.get();
    	else
    		return null;
    }
    
    public Profile findByUserId(Long id) {
    	Optional<Profile> profile= repository.findByUserId(id);
    	if(profile.isPresent())
    		return profile.get();
    	else
    		return null;
    }
}
