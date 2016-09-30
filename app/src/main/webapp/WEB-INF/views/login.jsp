<!DOCTYPE html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<div class="login-page">
	<div class="img-container">
		<div class="text-center pull-right photo">
			<img src="content/images/flat-avatar.png"
				class="user-avatar img-circle img-responsive" />
			<h1>
				VERSATILE<br/ > <span>DASHBOARD</span><br /> <small>Free
					Version</small>
			</h1>
		</div>
	</div>

	<div class="form-content">
		<div class="pro-link">
			<a href="http://www.strapui.com/">MORE PREMIUM THEME</a>
		</div>
		<form role="form" class="bottom-75" ng-submit="submit()">
			<div class="table-form">
				<div class="form-groups">
					<div class="form-group">
						<input type="text" class="form-control input-lg" id=""
							placeholder="EMAIL">
					</div>
					<div class="form-group">
						<input type="password" class="form-control input-lg" id=""
							placeholder="PASSWORD">
					</div>


				</div>
				<div class="button-container">
					<button type="submit" class="btn btn-default login">
						<img src="content/images/arrow.png" alt="">
					</button>
				</div>
			</div>
			<div class="remember">
				<label class="checkbox1" for="Option"> <input id="Option"
					type="checkbox" class=""> <span></span>
				</label> remember me <span class="pass"> forgot password?</span>
			</div>
		</form>
	</div>
</div>

</html>