<?php

session_start();
if(isset($_GET['redirect'])){
    $_SESSION['redirect'] = $_GET['redirect'];
}

$msg = '';

if(isset($_SESSION['user'])){
    if(isset($_SESSION['redirect'])){
        header("Location: ".$_SESSION['redirect']);
        unset($_SESSION['redirect']);
    }else{
        header("Location: /");
    }
}

if( $_POST["username"] || $_POST["password"] ) {
    if (preg_match("/[ ]/",$_POST['username'] )) {
        $msg = '<div class="alert alert-danger" role="alert">Invalid Username</div>';
    }
    $remote_url = 'http://localhost:8080/users/'.$_POST["username"]."/";

    // Create a stream
    $opts = array(
    'http'=>array(
        'method'=>"GET",
        'header' => "Authorization: Basic " . base64_encode($_POST["username"].":".$_POST["password"])                 
    )
    );

    $context = stream_context_create($opts);

    // Suppress the warning messages
    error_reporting(0);

    // Open the file using the HTTP headers set above
    $file = file_get_contents($remote_url, false, $context);

    if ($file != false) {
        $file = json_decode($file);
        $_SESSION['user'] = $file;
        $_SESSION["user"]->password = $_POST["password"];
        if(isset($_SESSION['redirect'])){
            header("Location: ".$_SESSION['redirect']);
            unset($_SESSION['redirect']);
        }else{
            header("Location: /");
        }
    }else{
        $msg = '<div class="alert alert-danger" role="alert">Something went wrong</div>';
    }

    // Enable warning messages again
    error_reporting(-1);
 }

$title = "Login";

include "includes/head.php";

echo($msg);
?>

<!-- Html Content Begins -->
<div class="content login-body m-auto">
    <h2 class="display-5 text-center pb-3">Login</h2>
    <form action="<?=$_SERVER['PHP_SELF']; ?>" method="post">
        <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name="username" aria-describedby="usernameHelp" placeholder="Enter Username" required>
        <small id="usernameHelp" class="form-text text-muted">We'll never share your username with anyone else.</small>
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Password" name="password" required>
    </div>
    <input type="submit" name="submit" value="Submit" class="btn btn-primary">
    </form>
</div>
<!-- Html Content Ends -->

<?php
include "includes/footer.php";