<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/icon.png">
    <link rel="stylesheet" type="text/css" href="style.css">
    <link href="../img/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <title>Easy Travel</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            outline: none;
        }

        html {
            height: 100%;
        }

        body {
            width: 100%;
            height: 100%;
            color: #333;
            background: #f7f7f7;
            font-size: lem;
            font-family: "Segoe UI", sans-serif;
            line-height: 135%;
        }

        a {
            color: #f48b8b;
            text-decoration: none;
            transition: all .6s ease;

        }

        a:hover {
            color: #8b9ff5;
            text-decoration: none;
            transition: all .6s ease;

        }

        a:active {
            color: #8ce4a6;
        }

        img {
            max-width: 100%;
            height: auto;
            width: auto \9;
        }

        hr {
            display: block;
            border: 0;
            height: 1px;
            border-top: 1px solid #ccc;
            padding: 0;
        }

        .left {
            float: left
        }

        :: selection {
            background: #f48b8b;
            color: #fff
        }

        :: moz-selection {
            background: #f48b8b;
            color: #fff
        }

        header, footer {
            width: 98%;
            background-color: #fff;
            min-height: 50px;
            float: left;
        }

        header {
            border-top: 2px solid silver;
            padding: 1%;
        }

        #logo {
            font-size: 2em;
            font-family: Arial, sans-serif;
        }

        #menu {
            color: #666;
            font-family: Arial, sans-serif;
            font-size: 1.7em;
            margin-top: 50px;
        }

        #menu hr {
            width: 500px;
            max-width: 100%;
            margin-top: 10px;
            margin-bottom: 10px;

        }

        #posts {
            float: left;
        }

        #posts img {
            width: 50%;
            height: 50%;
            border-radius: 5px 5px 0px 0px;
            border-bottom: 1px solid siver;
            margin-bottom: 10px;
        }

        #posts p {
            width: 50%;
            align: justify;
        }

        wellrow #post {
        }

        #wrapper {
            float: left;
            width: 100%;
            margin-top: 20px;
        }

        #wrapper #articles {
            float: left;
            width: 80%;
            margin-left: 10%;
        }

        #wrapper #articles article {
            float: left;
            width: 31.33333%;
            border: 1px solid silver;
            box-sizing: border-box;
            border-radius: 5px;
            background-color: #fafafa;
            margin-right: 2%;
            min-height: 400px;
            margin-bottom: 20px;
            padding: 10px;
        }

        #wrapper #articles article img {
            margin-left: 10%;
            width: 80%;
            height: 80%;
            border-radius: 5px 5px 0px 0px;
            border-bottom: 1px solid siver;
            margin-bottom: 10px;
        }

        #wrapper #articles article h4 {
            width: 90%;
            margin-left: 5%;
            font-weight: normal;
            font-size: 1.7em;
            color: #4a4a4a;
        }

        #wrapper #wellrow {
            width: 80%;
            margin-left: 10%;

        }

        #wrapper #articles article p {
            width: 80%;
            margin-left: 10%;
        }

        #wrapper #articles article a {
            padding: 5px;
            background-color: #0066ff;
            margin-left: 5%;
            border-radius: 5px;
            float: left;
            color: #fafafa;
        }

        #wrapper #articles article a:hover {
            background-color: #b8daf8;
            color: #0066ff;
        }

        #main_soc_block {
            width: 100%;
            padding: 2% 2%;
            border-bottom: 2px solid silver;
            border-top: 2px solid silver;
            margin: 20px 0;
            float: left;
            background: url(../img/font1.jpg) repeat-x center;
            height: 200px;
        }

        #main_soc_block_in {
            text-align: center;
            padding: 1%;
            height: 100px;
            background-color: #fafafa;
            border: 1px solid silver;
            border-radius: 7px;
            width: 500px;
        }

        footer {
            width: 100%;
            border-top: 2px solid silver;
            margin-top: 10px;
            padding-left: 10px;
        }

        #page-wrap:after {
            content: "";
            display: block;
        }

        footer, #page-wrap:after {
            height: 10px
        }

        #page-wrap {
            min-height: 94%;
            margin-bottom: -10px;
        }

        .navbar-def {
            background-color: #0099ff;
            border-color: #0066ff;
        }

        .navbar-def .navbar-brand {
            color: #fff;
        }

        .navbar-def .navbar-brand:hover,
        .navbar-def .navbar-brand:focus {
            color: #fff;
        }

        .navbar-def .navbar-nav > li > a {
            color: #fff;
        }

        .navbar-def .navbar-nav > li > a:hover,
        .navbar-def .navbar-nav > li > a:focus {
            color: #0099ff;
        }

        .navbar-def .navbar-nav > .active > a,
        .navbar-def .navbar-nav > .active > a:hover,
        .navbar-def .navbar-nav > .active > a:focus {
            color: #fff;
            background-color: #0000ff;
        }

        .navbar-def .navbar-nav > .open > a,
        .navbar-def .navbar-nav > .open > a:hover,
        .navbar-def .navbar-nav > .open > a:focus {
            color: #fff;
            background-color: #0099ff;
        }

        .navbar-def .navbar-nav > .dropdown > a .caret {
            border-top-color: #fff;
            border-bottom-color: #fff;
        }

        .navbar-def .navbar-nav > .dropdown > a:hover .caret,
        .navbar-def .navbar-nav > .dropdown > a:focus .caret {
            border-top-color: #fff;
            border-bottom-color: #fff;
        }

        .navbar-def .navbar-nav > .open > a .caret,
        .navbar-def .navbar-nav > .open > a:hover .caret,
        .navbar-def .navbar-nav > .open > a:focus .caret {
            border-top-color: #fff;
            border-bottom-color: #fff;
        }

        .navbar-def .navbar-toggle {
            border-color: #0099ff;
        }

        .navbar-def .navbar-toggle:hover,
        .navbar-def .navbar-toggle:focus {
            background-color: #0099ff;
        }

        .navbar-def .navbar-toggle .icon-bar {
            background-color: #fff;
        }

        @media (max-width: 767px) {
            .navbar-def .navbar-nav .open .dropdown-menu > li > a {
                color: #fff;
            }

            .navbar-def .navbar-nav .open .dropdown-menu > li > a:hover,
            .navbar-def .navbar-nav .open .dropdown-menu > li > a:focus {
                color: #ecdbff;
                background-color: #0099ff;
            }

        }
    </style>
