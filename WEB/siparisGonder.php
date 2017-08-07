<?php
 
 if($_POST){
 require_once('dbConnect.php');
 
 $urunid = $_POST["urunid"];
 $email = $_POST["email"];
 $adet = $_POST["adet"];
 $toplam = $_POST["toplam"];
 
 
 $sql = "INSERT INTO siparis (urunler_id,users_mail,siparis_adet,toplamtutar) VALUES ('$urunid','$email','$adet','$toplam')";

 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully Uploaded";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }
 
 ?>