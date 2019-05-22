<?php

function getId($url)
{
    return substr($url,strrpos($url,"/"));
}