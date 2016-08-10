<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