</head>
<body>
<div id="page-wrap">
    <header>
        <nav class="navbar navbar-def navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="" id="logo">Easy travel</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/">Новости</a></li>
                        <li><a href="/forum">Форум</a></li>
                        <li><a href="/mytrips">Мои путешествия</a></li>
                        <li><a href="/about">О сайте</a></li>
                    </ul>
                <#if user??>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/profile"><span class="glyphicon glyphicon-user"></span>
                        ${user.profile.name}</a>
                        </li>
                        <li><a href="/logout"><span class="glyphicon glyphicon-log-in"></span> Выйти</a></li>
                    </ul>
                <#else>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/registration"><span class="glyphicon glyphicon-user"></span>
                            Регистрация</a></li>
                        <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Войти</a></li>
                    </ul>
                </#if>
                </div>
            </div>
        </nav>
    </header>
    <div id="wrapper">
        <div id="articles">
            <div class="well row">
                <div class="col-lg-2">
                    <img src="${user.profile.photoPath}" width="200" height="200">
                    <form action="/upload" method="post" enctype="multipart/form-data">
                        <input type="file" name="file" accept="image/*">
                        <input type="submit" class="btn btn-default pull-left" value="Загрузить" />
                    </form>
                </div>
                <form action="/profilechange" method="post" class="col-lg-6 col-lg-offset-1">
                    <div class="form-group">
                        <label>
                            Имя
                            <input type="text" name="name" class="form-control" value="${user.profile.name}">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            Фамилия
                            <input type="text" name="surname" class="form-control" value="${user.profile.surname}">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            Отчество
                            <input type="text" name="fathersname" class="form-control"
                                   value="${user.profile.fathersName}">
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            Email
                            <input type="text" name="email" class="form-control" value="${user.loginData.email}">
                        </label>
                    </div>
                    <input type="submit" class="btn btn-default pull-right" style="margin:10px;" value="Сохранить">
                </form>
                <button type="submit" class="btn btn-default pull-right" style="margin:10px;">Отмена</button>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<footer>
    <span class="left">Все права защищены &copy; 2017</span>
</footer>
</body>
</html>