<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%
String action = "Save";
%>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <c:choose>
    <c:when test="${empty emailAccount.id}">
      <li class="breadcrumb-item active"><spring:message code="Add Email Account" text="Add Email Account" /></li>
    </c:when>
    <c:otherwise>
      <li class="breadcrumb-item active"><spring:message code="Edit Email Account" text="Edit Email Account" /></li>
      <% action = "Edit"; %>
    </c:otherwise>
  </c:choose>
</ol>
<div class="row" style="height:10px;">
</div>
<form:form method="POST" modelAttribute="emailAccount" class="form-horizontal" enctype="multipart/form-data" action="addEmailAccount">
<form:hidden path="id"  class="form-control input-sm"/>
<div class="container py-5">
    <h6><spring:message code="Email Account Details" text="Email Account Details" /></h6><hr>
    <div class="row">
        <div class="col-md-10 mx-auto">
          <div class="form-group row">
              <div class="col-sm-6">
                  <label for="host"><spring:message code="Host" text="Host" />&nbsp;<span class="urgent_fields">*</span></label>
                  <form:input type="text" path="host" placeholder="smtp.gmail.com" id="host" class="form-control input-sm"/>
                  <form:errors path="host" class="help-inline has-error"></form:errors>
              </div>
              <div class="col-sm-6">
                  <label for="port"><spring:message code="Port" text="Port" />&nbsp;<span class="urgent_fields">*</span></label>
                  <form:input type="text" path="port" id="port" placeholder="587" class="form-control input-sm"/>
                  <form:errors path="port" class="help-inline has-error"></form:errors>
              </div>
          </div>
          <div class="form-group row">
              <div class="col-sm-6">
                  <label for="username"><spring:message code="Email" text="Email" />&nbsp;<span class="urgent_fields">*</span></label>
                  <form:input type="text" path="username" id="username" placeholder="Enter email" class="form-control input-sm"/>
                  <form:errors path="username" class="help-inline has-error"></form:errors>
              </div>
              <div class="col-sm-6">
                  <label for="password"><spring:message code="Password" text="Password" />&nbsp;<span class="urgent_fields">*</span></label>
                  <form:input type="password" path="password" id="password" class="form-control input-sm"/>
                  <form:errors path="password" class="help-inline has-error"></form:errors>
              </div>
          </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-sm-2"><button type="submit" class="btn btn-success"><spring:message code="<%=action %>" text="<%=action %>" /></button></div>
        <div class="col-sm-10"></div>
    </div>
</div>
</form:form>