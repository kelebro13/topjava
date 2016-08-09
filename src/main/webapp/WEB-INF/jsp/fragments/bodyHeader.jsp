<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="meals" class="navbar-brand"><fmt:message key="app.title"/></a>

        <div class="collapse navbar-collapse navbar-right">
            <ul class="nav navbar-nav">
                <li>
                    <form:form class="navbar-form navbar-right" action="logout" method="post">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <a class="btn btn-info" role="button" href="users"><fmt:message key="users.title"/></a>
                            </sec:authorize>
                            <a class="btn btn-info" role="button" href="profile">${userTo.name} profile</a>
                            <input type="submit" class="btn btn-primary" value="<fmt:message key="app.logout"/>">
                        </sec:authorize>
                    </form:form>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false" id="language">
                        ${pageContext.response.locale}
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a onclick="changeLang('en')">English</a></li>
                        <li><a onclick="changeLang('ru')">Русский</a></li>
                    </ul>
                </li>
                <script>
                    function changeLang(str) {
                        debugger;
                        var url = window.location.href.split('?')[0];
                        window.location.href = url + "?lang=" + str;
                    }
                </script>
            </ul>
        </div>
    </div>
</div>
