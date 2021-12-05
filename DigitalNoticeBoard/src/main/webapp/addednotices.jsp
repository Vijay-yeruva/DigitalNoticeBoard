<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/style.css" rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
<link href="${contextPath}/resources/css/home.css" rel="stylesheet">
</head>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">
    <img src="${contextPath}/resources/img/icon.png" width="30" height="30" alt="">
  </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="${contextPath}/${user.username}/userhome">Public <span class="sr-only">(current)</span></a>
      </li>
       <c:forEach var="grp" items="${groups}">
      <li class="nav-item">
        <a class="nav-link" href="${contextPath}/${user.username}/group/${grp.id}">${grp.name}</a>
      </li>
      </c:forEach>
    </ul>
    <div class="dropdown">
	  <a class="btn btn-secondary dropdown-toggle" href="1" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		${user.username} 
	  </a>
	  <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
      <a class="dropdown-item" href="${contextPath}/${user.username}/profile">Profile</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/notice/addedNotices">Added Notices</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/bookmarkednotices">BookMarked Notices</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/notice/addNotice">Add Notice</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/notice/addShortNotice">Add Short Notice</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/newGroup">New Group</a>
      <a class="dropdown-item" href="${contextPath}/${user.username}/pendinginvite">Current Invitations</a>
      <a class="dropdown-item" href="${contextPath}/signout">SignOut</a>
	  </div>
	</div>
  </div>
</nav>
<body>
<div class="container">
  <div class="row">
    <c:forEach var="note" items="${notices}">  
      <div class="col-ms-4">
      <div class="card notice" style="width: 25rem;">
        <h3 class="card-title bg-primary">${note.title}</h3>
          <div class="card-body">      
          <p class=notice-summary>${note.summary}</p>
          <p class=notice-expirationtime><strong>Created Date :</strong><fmt:formatDate value="${note.createdAt}" pattern="yyyy-MM-dd" /></p>
          <p class=notice-expirationtime><strong>Expire Date :</strong><fmt:formatDate value="${note.expirationDate}" pattern="yyyy-MM-dd" /></p>
          <button id="delete-button" onclick="deleteRecord(${note.id})"><i class="fa fa-trash" aria-hidden="true"></i></button>
        </div>
      </div>
    </div>
 
    </c:forEach>  
  </div>
  <div class="card" style="">
    <ul class="list-group list-group-flush">
    <c:forEach var="shnote" items="${ShortNotices}"> 
     <!-- <div class="shortnotice">-->
        <li class="list-group-item">
 		      <p>${shnote.details}</p>
           <div>
 	      	<p>Expires at: <fmt:formatDate value="${shnote.expirationDate}" type="time" pattern="HH:mm" /></p>
           <button id="delete-snote" onclick="deleteShortNoticeRecord(${shnote.id})" class="btn btn-primary float-right"><i class="far fa-trash-alt"></i></button>
           </div>

 	   <!--</div>-->
    </li>
    

    </c:forEach>
    </ul>	 
  </div> 
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script>
  var contextPath="${contextPath}";
  var userName="${user.username}"

function deleteRecord(id){
  
    if(confirm("Are you sure you want to delete the Notice?")){
      $.ajax({
        url: contextPath+"/"+userName+"/notice/deletenotice/"+id,
        method: 'GET',
        success: function () {
            window.location.reload();
        },
        error: function (error) {
            alert(error);
        }
    })

    }
    else{
      //alert("no");
        return false;
    }
};
function deleteShortNoticeRecord(id){
  
  if(confirm("Are you sure you want to delete the Short Notice?")){
    $.ajax({
      url: contextPath+"/"+userName+"/notice/deleteshortnotice/"+id,
      method: 'GET',
      success: function () {
          //alert('record has been deleted');
          //getAllBooks();
          window.location.reload();
      },
      error: function (error) {
          alert(error);
      }
  })

  }
  else{
    //alert("no");
      return false;
  }
};

</script>
</body>
</html>
