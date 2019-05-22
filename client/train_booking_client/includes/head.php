<?php
include "imports.php";
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Didact+Gothic&display=swap" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">
    <title>SL Train - <?=$title?></title>
</head>
<body>
    <div class="content">
    <!-- Top Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
        <a class="navbar-brand mb-0 h1" href="/">SL Train</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <? if(isset($_SESSION['user'])){ ?>
                    <li class="nav-item pr-1">
                        <a class="nav-link btn btn-outline-light text-dark" href="/logout.php">Logout</a>
                    </li>
                <? } else { ?>
                    <li class="nav-item pr-1">
                        <a class="nav-link btn btn-outline-light text-dark" href="/login.php">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-outline-light text-dark" href="/register.php">Register</a>
                    </li>
                <? } ?>
            </ul>
        </div>
    </nav>
