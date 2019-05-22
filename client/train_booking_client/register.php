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

    $remote_url = 'http://localhost:8080/users/';

    $opts = array(
        'http' => array(
            'method' => 'POST',
            'header' => array(
                "Content-type: application/json"
            ),
            'content' => '{
                "username":"'.($_POST['username']).'",
                "password":"'.($_POST['password']).'",
                "firstName": "'.($_POST['firstname']).'",
                "lastName": "'.($_POST['lastname']).'",
                "email": "'.($_POST['email']).'",
                "telNo": "'.($_POST['telno']).'"
            }'
        )
    );

    $context = stream_context_create($opts);
    
    set_error_handler(function($errno, $errstr, $errfile, $errline, array $errcontext) {
        // error was suppressed with the @-operator
        if (0 === error_reporting()) {
            return false;
        }
    
        throw new ErrorException($errstr, 0, $errno, $errfile, $errline);
    });
    
    try{
        // Open the file using the HTTP headers set above
        $file = file_get_contents($remote_url, false, $context);

        if ($file != false) {
            $msg = '<div class="alert alert-danger" role="alert">Something Went Wrong</div>';
        }

        if ($file != false) {
            $file = json_decode($file);
            $_SESSION['user'] = $file;
            if(isset($_SESSION['redirect'])){
                header("Location: ".$_SESSION['redirect']);
                unset($_SESSION['redirect']);
            }else{
                header("Location: /");
            }
        }
    }catch(Exception $ex){
        $msg = '<div class="alert alert-danger" role="alert">Something Went Wrong</div>';
    }
 }

$title = "Login";

include "includes/head.php";

echo($msg);
?>

<!-- Html Content Begins -->
<div class="content reg-body m-auto">
    <h2 class="display-5 text-center pb-3">Register</h2>
    <form action="<?=$_SERVER['PHP_SELF']; ?>" method="post">
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="validationCustom01">First name</label>
                <input type="text" class="form-control" id="validationCustom01" name="firstname" placeholder="First name" required>
                <div class="valid-feedback">
                    Looks good!
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationCustom02">Last name</label>
                <input type="text" class="form-control" id="validationCustom02" placeholder="Last name" name="lastname" required>
                <div class="valid-feedback">
                    Looks good!
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <label for="validationCustomUsername">Username</label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroupPrepend">@</span>
                    </div>
                    <input type="text" class="form-control" id="validationCustomUsername" placeholder="Username" name="username" aria-describedby="inputGroupPrepend" required>
                    <div class="invalid-feedback">
                        Please choose a username.
                    </div>
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationCustom03">Email</label>
                <input type="email" class="form-control" id="validationCustom03" placeholder="Email" name="email" required>
                <div class="invalid-feedback">
                    Please provide a valid email.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="validationCustom04">Telephone No</label>
                <input type="number" class="form-control" id="validationCustom04" placeholder="Telephone No" name="telno" required>
                <div class="invalid-feedback">
                    Please provide a valid Telephone No.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="validationCustom05">Password</label>
                <input type="password" class="form-control" id="validationCustom05" placeholder="Password" name="password" required>
                <div class="invalid-feedback">
                    Please provide a valid password.
                </div>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Register</button>
    </form>
</div>
<!-- Html Content Ends -->

<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>
<?php
include "includes/footer.php";