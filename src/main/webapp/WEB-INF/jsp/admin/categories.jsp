<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="../js/actions.js"></script>
<script>
var filterState=0;
$(document).ready(function(){
   $('#hideShowDiv').click(function() { 
      if(filterState==0){
        $("#filters").slideUp("slow");
        $("#hideShowDiv").html("<a href='#'>Show Filters</a>");
        filterState=1;
      }else if(filterState==1){
        $("#filters").slideDown("slow");
        $("#hideShowDiv").html("<a href='#'>Hide Filters</a>");
        filterState=0;
      }
   });
});
</script>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <li class="breadcrumb-item active"><spring:message code="Categories" text="Categories" /></li>
  <li class="ml-auto"><span id="hideShowDiv"><a href="#">Hide Filters</a></span></li>
</ol>
<div class="row" style="height: 10px;"></div>
<div class="row" id="filters">
	<div class="col-sm-12">
		<form method="GET" class="form-horizontal" action="<%=RequestUrls.CATEGORIES%>">
			<div class="card">
				<div class="card-body main-center">
					<div class="row">
						<div class="col-sm-3">
							<label for="categoryName"><spring:message code="Category Name" text="Category Name" /></label>
							<input type="text" id="name" name="name" value="${param.name}" class="form-control input-sm" />
						</div>
						<div class="col-sm-9"></div>
					</div>
					<div class="row">
						<div class="col-sm-1">
							<input type="submit" value="<spring:message code="Search" text="Search" />" style="margin-top: 15px;" class="btn btn-sm btn-info form-control">
						</div>
						<div class="col-sm-11"></div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="row" style="height: 20px;"></div>
<div class="row">
	<div class="col-sm-11">
		<a class="btn btn-sm btn-info pover" href="#" rel="moreActions" data-popover-content="#moreActionContent" data-toggle="popover" >More Actions&nbsp;&nbsp;<i class="fa fa-caret-right" aria-hidden="true" ></i></a>
	</div>
</div>
<div id="moreActionContent" class="d-none">
	<ul class="list-group list-group-flush">
	  <li class="list-group-item list-group-item-action"><a href="${contextPath}<%=RequestUrls.ADD_CATEGORY %>"><spring:message code="Add Category" text="Add Category" /></a></li>
    <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="MULTIPLE" data-url="<%=RequestUrls.DELETE_BULK_CATEGORY %>"><spring:message code="Delete" text="Delete" /></a></li>
    <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="ALL" data-url="<%=RequestUrls.DELETE_ALL_CATEGORY %>"><spring:message code="Delete All" text="Delete All" /></a></li>
	</ul>
</div>
<div class="row" style="height: 10px;"></div>
<c:choose>
	<c:when test="${page.getContent().size() > 0}">
		<div class="row">
			<div class="col-sm-12">
				<table class="table content-table">
					<tr>
						<th><input type="checkbox" name="ids" id="all" /></th>
						<th>Category Name</th>
						<th>Action</th>
					</tr>
					<c:forEach var="category" items="${page.getContent()}" varStatus="loop">
						<tr>
							<td><input class="checkbox" type="checkbox" name="ids" value="${category.id}" /></td>
							<td>${category.name}</td>
							<td>
							  <a href="#" class="pover" rel="moreActions" data-popover-content="#singleRecordAction${loop.index}" data-placement="left" data-toggle="popover" ><i class="fa fa-list" aria-hidden="true"></i></a>
                <div id="singleRecordAction${loop.index}" class="d-none">
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item list-group-item-action"><a href="${contextPath}<%=RequestUrls.ADD_CATEGORY %>?id=${category.id}"><spring:message code="Edit" text="Edit" /></a></li>
                    <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="SINGLE" data-url="<%=RequestUrls.CATEGORIES %>/${category.id}"><spring:message code="Delete" text="Delete" /></a></li>
                  </ul>
                </div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="row card norecord">
			<div class="col-sm-12">No Records Found.</div>
		</div>
	</c:otherwise>
</c:choose>
	<c:choose>
		<c:when test="${page.getTotalPages() > 1}">
			<div class="row">
				<div class="col-sm-12">${pagging}</div>
			</div>
		</c:when>
	</c:choose>
<div class="row">
  <div class="modal fade" id="deleteConfirmation" role="dialog" aria-labelledby="deleteConfirmationLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header"><spring:message code="Confirm Delete" text="Confirm Delete" /></div>
        <div class="modal-body mx-3">
          <p><spring:message code="Are you sure you want to delete?" text="Are you sure you want to delete?" /> </p>
        </div>
        <div class="modal-footer">
          <form:form id="deleteForm">
            <button type="submit" class="btn btn-success"><spring:message code="Delete" text="Delete" /></button>
            <button type="button" class="btn btn-success" data-dismiss="modal"><spring:message code="Close" text="Close" /></button>
          </form:form>
        </div>
      </div>  
    </div>
  </div>
</div>
<div class="row">
  <div class="modal fade" id="alertMessages" role="dialog" aria-labelledby="alertMessagesLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-body mx-3"></div>
        <div class="modal-footer">
            <button type="button" class="btn btn-success" data-dismiss="modal"><spring:message code="OK" text="OK" /></button>
        </div>
      </div>
    </div>
  </div>
</div>