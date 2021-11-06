package com.ualbany.digitalnoticeboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ualbany.digitalnoticeboard.model.Channel;
import com.ualbany.digitalnoticeboard.model.Visibility;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
	public List<Channel> findByVisibility(Visibility visibility);
	
}