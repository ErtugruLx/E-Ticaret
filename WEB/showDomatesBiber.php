<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connection.php';
	showSoru();

}

function showSoru()
{
	global $connect;

	$name = $_POST["name"];

	if(empty($name)){
		$query = " SELECT *
	FROM urunler AS u
	LEFT JOIN kategori AS k ON u.urun_kategori = k.kategori_id
	where u.urun_kategori=1; ";
	
	}else{
	$query = " SELECT *
	FROM urunler AS u
	LEFT JOIN kategori AS k ON u.urun_kategori = k.kategori_id
	where u.urun_kategori=1; ";
	
	}
	
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array(); 
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$temp_array[] = $row;
		}
	}
	
	header('Content-Type: application/json; charset=utf-8');
	echo json_encode(array("urunler"=>$temp_array));
	

	 
	mysqli_close($connect);	

}
?>