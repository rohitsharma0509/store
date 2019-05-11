<%@page import="com.app.ecom.store.model.User"%>
<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
User user = (User) session.getAttribute("user");
String name = String.format("%1$s %2$s", user.getFirstName(), user.getLastName());
%>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
<script src="../js/jquery.min.js"></script>
<script src="../js/popper.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script>
  function openModal(url) {
    $('.modal-body').load(url, function(){
        $('#myModal').modal({show:true});
    });
  }
  function callAjax(method, baseUrl) {
    $.ajax({
        type: method,
        url: baseUrl,
        contentType: "application/json",
        dataType: "json",
        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
        success: function(response) {
            console.log(response);
        },
        error: function(response) {
            console.log(response);
        }
    });   
  }
  function callAjaxForDelete(baseUrl){
    alert("Are you sure you want to delete?");
    $.ajax({
        type: "DELETE",
        url: baseUrl,
        contentType: "application/json",
        dataType: "json",
        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
        success: function(response) {
          window.location.reload();
        },
        error: function(response) {
            console.log(response);
        }
    });   
  }
  $(function(){
      $('[rel="moreActions"]').popover({
          container: 'body',
          html: true,
          content: function () {
              var clone = $($(this).data('popover-content')).clone(true).removeClass('d-none');
              return clone;
          }
      }).click(function(e) {
          e.preventDefault();
      });
    $("#all").change(function() {
      $(".checkbox").prop("checked", $(this).prop("checked"));
    });
    $(".checkbox").change(function() {
      if(false==$(this).prop("checked")){
        $("#all").prop("checked", false);
      }
      if($(".checkbox:checked").length == $(".checkbox").length) {
        $("#all").prop("checked", true);
      }
    });
  });
  
  </script>

<style>
* {
  font-size: 12px;
}
.main-row {
  min-height: 400px;
}
.usermenulistcol {
  padding-right: 0px;
}

.submenus {
  background-color: #222222a8;
}

.menubar>li>a {
  margin-right: 0px;
  border: 1px solid #000000;
}

.navbar-dark .navbar-nav .nav-link {
  color: white;
}

.urgent_fields {
  color: red;
}

.form-group-row {
  padding-bottom: 10px;
}

.main-center {
  background: #009edf1a;
  color: #000000;
  text-shadow: none;
  -webkit-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  -moz-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
}

.shadow {
  text-shadow: none;
  -webkit-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  -moz-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
}

.content-table {
  background: #009edf1a;
  -webkit-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  -moz-box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
  box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.31);
}
.has-error {
    color: red;
}
.norecord {
  height: 100px;
    margin-left: 1px;
    margin-right: 1px;
    margin-bottom: 10px;
}
.modal-dialog {
  max-width: 800px;
}
</style>

<div class="row" style="height: 50px; margin-top: 10px;background-color: black;">
  <div class="col-sm-9"></div>
  <div class="col-sm-3">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
      <form id="logoutForm" method="POST" action="${contextPath}/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="dropdown">
          <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" 
          style="float: right;min-width:160px;background-color: black;color:white;"><%=name %></button>
          <div class="dropdown-menu">
            <security:authorize access="hasAuthority('ADMIN')">
              <a class="dropdown-item" href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a>
            </security:authorize>
            <a class="dropdown-item" href="${contextPath}<%=RequestUrls.MY_ACCOUNT %>"><spring:message code="My Account" text="My Account" /></a>
            <a class="dropdown-item" href="#" onclick="document.forms['logoutForm'].submit()"><spring:message code="Logout" text="Logout" /></a>
          </div>
        </div>
      </form>
    </c:if>
  </div>
</div>