<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  <li class="breadcrumb-item active"><spring:message code="Products" text="Products" /></li>
  <li class="ml-auto"><span id="hideShowDiv"><a href="#">Hide Filters</a></span></li>
</ol>
<div class="row" style="height: 10px;"></div>
<div class="row" id="filters">
	<div class="col-sm-12">
	<form method="GET" class="form-horizontal" action="<%=RequestUrls.PRODUCTS %>">
		<div class="card">
			<div class="card-body main-center">
			<div class="row">
				<div class="col-sm-2">
					<label for="category" class="control-label text-right"><spring:message code="Category" text="Category" /></label> 
					<select id="categoryId" name="categoryId" class="form-control input-sm">
						<option value="-1"><spring:message code="Select Category" text="Select Category" /></option>
						<c:forEach var="category" items="${categories}">
							<c:choose>
								<c:when test="${param.categoryId == category.id}">
									<option selected value="${category.id}">${category.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${category.id}">${category.name}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-1"></div>
				<div class="col-sm-2">
					<label for="brandName"><spring:message code="Brand Name" text="Brand Name" /></label>
					<input type="text" id="brandName" name="brandName" value="${param.brandName}" class="form-control input-sm" />
				</div>
				<div class="col-sm-1"></div>
				<div class="col-sm-2">
					<label for="productName"><spring:message code="Product Name" text="Product Name" /></label> 
					<input type="text" id="productName" name="productName" value="${param.productName}" class="form-control input-sm" />
				</div>
				<div class="col-sm-4"></div>
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
	  <li class="list-group-item list-group-item-action"><a href="${contextPath}<%=RequestUrls.ADD_PRODUCT %>"><spring:message code="Add Product" text="Add Product" /></a></li>
	  <li class="list-group-item list-group-item-action"><a href="${contextPath}<%=RequestUrls.PRODUCTS_IMPORT %>"><spring:message code="Import CSV/XML" text="Import CSV/XML" /></a></li>
	  <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="MULTIPLE" data-url="<%=RequestUrls.DELETE_BULK_PRODUCT %>"><spring:message code="Delete" text="Delete" /></a></li>
    <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="ALL" data-url="<%=RequestUrls.DELETE_ALL_PRODUCT %>"><spring:message code="Delete All" text="Delete All" /></a></li>
	</ul>
</div>
<div class="row" style="height: 10px;"></div>
<div class="row">
	<div class="col-sm-12">
		<c:choose>
			<c:when test="${page.getContent().size() > 0}">
				<div class="row">
					<div class="col-sm-12">
						<table class="table content-table">
							<tr>
								<th><input type="checkbox" name="ids" id="all" value='-1' /></th>
								<th><spring:message code="Code" text="Code" /></th>
								<th><spring:message code="Brand Name" text="Brand Name" /></th>
								<th><spring:message code="Name" text="Name" /></th>
								<th><spring:message code="Quantity" text="Quantity" /></th>
								<th><spring:message code="Price" text="Price" /></th>
								<th><spring:message code="Alert Quantity" text="Alert Quantity" /></th>
								<th><spring:message code="Action" text="Action" /></th>
							</tr>
							<c:forEach var="product" items="${page.getContent()}" varStatus="loop">
								<tr>
									<td><input class="checkbox" type="checkbox" name="ids" value="${product.id}" /></td>
									<td>${product.code}</td>
									<td>${product.brandName}</td>
									<td>${product.name}</td>
									<td>${product.quantity}</td>
									<td>${product.perProductPrice}</td>
									<td>${product.alertQuantity}</td>
									<td>
										<a href="#" class="pover" rel="moreActions" data-popover-content="#singleRecordAction${loop.index}" data-placement="left" data-toggle="popover" ><i class="fa fa-list" aria-hidden="true"></i></a>
		                <div id="singleRecordAction${loop.index}" class="d-none">
		                  <ul class="list-group list-group-flush">
		                    <li class="list-group-item list-group-item-action"><a href="${contextPath}<%=RequestUrls.ADD_PRODUCT %>?id=${product.id}"><spring:message code="Edit" text="Edit" /></a></li>
		                    <li class="list-group-item list-group-item-action"><a href="#" class="deleteBtn" data-flag="SINGLE" data-url="<%=RequestUrls.PRODUCTS %>/${product.id}"><spring:message code="Delete" text="Delete" /></a></li>
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
					<div class="col-sm-12">
						<spring:message code="No Records Found" text="No Records Found" />
					</div>
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
	</div>
</div>
<div class="row">
  <div class="modal fade" id="deleteConfirmation" role="dialog" aria-labelledby="deleteConfirmationLabel" aria-hidden="true">
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
  <div class="modal fade" id="alertMessages" role="dialog" aria-labelledby="alertMessagesLabel" aria-hidden="true">
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