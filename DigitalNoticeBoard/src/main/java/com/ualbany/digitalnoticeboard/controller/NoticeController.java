package com.ualbany.digitalnoticeboard.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ualbany.digitalnoticeboard.model.Channel;
import com.ualbany.digitalnoticeboard.model.Group;
import com.ualbany.digitalnoticeboard.model.Notice;
import com.ualbany.digitalnoticeboard.model.ShortNotice;
import com.ualbany.digitalnoticeboard.model.User;
import com.ualbany.digitalnoticeboard.model.dto.AddNoticeDto;
import com.ualbany.digitalnoticeboard.service.ChannelService;
import com.ualbany.digitalnoticeboard.service.GroupService;
import com.ualbany.digitalnoticeboard.service.NoticeService;
import com.ualbany.digitalnoticeboard.service.ShortNoticeService;
import com.ualbany.digitalnoticeboard.service.UserService;

@Controller
public class NoticeController extends BaseController {

	@Autowired
	ChannelService channelService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	ShortNoticeService shortNoticeService;
	
	@Autowired
	GroupService groupService;
	
	@GetMapping(value = "/{username}/notice/addNotice")
    public ModelAndView addNoticeGetRequest(@PathVariable final String username, @ModelAttribute("noticeForm") AddNoticeDto noticeForm, BindingResult bindingResult, Model model) {
		User user = userService.findByUsername(username);
	    ModelAndView mv = new ModelAndView("addnotice");
	    List<String> channels = channelService.getAllChannels();
        mv.addObject("user", user);
        mv.addObject("channels", channels);
        List<Group> groups = groupService.getUserGroups(user);
    	mv.addObject("groups", groups);
        return mv;
    }
	 
	@PostMapping("/{username}/notice/addNotice")
    public ModelAndView addNoticePutRequest(@PathVariable final String username, @ModelAttribute("noticeForm") AddNoticeDto noticeForm, BindingResult bindingResult, Model model) {
		User user = userService.findByUsername(username);
		Notice notice = new Notice();
		notice.setTitle(noticeForm.getTitle());
		notice.setSummary(noticeForm.getSummary());
		notice.setDetails(notice.getDetails());
		notice.setExpirationDate(noticeForm.getExpirationDate());
		notice.setChannels(channelService.getChannelByTiles(noticeForm.getChannels()));
		setpersistableproperties(notice, user);
		
        noticeService.save(notice);
        
		ModelAndView mv = new ModelAndView("userhome");
		mv.addObject("user", user);
        List<Channel> channels = channelService.getChannelsWithValidNotices();
        mv.addObject("Channels", channels);
        List<ShortNotice> shortnotices = shortNoticeService.getAllActiveNotices();
        Collections.sort(shortnotices, (o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()));

        mv.addObject("ShortNotices", shortnotices);
        List<Group> groups = groupService.getUserGroups(user);
    	mv.addObject("groups", groups);
        return mv;
    }
	
	@GetMapping("/{username}/notice/addedNotices")
    public ModelAndView addedNoticesGetRequest(@PathVariable final String username, Model model) {
		User user = userService.findByUsername(username);
        return getNoticeList(user);
    }
	
	@GetMapping("/{username}/bookmarkednotices")
    public ModelAndView getBookmarkedNoticesGetRequest(@PathVariable final String username, Model model) {
		User user = userService.findByUsername(username);
		ModelAndView mv = new ModelAndView("bookmarkednotices");
		mv.addObject("user", user);
		mv.addObject("notices", user.getBookmarkednotices()); 
		 List<Group> groups = groupService.getUserGroups(user);
	    	mv.addObject("groups", groups);
        return mv;
    }
	
	public ModelAndView getNoticeList(User user) {
		
        List<Notice> notices= noticeService.getUserCreatedNotices(user);
        List<ShortNotice> shortnotices = shortNoticeService.getUserCreatedNotices(user);
        Collections.sort(shortnotices, (o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()));
		ModelAndView mv = new ModelAndView("addednotices");
		mv.addObject("user", user);
		mv.addObject("notices", notices);      
        mv.addObject("ShortNotices", shortnotices);
        List<Group> groups = groupService.getUserGroups(user);
    	mv.addObject("groups", groups);
        return mv;
	}
	@GetMapping("/{username}/notice/deletenotice/{id}")
    public ModelAndView deleteNoticesById(@PathVariable final String username,@PathVariable final Long id, Model model) {
		User user = userService.findByUsername(username);
       noticeService.deleteNoticeById(user, id);
        return getNoticeList(user);
    }
}
