<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/sockjs.min.js"></script>
<script src="js/stomp.min.js"></script>
<link href="css/chat.css" rel="stylesheet">
<script src="js/chat.js"></script>
<style>
.indicator {
  background-color: #dee2e6!important;
}
.indicator-body {
  color: black;
  min-height: 123px;
}
</style>
<div class="row">
  <div class="col-sm-11" style="height: 40px;"><spring:message code="Dashboard" text="Dashboard" /></div>
  <div class="col-sm-1">
    <div class="chat-wrapper">
      <a href="#" onclick="openChatWindow()"><i class="fa fa-comments" aria-hidden="true"></i>&nbsp;<spring:message code="Chat Now" text="Chat Now" /></a>
    </div>
  </div>
</div>
<div class="row" style="height: 10px;"></div>
<security:authorize access="hasAuthority('ADMIN')">
<div class="row">
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6>
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;${totalProducts}</h6>
        <h5><spring:message code="Total Products" text="Total Products" /></h5>
      </div>
    </div>
  </div>
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6>
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;${availableProducts}
        </h6>
        <h5><spring:message code="Available Products" text="Available Products" /></h5>
      </div>
    </div>
  </div>
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6 class="card-title">
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;${outOfStockProducts}
        </h6>
        <h5 class="card-text"><spring:message code="Out of Stock Products" text="Out of Stock Products" /></h5>
      </div>
    </div>
  </div>
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6>
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;${alertProducts}
        </h6>
        <h5><spring:message code="Alert Products" text="Alert Products" /></h5>
      </div>
    </div>
  </div>
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6>
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;${todayOrder}
        </h6>
        <h5><spring:message code="Today's Order" text="Today's Order" /></h5>
      </div>
    </div>
  </div>
  <div class="col-sm-2">
    <div class="card indicator">
      <div class="card-body indicator-body shadow">
        <h6>
          <i class="fa fa-user" aria-hidden="true"></i>&nbsp;&nbsp;0
        </h6>
        <h5><spring:message code="Feedback Received" text="Feedback Received" /></h5>
      </div>
    </div>
  </div>
</div>
<div class="row" style="margin-top:15px;">
  <div class="col-sm-12">
    <div class="card">
      <div class="card-body shadow" style="padding:0px;">
        <div class="card-img-top"><img style="max-width: 100%;" alt="Stock Status" src="data:image/png;base64,${compareGraph}"/></div>
      </div>
    </div>
  </div>
</div>
<div class="row" style="margin-top:15px;">
  <div class="col-sm-4">
    <div class="card">
      <div class="card-body shadow" style="padding:0px;">
        <div class="card-img-top"><img style="max-width: 100%;" alt="Stock Status" src="data:image/png;base64,${stockStatus}"/></div>
      </div>
    </div>
  </div>
  <div class="col-sm-4">
    <div class="card">
      <div class="card-body shadow" style="padding:0px;">
        <div class="card-img-top"><img style="max-width: 100%;" alt="Yearly Sales Graph" src="data:image/png;base64,${yearlySalesGraph}"/></div>
      </div>
    </div>
  </div>
  <div class="col-sm-4">
    <div class="card">
      <div class="card-body shadow" style="padding:0px;">
        <div class="card-img-top"><img style="max-width: 100%;" alt="Monthly Sales Graph" src="data:image/png;base64,${monthlySalesGraph}"/></div>
      </div>
    </div>
  </div>
</div>
</security:authorize>
<div class="row">
  <div id="chatWindow" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">How may help you ?</h4>
          <button type="button" onclick="disconnect()" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <div id="chat-page">
            <div class="chat-container">
              <div class="connecting d-none">Connecting...</div>
              <ul id="messageArea"></ul>
              <br>
              <div class="form-group">
                <div class="input-group clearfix">
                  <input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control" />
                  <input type="hidden" id="name" value="${pageContext.request.userPrincipal.name}" class="form-control" />
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                  <button class="btn btn-info btn-sm" id="btn-send" onclick="sendMessage()">Send</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="row" style="height: 50px;"></div>