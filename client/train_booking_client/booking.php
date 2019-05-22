<?php

session_start();

$msg='';
$error=false;

if(!isset($_SESSION['user'])){
    header("Location: /login.php");
}

$remote_url = "http://localhost:8080/trains/";

// Suppress the warning messages
//error_reporting(0);

// Open the file using the HTTP headers set above
$file = file_get_contents($remote_url);

if ($file != false) {
    $trains = json_decode($file)->_embedded->trains;
    //print_r($trains);
}

if($file == false || count($trains)==0){
    $msg = '
        <div class="display-4 p-5 m-5">Sorry! No trains are available</div>
    ';
    $error = true;
    goto skip;
}

do{
    if( $_POST["trains"] || $_POST["date"] ) {
        if ($_POST['trains'] == 0) {
            $msg = '<div class="alert alert-danger" role="alert">Invalid Train</div>';
        }

        $trainId = (substr($trains[$_POST['trains']]->_links->self->href,1+strrpos($trains[$_POST['trains']]->_links->self->href,"/")));

        $remote_url = 'http://localhost:8080/users/'.($_SESSION["user"]->username).'/bookings/';

        $opts = array(
            'http' => array(
                'method' => 'POST',
                'header' => array(
                    "Content-type: application/json",
                    "Authorization: Basic " . base64_encode($_SESSION["user"]->username.":".$_SESSION["user"]->password)
                ),
                'content' => '{
                    "personId":"'.($_SESSION["user"]->username).'",
                    "trainId": "'.$trainId.'",
                    "date": "'.($_POST["date"]).'"
                }'
            )
        );

        $context = stream_context_create($opts);

        // Open the file using the HTTP headers set above
        $file = file_get_contents($remote_url, false, $context);

        if ($file != false) {
            $booking = json_decode($file);
            $_SESSION['booking'] = $booking->id;
            if($_POST['nic']){
                $remote_url = 'http://localhost:8080/users/'.($_SESSION["user"]->username).'/bookings/'.$_SESSION['booking'].'/discounts/govenment';

                $opts = array(
                    'http' => array(
                        'method' => 'POST',
                        'header' => array(
                            "Content-type: application/json",
                            "Authorization: Basic " . base64_encode($_SESSION["user"]->username.":".$_SESSION["user"]->password)
                        ),
                        'content' => $_POST['nic']
                    )
                );

                $context = stream_context_create($opts);

                // Open the file using the HTTP headers set above
                $file = file_get_contents($remote_url, false, $context);
                print_r($file);
                header("Location: /payment.php");
            }
            
        }
    }
}while(false);

skip:

// Enable warning messages again
error_reporting(-1);

$title = "Booking";

include "includes/head.php";

echo($msg);

if(!$error){
    ?>

    <!-- Html Content Begins -->
    <div class="content login-body m-auto">
        <h2 class="display-5 text-center pb-3">Book Your Train</h2>
        <form action="<?=$_SERVER['PHP_SELF']; ?>" method="post">
            <div class="form-group">
                <label for="trains">Trains</label>
                <select class="form-control"  id="trains" name="trains">
                    <option value="0" selected>Default select</option>
                    <? 
                    $counter = 1;
                    foreach($trains as $train){ ?>
                        <option value="<?=$counter++?>"><?=$train->name?></option>
                    <? } ?>
                </select>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="text" class="form-control" id="date" placeholder="dd/mm/yyyy" name="date" required>
            </div>
            <div class="form-group">
                <label for="nic">NIC to check if you are valid for the government employee discount</label>
                <input type="text" class="form-control" id="nic" placeholder="9********V" name="nic" required>
            </div>
        <input type="submit" name="submit" value="Submit" class="btn btn-primary">
        </form>
    </div>
    <!-- Html Content Ends -->

    <?php
}
include "includes/footer.php";