<?php

define('hostname', 'localhost');
define('user', 'root');
define('password', '');
define('databaseName', 'eticaret');


$connect = mysqli_connect(hostname, user, password, databaseName);
$connect->set_charset("utf8")
?>