<?php

session_start();

$msg = '';

if(isset($_SESSION['booking'])){
    header("Location: /");
}

$title = "Payment";

include "includes/head.php";

echo($msg);
?>

<!-- Html Content Begins -->
<div class="content reg-body m-auto">
    <h2 class="display-5 text-center pb-3">Select Payment Type</h2>
    <div class="display-inline text-center m-5">
        <a class="btn btn-primary" href="dialogpayment.php">Pay with Dialoy Pay</a>
        <a class="btn btn-primary" href="cardpayment.php">Pay with Visa Card</a>
    </div>
</div>
<!-- Html Content Ends -->

<?php
include "includes/footer.php";