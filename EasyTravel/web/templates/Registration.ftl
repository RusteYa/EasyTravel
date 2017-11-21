<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="">
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
    </style>
</head>
<body class="container">
<div class="login" style="margin:auto;width:30%;">
    <h3><strong>Регистрация</strong></h3>
    <form method="place" action="/registration">
        <div class="form-group">
            <input type="text" class="form-control" name="name" placeholder="Имя">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="surname" placeholder="Фамилия">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="fathers_name" placeholder="Отчество">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="login" placeholder="Логин">
        </div>
        <div class="form-group">
            <input type="email" class="form-control" name="email" placeholder="Емайл">
        </div>
        <div class="form-group">
            <input type="password" id="pas1" class="form-control" name="password" placeholder="Пароль">
        </div>
        <div class="form-group">
            <input type="password" id="pas2" class="form-control" placeholder="Подтверждение пароля">
        </div>
        <button type="submit" class="btn btn-default">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>