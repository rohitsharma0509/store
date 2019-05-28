<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <li class="breadcrumb-item"><a href="${contextPath}<%=RequestUrls.ROLES%>"><spring:message code="Roles" text="Roles" /></a></li>
  <li class="breadcrumb-item active"><spring:message code="Modify Privileges" text="Modify Privileges" /></li>
</ol>
<div class="row" style="height:10px;"></div>
<c:choose>
  <c:when test="${privilegeDtos.size() > 0}">
    <div class="row">
      <div class="col-sm-12">
        <table class="table content-table">
          <tr>
            <th><input type="checkbox" name="ids" id="all" /></th>
            <th><spring:message code="Privilege Name" text="Privilege Name" /></th>
            <th><spring:message code="Description" text="Description" /></th>
          </tr>
          <c:forEach var="privilegeDto" items="${privilegeDtos}">
            <tr>
              <c:choose>
              <c:when test="${privilegeDts.isInRole}">
                <td><input class="checkbox" type="checkbox" name="ids" checked value="${privilegeDto.id}" /></td>
              </c:when>
              <c:otherwise>
                <td><input class="checkbox" type="checkbox" name="ids" value="${privilegeDto.id}" /></td>
              </c:otherwise>
              </c:choose>
              <td>${privilegeDto.name}</td>
              <td>${privilegeDto.description}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </c:when>
  <c:otherwise>
    <div class="row card norecord">
      <div class="col-sm-12"><spring:message code="No Records Found" text="No Records Found" />.</div>
    </div>
  </c:otherwise>
</c:choose>